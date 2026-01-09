package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Options_hasOption_14_0_Test_testHasOptionWithNullReturnsFalse {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }

    @Test
    void testHasOptionWithNullReturnsFalse() {
        // no entries in maps, null should be handled by Util.stripLeadingHyphens -> returns null (or treated empty)
        assertFalse(options.hasOption(null));
    }

    @Test
    void testHasOptionWithNullWhenMapContainsNullKeyReturnsTrue() throws Exception {
        // If the internal maps contain a null key, hasOption(null) should return true.
        // Inject an Option into shortOpts with a null key via reflection.
        Option injected = Option.builder("x").build();
        putIntoMap(options, "shortOpts", null, injected);

        assertTrue(options.hasOption(null));
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
