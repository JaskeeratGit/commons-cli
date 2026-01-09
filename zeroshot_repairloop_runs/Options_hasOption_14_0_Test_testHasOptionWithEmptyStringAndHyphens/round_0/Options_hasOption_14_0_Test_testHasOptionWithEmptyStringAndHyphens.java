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
