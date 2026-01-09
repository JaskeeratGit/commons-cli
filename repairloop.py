#!/usr/bin/env python3
# repairloop.py
from __future__ import annotations

import argparse
import datetime as dt
import json
import os
import re
import sys
import time
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

from prompt_constructor import RepairLoopContext  # type: ignore


_OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions"

_PACKAGE_RE = re.compile(r"^\s*package\s+([a-zA-Z0-9_.]+)\s*;\s*$", re.MULTILINE)
_CODE_FENCE_RE = re.compile(r"```(?:java|[a-zA-Z0-9_-]+)?\s*([\s\S]*?)```", re.MULTILINE)
_PUBLIC_CLASS_RE = re.compile(r"\bpublic\s+(?:final\s+)?class\s+([A-Za-z_][A-Za-z0-9_]*)\b")


# =========================
# IO
# =========================

def read_json(path: Path) -> Any:
    return json.loads(path.read_text(encoding="utf-8", errors="replace"))


def read_text(path: Path) -> str:
    return path.read_text(encoding="utf-8", errors="replace")


# =========================
# broken map handling
# =========================

def coerce_record_ids(obj: Any) -> List[str]:
    """
    Your broken_tests_mapped.json can be:
      - dict keyed by filename -> metadata
      - list of dicts, each containing some id/path key
    We only need record ids (filenames) because unit tests are pulled from --broken-tests-dir.
    """
    ids: List[str] = []

    if isinstance(obj, dict):
        for k in obj.keys():
            ids.append(str(k))
        return ids

    if isinstance(obj, list):
        for i, item in enumerate(obj):
            if not isinstance(item, dict):
                continue
            rid = item.get("id") or item.get("test_id") or item.get("path") or item.get("file") or item.get("filename")
            if rid is None:
                rid = str(i)
            ids.append(str(rid))
        return ids

    raise ValueError("broken_tests_mapped.json must be a dict or list")


# =========================
# unit test discovery
# =========================

def index_java_files(root: Path) -> Dict[str, List[Path]]:
    idx: Dict[str, List[Path]] = {}
    for p in root.rglob("*.java"):
        idx.setdefault(p.name, []).append(p)
    for k in list(idx.keys()):
        idx[k] = sorted(idx[k])
    return idx


def load_unit_test_from_dir(
    *,
    broken_tests_dir: Path,
    java_index: Dict[str, List[Path]],
    record_id: str,
) -> Tuple[str, Path]:
    rid_name = Path(record_id).name
    if not rid_name.endswith(".java"):
        rid_name += ".java"

    matches = java_index.get(rid_name, [])
    if not matches:
        candidate = broken_tests_dir / record_id
        if candidate.exists() and candidate.is_file():
            matches = [candidate]

    if not matches:
        raise FileNotFoundError(f"Could not locate {rid_name} under {broken_tests_dir}")

    chosen = matches[0]
    return read_text(chosen), chosen


def parse_java_package(java_src: str) -> Optional[str]:
    m = _PACKAGE_RE.search(java_src or "")
    return m.group(1).strip() if m else None


# =========================
# infer focal identifiers from filename
# =========================

def parse_filename_tokens(record_id: str) -> Tuple[Optional[str], Optional[str], Optional[str], Optional[str]]:
    """
    Expected:
      <ClassName>_<methodName>_<methodIndex>_<variant>_Test_...

    Returns: (className, methodName, methodIndex, variant)
    """
    stem = Path(record_id).name
    if stem.endswith(".java"):
        stem = stem[:-5]
    parts = stem.split("_")
    if len(parts) < 5:
        return None, None, None, None
    class_name = parts[0] or None
    method_name = parts[1] or None
    method_index = parts[2] or None
    variant = parts[3] or None
    return class_name, method_name, method_index, variant


# =========================
# classMapping.json handling
# =========================

def load_class_mapping(class_map_path: Path) -> Dict[str, Any]:
    obj = read_json(class_map_path)
    if not isinstance(obj, dict):
        raise ValueError("classMapping.json must be a dict {class_key: {...}}")
    return obj


def infer_class_key(
    *,
    class_map: Dict[str, Any],
    class_name: str,
    package_name: Optional[str],
) -> Optional[str]:
    """
    Deterministic:
      1) exact className + exact packageName
      2) exact className + packageName prefix match
      3) exact className + choose shortest packageName (stable fallback)
    """
    candidates: List[Tuple[str, str]] = []
    for ck, entry in class_map.items():
        if not isinstance(entry, dict):
            continue
        if str(entry.get("className") or "") != class_name:
            continue
        pkg = str(entry.get("packageName") or "")
        if pkg:
            candidates.append((str(ck), pkg))

    if not candidates:
        return None

    if package_name:
        exact = [ck for ck, pkg in candidates if pkg == package_name]
        if exact:
            return exact[0]
        prefix = [ck for ck, pkg in candidates if pkg.startswith(package_name)]
        if prefix:
            return prefix[0]

    # stable fallback
    candidates.sort(key=lambda x: (len(x[1]), x[1], x[0]))
    return candidates[0][0]


# =========================
# method file inference (numbered JSONs)
# =========================

def _method_json_matches_method_name(method_json: Dict[str, Any], method_name: str) -> bool:
    mn = method_json.get("methodName")
    if isinstance(mn, str) and mn == method_name:
        return True
    sig = method_json.get("methodSignature")
    if isinstance(sig, str):
        # methodSignature like "getOptionValue(String)" or "getOptionValue(char,Object)"
        if sig.strip().startswith(method_name + "("):
            return True
    brief = method_json.get("brief")
    if isinstance(brief, str) and (method_name + "(") in brief:
        return True
    full = method_json.get("full_method_info")
    if isinstance(full, str) and (method_name + "(") in full:
        return True
    src = method_json.get("sourceCode")
    if isinstance(src, str) and (method_name + "(") in src:
        return True
    return False


def infer_method_file_stem_numbered(
    *,
    class_info_dir: Path,
    package_name: str,
    class_name: str,
    method_name: str,
    method_index_token: Optional[str],
) -> Optional[str]:
    """
    Your class-info layout:
      class-info/<pkg path>/<ClassName>/
        class.json
        0.json
        1.json
        2.json
        ...

    We prefer:
      A) <methodIndex>.json if it exists AND matches method_name
      B) scan all numeric jsons and pick first that matches method_name
    """
    class_dir = class_info_dir.joinpath(*package_name.split("."), class_name)
    if not class_dir.exists():
        return None

    # A) try methodIndex token
    if method_index_token and method_index_token.isdigit():
        p = class_dir / f"{method_index_token}.json"
        if p.exists():
            try:
                mj = read_json(p)
                if isinstance(mj, dict) and _method_json_matches_method_name(mj, method_name):
                    return p.stem  # numeric stem
            except Exception:
                pass

    # B) scan all numeric jsons
    json_files = sorted([p for p in class_dir.glob("*.json") if p.name != "class.json"])
    for p in json_files:
        if not p.stem.isdigit():
            continue
        try:
            mj = read_json(p)
        except Exception:
            continue
        if isinstance(mj, dict) and _method_json_matches_method_name(mj, method_name):
            return p.stem

    return None


# =========================
# OpenRouter
# =========================

def openrouter_chat(
    *,
    api_key: str,
    model: str,
    prompt: str,
    temperature: float = 0.0,
    max_tokens: Optional[int] = None,
    site_url: Optional[str] = None,
    app_title: Optional[str] = None,
    timeout_s: int = 180,
) -> str:
    payload: Dict[str, Any] = {
        "model": model,
        "messages": [{"role": "user", "content": prompt}],
        "temperature": temperature,
    }
    if max_tokens is not None:
        payload["max_tokens"] = int(max_tokens)

    headers = {
        "Authorization": f"Bearer {api_key}",
        "Content-Type": "application/json",
    }
    if site_url:
        headers["HTTP-Referer"] = site_url
    if app_title:
        headers["X-Title"] = app_title

    req = Request(_OPENROUTER_URL, data=json.dumps(payload).encode("utf-8"), headers=headers, method="POST")

    try:
        with urlopen(req, timeout=timeout_s) as resp:
            body = resp.read().decode("utf-8", errors="replace")
    except HTTPError as e:
        err_body = ""
        try:
            err_body = e.read().decode("utf-8", errors="replace")
        except Exception:
            pass
        raise RuntimeError(f"OpenRouter HTTPError {e.code}: {err_body or str(e)}") from e
    except URLError as e:
        raise RuntimeError(f"OpenRouter URLError: {e}") from e

    j = json.loads(body)
    choices = j.get("choices") or []
    if not choices:
        raise RuntimeError(f"OpenRouter response missing choices: {body[:2000]}")
    msg = (choices[0].get("message") or {})
    content = msg.get("content")

    if isinstance(content, str):
        return content.strip()
    if isinstance(content, list):
        parts: List[str] = []
        for part in content:
            if isinstance(part, dict) and isinstance(part.get("text"), str):
                parts.append(part["text"])
            elif isinstance(part, str):
                parts.append(part)
        return "\n".join(parts).strip()

    text = choices[0].get("text")
    if isinstance(text, str):
        return text.strip()

    raise RuntimeError(f"OpenRouter response content not found: {body[:2000]}")


def extract_java_from_llm_response(text: str) -> str:
    t = text.strip()

    if "<<Generation Begin>>" in t and "<<Generation Over>>" in t:
        inner = t.split("<<Generation Begin>>", 1)[1].split("<<Generation Over>>", 1)[0].strip()
        if inner:
            return inner

    fences = _CODE_FENCE_RE.findall(t)
    if fences:
        blocks = sorted((b.strip() for b in fences if b and b.strip()), key=len, reverse=True)
        if blocks:
            return blocks[0]

    return t


def infer_pkg_and_public_class(java_src: str) -> Tuple[Optional[str], Optional[str]]:
    pkg = None
    m = _PACKAGE_RE.search(java_src)
    if m:
        pkg = m.group(1).strip()

    cls = None
    m2 = _PUBLIC_CLASS_RE.search(java_src)
    if m2:
        cls = m2.group(1).strip()

    return pkg, cls


def safe_stem(s: str) -> str:
    s2 = re.sub(r"[^A-Za-z0-9_.-]+", "_", s).strip("_")
    return s2 or "output"


# =========================
# Main
# =========================

def main() -> int:
    ap = argparse.ArgumentParser(description="Round-0 repair attempt for each broken test via OpenRouter")
    ap.add_argument("--broken-map", required=True, type=Path, help="broken_tests_mapped.json path (used for record ids)")
    ap.add_argument("--broken-tests-dir", required=True, type=Path, help="root dir containing broken .java tests (recursively)")

    ap.add_argument("--class-map", required=True, type=Path, help="classMapping.json path")
    ap.add_argument("--class-info-dir", required=True, type=Path, help="class-info root dir")

    ap.add_argument("--model", required=True, help="OpenRouter model id (e.g., openai/gpt-5-mini)")
    ap.add_argument("--api-key", default=os.getenv("OPENROUTER_API_KEY", ""), help="or set OPENROUTER_API_KEY")
    ap.add_argument("--site-url", default=os.getenv("OPENROUTER_SITE_URL", ""), help="optional HTTP-Referer")
    ap.add_argument("--app-title", default=os.getenv("OPENROUTER_APP_TITLE", ""), help="optional X-Title")

    ap.add_argument("--prompt-method", choices=["zero_shot", "few_shot", "cot"], default="zero_shot")
    ap.add_argument("--temperature", type=float, default=0.0)
    ap.add_argument("--max-tokens", type=int, default=None)

    ap.add_argument("--out-dir", required=True, type=Path, help="output directory")
    ap.add_argument("--mirror-package-dirs", action="store_true", help="write under <out-dir>/<package path>/")
    ap.add_argument("--max-records", type=int, default=None)
    ap.add_argument("--sleep-s", type=float, default=0.0)

    args = ap.parse_args()

    if not args.api_key:
        print("ERROR: missing OpenRouter API key (set OPENROUTER_API_KEY or pass --api-key).", file=sys.stderr)
        return 2
    if not args.broken_tests_dir.exists():
        print(f"ERROR: --broken-tests-dir not found: {args.broken_tests_dir}", file=sys.stderr)
        return 2

    broken_obj = read_json(args.broken_map)
    record_ids = coerce_record_ids(broken_obj)
    if args.max_records is not None:
        record_ids = record_ids[: max(0, args.max_records)]

    class_map = load_class_mapping(args.class_map)
    java_index = index_java_files(args.broken_tests_dir)

    args.out_dir.mkdir(parents=True, exist_ok=True)
    jsonl_path = args.out_dir / "llm_round0_results.jsonl"
    round_num = 0

    for i, record_id in enumerate(record_ids, start=1):
        # 1) load test from directory tree
        try:
            unit_test, unit_test_path = load_unit_test_from_dir(
                broken_tests_dir=args.broken_tests_dir,
                java_index=java_index,
                record_id=record_id,
            )
        except Exception as e:
            print(f"[SKIP {i}] {record_id}: cannot load unit test: {e}", file=sys.stderr)
            continue

        package_name = parse_java_package(unit_test)
        cls_name, method_name, method_index, _variant = parse_filename_tokens(record_id)
        if not cls_name or not method_name:
            print(f"[SKIP {i}] {record_id}: cannot parse <Class>_<method> from filename", file=sys.stderr)
            continue

        # 2) infer class_key from classMapping.json using className+packageName
        class_key = infer_class_key(class_map=class_map, class_name=cls_name, package_name=package_name)
        if not class_key:
            print(f"[SKIP {i}] {record_id}: cannot find class_key for {package_name}.{cls_name}", file=sys.stderr)
            continue

        entry = class_map.get(class_key)
        pkg = str(entry.get("packageName") or "") if isinstance(entry, dict) else ""
        cn = str(entry.get("className") or "") if isinstance(entry, dict) else ""
        if not pkg or not cn:
            print(f"[SKIP {i}] {record_id}: bad classMapping entry for {class_key}", file=sys.stderr)
            continue

        # 3) infer method_file_stem (numeric) from class-info
        method_file_stem = infer_method_file_stem_numbered(
            class_info_dir=args.class_info_dir,
            package_name=pkg,
            class_name=cn,
            method_name=method_name,
            method_index_token=method_index,
        )
        if not method_file_stem:
            print(f"[SKIP {i}] {record_id}: cannot find method json for {pkg}.{cn}.{method_name}", file=sys.stderr)
            continue

        # required placeholders (round 0)
        error_type = ""
        error_message = ""

        # 4) build prompt
        try:
            ctx = RepairLoopContext(class_map_path=args.class_map, class_info_dir=args.class_info_dir)
            ctx.populate_all(
                class_key=class_key,
                method_file_stem=method_file_stem,
                unit_test=unit_test,
                error_message=error_message,
                error_type=error_type,
                prompt_method=args.prompt_method,  # type: ignore[arg-type]
            )
            prompt = ctx.render_prompt()
        except Exception as e:
            print(f"[SKIP {i}] {record_id}: prompt construction failed: {e}", file=sys.stderr)
            continue

        # 5) call LLM once
        try:
            llm_raw = openrouter_chat(
                api_key=args.api_key,
                model=args.model,
                prompt=prompt,
                temperature=args.temperature,
                max_tokens=args.max_tokens,
                site_url=args.site_url or None,
                app_title=args.app_title or None,
            )
        except Exception as e:
            print(f"[FAIL {i}] {record_id}: OpenRouter call failed: {e}", file=sys.stderr)
            continue

        # 6) write outputs
        java_src = extract_java_from_llm_response(llm_raw)
        out_pkg, out_cls = infer_pkg_and_public_class(java_src)

        base_hint = Path(record_id).name
        out_stem = safe_stem(out_cls or Path(base_hint).stem)

        out_dir = args.out_dir
        if args.mirror_package_dirs and out_pkg:
            out_dir = out_dir / Path(*out_pkg.split("."))
            out_dir.mkdir(parents=True, exist_ok=True)

        java_path = out_dir / f"{out_stem}.java"
        meta_path = out_dir / f"{out_stem}.json"

        java_path.write_text(java_src.rstrip() + "\n", encoding="utf-8")

        meta = {
            "round": round_num,
            "error_type": error_type,
            "error_message": error_message,
            "prompt_method": args.prompt_method,
            "prompt": prompt,
            "response": llm_raw,
            "record_id": str(record_id),
            "input_unit_test_path": str(unit_test_path),
            "class_key": class_key,
            "packageName": pkg,
            "className": cn,
            "methodName": method_name,
            "method_index_token": method_index,
            "method_file_stem": method_file_stem,
            "model": args.model,
            "timestamp_utc": dt.datetime.utcnow().replace(microsecond=0).isoformat() + "Z",
        }
        meta_path.write_text(json.dumps(meta, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")

        with jsonl_path.open("a", encoding="utf-8") as f:
            f.write(json.dumps(meta, ensure_ascii=False) + "\n")

        if args.sleep_s > 0:
            time.sleep(args.sleep_s)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
