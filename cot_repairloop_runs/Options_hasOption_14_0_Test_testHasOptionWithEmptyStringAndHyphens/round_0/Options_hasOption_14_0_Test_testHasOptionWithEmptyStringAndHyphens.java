package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Options.hasOption related to empty-string and leading-hyphen cases.
 *
 * Note: Do NOT declare any top-level helper classes named Option or OptionGroup in this file.
 * Declaring such classes at package level would shadow the real org.apache.commons.cli.Option
 * implementation used across the test-suite and cause compilation failures elsewhere.
 */
class Options_hasOption_14_0_Test_testHasOptionWithEmptyStringAndHyphens {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }

    @Test
    void testHasOptionWithEmptyStringAndHyphens() throws Exception {
        // Put an entry with empty string key into shortOpts and longOpts
        putIntoMap(options, "shortOpts", "", new Option(""));
        putIntoMap(options, "longOpts", "", new Option(""));

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
