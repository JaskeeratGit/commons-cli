package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_create_2_0_Test {

    @BeforeEach
    public void resetOptionBuilderState() throws Exception {
        // invoke private static reset() on OptionBuilder to ensure a clean state
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    @Test
    public void testCreateSetsOptionProperties() throws Exception {
        // prepare values to set on OptionBuilder's private static fields
        String expectedDescription = "a description";
        String expectedLongOpt = "long-option";
        String expectedArgName = "ARG";
        boolean expectedRequired = true;
        int expectedArgCount = 3;
        Class<?> expectedType = Integer.class;
        boolean expectedOptionalArg = true;
        char expectedValueSeparator = ';';
        // set private static fields via reflection
        setStaticField("description", expectedDescription);
        setStaticField("longOption", expectedLongOpt);
        setStaticField("argName", expectedArgName);
        setStaticField("required", expectedRequired);
        setStaticField("argCount", expectedArgCount);
        setStaticField("type", expectedType);
        setStaticField("optionalArg", expectedOptionalArg);
        setStaticField("valueSeparator", expectedValueSeparator);
        // call the focal method
        Option opt = OptionBuilder.create("o");
        // verify Option instance fields were set from OptionBuilder
        assertNotNull(opt, "Option should not be null");
        assertEquals(expectedDescription, opt.getDescription(), "description should be propagated");
        assertEquals(expectedLongOpt, opt.getLongOpt(), "long option should be propagated");
        assertEquals(expectedArgName, opt.getArgName(), "arg name should be propagated");
        assertEquals(expectedArgCount, opt.getArgs(), "arg count should be propagated");
        assertEquals(expectedType, opt.getType(), "type should be propagated");
        assertEquals(expectedValueSeparator, opt.getValueSeparator(), "value separator should be propagated");
        // verify OptionBuilder was reset to defaults after successful create
        assertEquals(null, getStaticField("description"), "description should be reset to null");
        assertEquals(null, getStaticField("argName"), "argName should be reset to null");
        assertEquals(null, getStaticField("longOption"), "longOption should be reset to null");
        assertEquals(String.class, getStaticField("type"), "type should be reset to String.class");
        assertEquals(false, getStaticField("required"), "required should be reset to false");
        assertEquals(Option.UNINITIALIZED, getStaticField("argCount"), "argCount should be reset to UNINITIALIZED");
        assertEquals(false, getStaticField("optionalArg"), "optionalArg should be reset to false");
        assertEquals((char) 0, getStaticField("valueSeparator"), "valueSeparator should be reset to 0");
    }

    @Test
    public void testCreateResetsOnException() throws Exception {
        // set some non-default values to ensure reset happens even when create throws
        setStaticField("description", "will be cleared");
        setStaticField("longOption", "will-be-cleared");
        setStaticField("argName", "X");
        setStaticField("required", true);
        setStaticField("argCount", 2);
        setStaticField("type", Double.class);
        setStaticField("optionalArg", true);
        setStaticField("valueSeparator", ':');
        // calling create with a null opt is expected to result in IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> OptionBuilder.create(null));
        // verify OptionBuilder was reset to defaults after exception
        assertEquals(null, getStaticField("description"), "description should be reset to null after exception");
        assertEquals(null, getStaticField("argName"), "argName should be reset to null after exception");
        assertEquals(null, getStaticField("longOption"), "longOption should be reset to null after exception");
        assertEquals(String.class, getStaticField("type"), "type should be reset to String.class after exception");
        assertEquals(false, getStaticField("required"), "required should be reset to false after exception");
        assertEquals(Option.UNINITIALIZED, getStaticField("argCount"), "argCount should be reset to UNINITIALIZED after exception");
        assertEquals(false, getStaticField("optionalArg"), "optionalArg should be reset to false after exception");
        assertEquals((char) 0, getStaticField("valueSeparator"), "valueSeparator should be reset to 0 after exception");
    }

    // Helper to set private static fields on OptionBuilder
    private void setStaticField(String fieldName, Object value) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        // primitive handling for char and boolean and int
        if (f.getType().isPrimitive()) {
            if (f.getType() == int.class) {
                f.setInt(null, (Integer) value);
                return;
            } else if (f.getType() == boolean.class) {
                f.setBoolean(null, (Boolean) value);
                return;
            } else if (f.getType() == char.class) {
                f.setChar(null, (Character) value);
                return;
            }
        }
        f.set(null, value);
    }

    // Helper to get private static field values from OptionBuilder
    private Object getStaticField(String fieldName) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(null);
    }
}
