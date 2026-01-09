package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for Options.hasOption handling of leading double-hyphen edge cases.
 */
class Options_hasOption_14_0_Test_testHasOptionDoubleHyphenEdgeCases {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }

    @Test
    void testHasOptionDoubleHyphenEdgeCases() throws Exception {
        // Put an empty key only into longOpts to ensure -- only case handled
        putIntoMap(options, "longOpts", "", Option.builder("emptyLong").build());
        // "--" becomes "" -> should be true
        assertTrue(options.hasOption("--"));
        // A string that starts with but longer than two hyphens: "--x" -> "x"
        putIntoMap(options, "longOpts", "x", Option.builder("x").build());
        assertTrue(options.hasOption("--x"));
    }

    // Helper to inject into private maps shortOpts/longOpts via reflection
    @SuppressWarnings("unchecked")
    private void putIntoMap(Options optionsInstance, String fieldName, String key, Option value) throws Exception {
        Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        Map<String, Option> map = (Map<String, Option>) f.get(optionsInstance);
        map.put(key, value);
    }
}
