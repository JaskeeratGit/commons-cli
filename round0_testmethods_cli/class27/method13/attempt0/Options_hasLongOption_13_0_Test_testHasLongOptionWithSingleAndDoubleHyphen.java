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

class Options_hasLongOption_13_0_Test_testHasLongOptionWithSingleAndDoubleHyphen {

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


}
