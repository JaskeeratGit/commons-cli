# sample_usage.py
from pathlib import Path
from prompt_constructor import RepairLoopContext

# assume RepairLoopContext is imported from wherever you put it
# from repairloop_context import RepairLoopContext

ctx = RepairLoopContext(
    class_map_path=Path(
        "/home/jaskeerat/paper1/cli_latest_stable/commons-cli/chatunitest-tmp/commons-cli/classMapping.json"
    ),
    class_info_dir=Path(
        "/home/jaskeerat/paper1/cli_latest_stable/commons-cli/chatunitest-tmp/commons-cli/class-info"
    ),
)

# 1) set run context (what happened when you ran the test)
ctx.set_run_context(
    unit_test="""
package org.apache.commons.cli.help;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextHelpAppendable_printWrapped_15_0_Test {
    @Test
    void printWrapped_doesNotThrow() throws Exception {
        TextHelpAppendable tha = new TextHelpAppendable(new StringBuilder());
        tha.printWrapped("hello");
        assertTrue(true);
    }
}
""".strip(),
    error_message="java.lang.NullPointerException: ...",  # whatever you captured
)

# 2) populate focal + dependent context from JSONs
# class_key: key inside classMapping.json (e.g., "class3")
# method_file_stem: the "<n>.json" for the focal method (e.g., "10" for toSyntaxOption())
ctx.populate_focal_from_files(
    class_key="class9",      # -> OptionFormatter (from your example)
    method_file_stem="15",   # -> OptionFormatter/toSyntaxOption() method json (10.json)
)

# 3) inspect the selected fields (no raw full-file dumps)
print("FOCAL FQCN:", ctx.focal_fqcn)
print("FOCAL method_sig:", ctx.method_sig)
print("\nFOCAL method source:\n", ctx.fm_source_code)

print("\nFOCAL class skeleton:\n", ctx.focal_class_skeleton)
print("\nFOCAL method stubs count:", len(ctx.focal_class_method_sigs))

print("\nDEPENDENT methods dict:\n", ctx.dependent_method_sigs)
print("\nDEPENDENT class skeleton keys:\n", list(ctx.dependent_class_skeletons.keys()))
for fqcn, skel in ctx.dependent_class_skeletons.items():
    print(f"\n--- Dep skeleton for {fqcn} ---\n{skel}")

# 4) choose prompt method and render
ctx.set_prompt_method("few_shot")
prompt = ctx.render_prompt()
print("\n\n===== FINAL PROMPT =====\n")
print(prompt)
