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
public class OptionBuilder_create_0_0_Test_testCreateWithLongOptionCreatesOptionAndResetsState {

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
    public void testCreateWithLongOptionCreatesOptionAndResetsState() throws Exception {
        // Set up OptionBuilder static fields to non-default values
        setOptionBuilderField("longOption", "myLongOpt");
        setOptionBuilderField("description", "a description");
        setOptionBuilderField("argName", "theArg");
        setOptionBuilderField("required", true);
        setOptionBuilderField("argCount", 2);
        setOptionBuilderField("type", Integer.class);
        setOptionBuilderField("optionalArg", true);
        setOptionBuilderField("valueSeparator", ':');
        // Call create() which should delegate to create(null) and then reset() in finally
        Option opt = OptionBuilder.create();
        // Option should be returned (not null)
        assertNotNull(opt, "create() should return an Option instance");
        // After successful creation, OptionBuilder state must be reset to defaults
        assertNull(getOptionBuilderField("longOption"), "longOption should be reset to null after create");
        assertNull(getOptionBuilderField("description"), "description should be reset to null after create");
        assertNull(getOptionBuilderField("argName"), "argName should be reset to null after create");
        assertEquals(String.class, getOptionBuilderField("type"), "type should be reset to String.class after create");
        assertEquals(false, getOptionBuilderField("required"), "required should be reset to false after create");
        assertEquals(false, getOptionBuilderField("optionalArg"), "optionalArg should be reset to false after create");
        assertEquals((char) 0, getOptionBuilderField("valueSeparator"));
        assertEquals(getOptionUninitialized(), ((Integer) getOptionBuilderField("argCount")).intValue(), "argCount should be reset to Option.UNINITIALIZED after create");
    }
}
