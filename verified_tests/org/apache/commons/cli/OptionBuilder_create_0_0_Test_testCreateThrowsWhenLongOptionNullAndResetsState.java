package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("deprecation")
public class OptionBuilder_create_0_0_Test_testCreateThrowsWhenLongOptionNullAndResetsState {

    // Helper to set private static fields on OptionBuilder
    private static void setOptionBuilderField(String name, Object value) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(null, value);
    }

    // Helper to get private static fields on OptionBuilder
    private static Object getOptionBuilderField(String name) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(null);
    }

    // Helper to invoke private static reset() on OptionBuilder
    private static void invokeReset() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    // Helper to get Option.UNINITIALIZED constant reflectively
    private static int getOptionUninitialized() throws Exception {
        try {
            Field uninit = Option.class.getDeclaredField("UNINITIALIZED");
            uninit.setAccessible(true);
            return uninit.getInt(null);
        } catch (NoSuchFieldException e) {
            // fallback if field is not present as declared; try public field
            return Option.UNINITIALIZED;
        }
    }

    @Test
    public void testCreateThrowsWhenLongOptionNullAndResetsState() throws Exception {
        // Ensure initial reset state
        invokeReset();
        // longOption should be null initially
        assertNull(getOptionBuilderField("longOption"));
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            OptionBuilder.create();
        });
        assertEquals("must specify longopt", ex.getMessage());
        // After exception, reset() must have been called and fields set to defaults
        assertNull(getOptionBuilderField("longOption"), "longOption should be reset to null");
        assertNull(getOptionBuilderField("description"), "description should be reset to null");
        assertNull(getOptionBuilderField("argName"), "argName should be reset to null");
        assertEquals(String.class, getOptionBuilderField("type"), "type should be reset to String.class");
        assertEquals(false, getOptionBuilderField("required"), "required should be reset to false");
        assertEquals(false, getOptionBuilderField("optionalArg"), "optionalArg should be reset to false");
        assertEquals((char) 0, getOptionBuilderField("valueSeparator"));
        assertEquals(getOptionUninitialized(), ((Integer) getOptionBuilderField("argCount")).intValue(), "argCount should be reset to Option.UNINITIALIZED");
    }

}
