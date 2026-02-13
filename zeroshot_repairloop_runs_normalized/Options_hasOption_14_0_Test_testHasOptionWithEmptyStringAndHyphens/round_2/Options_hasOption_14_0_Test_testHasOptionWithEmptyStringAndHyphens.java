package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Options_hasOption_14_0_Test_testHasOptionWithEmptyStringAndHyphens {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }


    @Test
    void testHasOptionWithEmptyStringAndHyphens() throws Exception {
        // Put an entry with empty string key into shortOpts and longOpts
        // Use a non-empty option name for the Option instance (builder disallows empty names)
        putIntoMap(options, "shortOpts", "", Option.builder("x").build());
        putIntoMap(options, "longOpts", "", Option.builder("x").build());
        // Direct empty string
        assertTrue(options.hasOption(""));
        // Single hyphen should strip to empty string
        assertTrue(options.hasOption("-"));
        // Double hyphen should strip to empty string
        assertTrue(options.hasOption("--"));
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
