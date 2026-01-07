package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

class Options_hasLongOption_13_0_Test {

    private Options options;

    private Field longOptsField;

    private Method hasLongOptionMethod;

    @BeforeEach
    void setUp() throws Exception {
        options = new Options();
        // access private field longOpts
        longOptsField = Options.class.getDeclaredField("longOpts");
        longOptsField.setAccessible(true);
        // access hasLongOption method (public, but invoked reflectively per requirements)
        hasLongOptionMethod = Options.class.getDeclaredMethod("hasLongOption", String.class);
        hasLongOptionMethod.setAccessible(true);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, Object> getLongOptsMap() throws IllegalAccessException {
        return (Map) longOptsField.get(options);
    }

    private boolean invokeHasLongOption(String arg) throws Exception {
        // invoke reflectively; handles null correctly
        Object result = hasLongOptionMethod.invoke(options, arg);
        return (Boolean) result;
    }

    @Test
    void testHasLongOptionWithoutHyphen() throws Exception {
        Map<String, Object> longOpts = getLongOptsMap();
        // put a key without hyphen
        longOpts.put("opt", null);
        assertTrue(invokeHasLongOption("opt"), "Expected true for exact long option name");
        // ensure non-existent returns false
        assertFalse(invokeHasLongOption("other"), "Expected false for non-existent option");
    }

    @Test
    void testHasLongOptionWithSingleAndDoubleHyphen() throws Exception {
        Map<String, Object> longOpts = getLongOptsMap();
        longOpts.put("verbose", null);
        // single hyphen
        assertTrue(invokeHasLongOption("-verbose"), "Single leading hyphen should be stripped and match");
        // double hyphen
        assertTrue(invokeHasLongOption("--verbose"), "Double leading hyphen should be stripped and match");
        // plain name also matches
        assertTrue(invokeHasLongOption("verbose"), "Plain name should match");
    }

    @Test
    void testHasLongOptionNotPresent() throws Exception {
        Map<String, Object> longOpts = getLongOptsMap();
        longOpts.clear();
        assertFalse(invokeHasLongOption("missing"), "Missing option should return false");
        assertFalse(invokeHasLongOption("-missing"), "Missing option with hyphen should return false");
        assertFalse(invokeHasLongOption("--missing"), "Missing option with double hyphen should return false");
    }

    @Test
    void testHasLongOptionEmptyAndNullKeys() throws Exception {
        Map<String, Object> longOpts = getLongOptsMap();
        longOpts.clear();
        // add empty-string key and null key to exercise stripLeadingHyphens behavior with empty/null input
        longOpts.put("", null);
        longOpts.put(null, null);
        // empty string: stripLeadingHyphens will return the empty string, so containsKey("") -> true
        assertTrue(invokeHasLongOption(""), "Empty string key should be detected when present");
        // null: stripLeadingHyphens will return null for null input; containsKey(null) -> true
        assertTrue(invokeHasLongOption(null), "Null key should be detected when present");
        // remove null key and verify null now returns false
        longOpts.remove(null);
        assertFalse(invokeHasLongOption(null), "Null key removed should result in false");
    }
}
