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

class Options_hasOption_14_0_Test {

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

    @Test
    void testHasOptionShortAndLongVariants() throws Exception {
        // Add short option "a" and long option "alpha"
        putIntoMap(options, "shortOpts", "a", new Option("a"));
        putIntoMap(options, "longOpts", "alpha", new Option("alpha"));
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

    @Test
    void testHasOptionDoubleHyphenEdgeCases() throws Exception {
        // Put an empty key only into longOpts to ensure -- only case handled
        putIntoMap(options, "longOpts", "", new Option("emptyLong"));
        // "--" becomes "" -> should be true
        assertTrue(options.hasOption("--"));
        // A string that starts with but longer than two hyphens: "--x" -> "x"
        putIntoMap(options, "longOpts", "x", new Option("x"));
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
