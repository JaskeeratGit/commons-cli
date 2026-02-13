package org.apache.commons.cli;

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

class Options_hasOption_14_0_Test_testHasOptionShortAndLongVariants {

    private Options options;

    @BeforeEach
    void setUp() {
        options = new Options();
    }



    @Test
    void testHasOptionShortAndLongVariants() throws Exception {
        // Add short option "a" and long option "alpha"
        putIntoMap(options, "shortOpts", "a", new org.apache.commons.cli.Option("a"));
        putIntoMap(options, "longOpts", "alpha", new org.apache.commons.cli.Option("alpha"));
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
    private void putIntoMap(Options optionsInstance, String fieldName, String key, org.apache.commons.cli.Option value) throws Exception {
        Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        Map<String, org.apache.commons.cli.Option> map = (Map<String, org.apache.commons.cli.Option>) f.get(optionsInstance);
        map.put(key, value);
    }
}
