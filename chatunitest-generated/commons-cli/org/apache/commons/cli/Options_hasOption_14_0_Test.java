package org.apache.commons.cli;

import org.apache.commons.cli.Options;
import java.lang.reflect.Field;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

class Options_hasOption_14_0_Test {

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMapField(final Options options, final String fieldName) throws Exception {
        final Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (Map<String, Object>) f.get(options);
    }

    @Test
    void testHasOptionNullAndEmpty() throws Exception {
        Options opts = new Options();
        // no entries -> null and empty should return false
        assertFalse(opts.hasOption(null), "hasOption(null) should be false when no null key present");
        assertFalse(opts.hasOption(""), "hasOption(\"\") should be false when no empty key present");
    }

    @Test
    void testHasOptionShortOptionSingleHyphenAndNoHyphen() throws Exception {
        Options opts = new Options();
        Map<String, Object> shortOpts = getMapField(opts, "shortOpts");
        // insert a short option with key "a"
        shortOpts.put("a", new Object());
        // single hyphen should resolve to "a"
        assertTrue(opts.hasOption("-a"), "hasOption(\"-a\") should be true when shortOpts contains \"a\"");
        // no hyphen should also match
        assertTrue(opts.hasOption("a"), "hasOption(\"a\") should be true when shortOpts contains \"a\"");
        // double hyphen should also map to "a"
        assertTrue(opts.hasOption("--a"), "hasOption(\"--a\") should be true when shortOpts contains \"a\"");
    }

    @Test
    void testHasOptionLongOptionDoubleHyphenAndSingleHyphen() throws Exception {
        Options opts = new Options();
        Map<String, Object> longOpts = getMapField(opts, "longOpts");
        // insert a long option with key "long"
        longOpts.put("long", new Object());
        // double hyphen standard long option
        assertTrue(opts.hasOption("--long"), "hasOption(\"--long\") should be true when longOpts contains \"long\"");
        // no hyphen
        assertTrue(opts.hasOption("long"), "hasOption(\"long\") should be true when longOpts contains \"long\"");
        // single hyphen should also be stripped to "long"
        assertTrue(opts.hasOption("-long"), "hasOption(\"-long\") should be true when longOpts contains \"long\"");
    }

    @Test
    void testHasOptionTripleHyphenHandledAsDoubleHyphenBranch() throws Exception {
        Options opts = new Options();
        Map<String, Object> shortOpts = getMapField(opts, "shortOpts");
        // When input is "---x", stripLeadingHyphens uses the "--" branch, producing "-x".
        // Insert key "-x" to ensure that case is handled.
        shortOpts.put("-x", new Object());
        assertTrue(opts.hasOption("---x"), "hasOption(\"---x\") should map to key \"-x\" and return true");
    }

    @Test
    void testCaseSensitivityAndNeitherMapContains() throws Exception {
        Options opts = new Options();
        Map<String, Object> shortOpts = getMapField(opts, "shortOpts");
        // Insert key with mixed case
        shortOpts.put("Opt", new Object());
        // case-sensitive check: different case should not match
        assertFalse(opts.hasOption("opt"), "hasOption(\"opt\") should be false when only \"Opt\" present");
        assertTrue(opts.hasOption("Opt"), "hasOption(\"Opt\") should be true when \"Opt\" present");
        // a random option that doesn't exist should be false
        assertFalse(opts.hasOption("doesNotExist"), "hasOption for nonexistent option should be false");
    }

    @Test
    void testEitherMapSatisfiesCondition() throws Exception {
        Options opts = new Options();
        Map<String, Object> shortOpts = getMapField(opts, "shortOpts");
        Map<String, Object> longOpts = getMapField(opts, "longOpts");
        // Put key only in longOpts
        longOpts.put("onlyLong", new Object());
        assertTrue(opts.hasOption("--onlyLong"), "hasOption should return true when longOpts contains the key");
        // Put key only in shortOpts
        shortOpts.put("onlyShort", new Object());
        assertTrue(opts.hasOption("-onlyShort"), "hasOption should return true when shortOpts contains the key");
    }
}
