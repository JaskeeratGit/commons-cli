package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_create_2_0_Test_testCreateResetsOnException {

    @BeforeEach
    public void resetOptionBuilderState() throws Exception {
        // invoke private static reset() on OptionBuilder to ensure a clean state
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
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
