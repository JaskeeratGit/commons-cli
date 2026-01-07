package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

class Option_hasArgName_26_0_Test {

    @Test
    void hasArgName_whenArgNameIsNull_returnsFalse() throws Exception {
        Option opt = new Option("a", "desc");
        // ensure argName is null (default)
        opt.setArgName(null);
        // direct API check
        assertFalse(opt.hasArgName());
        // reflective invocation of the method (declared method)
        Method hasArgName = Option.class.getDeclaredMethod("hasArgName");
        hasArgName.setAccessible(true);
        Object result = hasArgName.invoke(opt);
        assertTrue(result instanceof Boolean);
        assertFalse((Boolean) result);
    }

    @Test
    void hasArgName_whenArgNameIsEmpty_returnsFalse() throws Exception {
        Option opt = new Option("b", "desc");
        opt.setArgName("");
        // direct API check
        assertFalse(opt.hasArgName());
        // also verify the private field actually contains the empty string via reflection
        Field argNameField = Option.class.getDeclaredField("argName");
        argNameField.setAccessible(true);
        Object fieldValue = argNameField.get(opt);
        assertEquals("", fieldValue);
    }

    @Test
    void hasArgName_whenArgNameIsNonEmpty_returnsTrue() throws Exception {
        Option opt = new Option("c", "desc");
        opt.setArgName("ARG");
        // direct API check
        assertTrue(opt.hasArgName());
        // reflective invocation of the method
        Method hasArgName = Option.class.getDeclaredMethod("hasArgName");
        hasArgName.setAccessible(true);
        Object result = hasArgName.invoke(opt);
        assertTrue((Boolean) result);
    }

    @Test
    void hasArgName_whenArgNameIsWhitespace_returnsTrue() throws Exception {
        Option opt = new Option("d", "desc");
        opt.setArgName(" ");
        // whitespace is not empty, so should return true
        assertTrue(opt.hasArgName());
        // confirm via reflected field value
        Field argNameField = Option.class.getDeclaredField("argName");
        argNameField.setAccessible(true);
        assertEquals(" ", argNameField.get(opt));
    }
}
