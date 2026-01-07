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




    // Helper to inject into private maps shortOpts/longOpts via reflection
    @SuppressWarnings("unchecked")
    private void putIntoMap(Options optionsInstance, String fieldName, String key, Option value) throws Exception {
        Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        Map<String, Option> map = (Map<String, Option>) f.get(optionsInstance);
        map.put(key, value);
    }
}

// Minimal supporting classes to satisfy Options' references in the test environment.
// These are package-private and only provide the minimal structure required for the tests.
class Option {

    private final String name;

    Option(String name) {
        this.name = name;
    }

    String getOpt() {
        return name;
    }
}

class OptionGroup {
    // empty - not needed for these tests
}
