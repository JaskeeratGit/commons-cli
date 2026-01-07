package org.apache.commons.cli;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for OptionBuilder.create(char)
 */
public class OptionBuilder_create_1_0_Test_testCreateWithChar_defaultsWhenNotConfigured {

    // Helper to set a private static field on OptionBuilder
    private static void setOptionBuilderStatic(String name, Object value) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(null, value);
    }

    // Helper to get a private static field value from OptionBuilder
    private static Object getOptionBuilderStatic(String name) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(null);
    }

    // Helper to obtain Option.UNINITIALIZED (robustly)
    private static int getOptionUninitialized() throws Exception {
        try {
            Field f = Option.class.getField("UNINITIALIZED");
            f.setAccessible(true);
            return f.getInt(null);
        } catch (NoSuchFieldException e) {
            Field f = Option.class.getDeclaredField("UNINITIALIZED");
            f.setAccessible(true);
            return f.getInt(null);
        }
    }

    // Reset OptionBuilder static fields to their defaults
    private static void resetOptionBuilderToDefaults() throws Exception {
        setOptionBuilderStatic("longOption", null);
        setOptionBuilderStatic("description", null);
        setOptionBuilderStatic("argName", null);
        setOptionBuilderStatic("required", false);
        setOptionBuilderStatic("argCount", getOptionUninitialized());
        setOptionBuilderStatic("type", null);
        setOptionBuilderStatic("optionalArg", false);
        setOptionBuilderStatic("valueSeparator", '\0');
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        resetOptionBuilderToDefaults();
    }

    @AfterEach
    public void afterEach() throws Exception {
        // Ensure clean state after each test
        resetOptionBuilderToDefaults();
    }


    @Test
    public void testCreateWithChar_defaultsWhenNotConfigured() throws Exception {
        // ensure defaults (already set in beforeEach)
        // act
        Option opt = OptionBuilder.create('z');
        // assert option fields reflect defaults
        assertNotNull(opt, "Option should not be null");
        assertEquals("z", opt.getOpt(), "opt string should match provided char");
        assertNull(opt.getDescription(), "description should be null by default");
        assertNull(opt.getLongOpt(), "long option should be null by default");
        assertEquals(getOptionUninitialized(), opt.getArgs(), "arg count should be UNINITIALIZED by default");
        assertNull(opt.getType(), "type should be null by default");
        assertNull(opt.getArgName(), "argName should be null by default");
        assertFalse(opt.isRequired(), "required should be false by default");
        assertEquals('\0', opt.getValueSeparator(), "value separator should be default char (0)");
        // verify OptionBuilder static fields remain default (create should reset them)
        assertNull(getOptionBuilderStatic("description"));
        assertNull(getOptionBuilderStatic("longOption"));
        assertNull(getOptionBuilderStatic("argName"));
        assertEquals(false, getOptionBuilderStatic("required"));
        assertEquals(getOptionUninitialized(), ((Integer) getOptionBuilderStatic("argCount")).intValue());
        assertNull(getOptionBuilderStatic("type"));
        assertEquals(false, getOptionBuilderStatic("optionalArg"));
        assertEquals('\0', ((Character) getOptionBuilderStatic("valueSeparator")).charValue());
    }
}
