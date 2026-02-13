package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Options_hasOption_14_0_Test_testHasOptionShortAndLongVariants {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }



    @Test
    void testHasOptionShortAndLongVariants() throws Exception {
        // Add short option "a" and long option "alpha"
        putIntoMap(options, "shortOpts", "a", Option.builder("a").build());
        putIntoMap(options, "longOpts", "alpha", Option.builder("alpha").build());
        // With hyphens
        assertTrue(options.hasOption("-a"));
        assertTrue(options.hasOption("--alpha"));
        // Without hyphens should also work (stripLeadingHyphens leaves it unchanged)
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
        // Unknown option should be false
        assertFalse(options.hasOption("unknown"));
        assertFalse(options.hasOption("-unknown"));
        assertFalse(options.hasOption("--unknown"));
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
