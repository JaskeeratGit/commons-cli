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
public class OptionBuilder_create_1_0_Test {

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
    public void testCreateWithChar_setsPropertiesAndResets() throws Exception {
        // arrange: set various OptionBuilder static properties
        setOptionBuilderStatic("description", "A description");
        setOptionBuilderStatic("longOption", "long-name");
        setOptionBuilderStatic("argName", "ARG");
        setOptionBuilderStatic("required", true);
        setOptionBuilderStatic("argCount", 2);
        setOptionBuilderStatic("type", String.class);
        setOptionBuilderStatic("optionalArg", true);
        setOptionBuilderStatic("valueSeparator", '=');
        // act: create option using the char-based API
        Option opt = OptionBuilder.create('x');
        // assert: option reflects the configured properties
        assertNotNull(opt, "Option should not be null");
        // opt string should be "x"
        assertEquals("x", opt.getOpt(), "opt string should match provided char");
        assertEquals("A description", opt.getDescription(), "description should be preserved");
        assertEquals("long-name", opt.getLongOpt(), "long option should be preserved");
        assertEquals(2, opt.getArgs(), "arg count should be preserved");
        // type may be stored as Class
        assertEquals(String.class, opt.getType(), "type should be preserved");
        assertEquals("ARG", opt.getArgName(), "argName should be preserved");
        // required
        assertTrue(opt.isRequired(), "option should be required");
        // value separator
        assertEquals('=', opt.getValueSeparator(), "value separator should be preserved");
        // finally: OptionBuilder static fields should have been reset by create(...) finally block
        assertNull(getOptionBuilderStatic("description"), "description static field should be reset to null");
        assertNull(getOptionBuilderStatic("longOption"), "longOption static field should be reset to null");
        assertNull(getOptionBuilderStatic("argName"), "argName static field should be reset to null");
        assertEquals(false, getOptionBuilderStatic("required"), "required static field should be reset to false");
        assertEquals(getOptionUninitialized(), ((Integer) getOptionBuilderStatic("argCount")).intValue(), "argCount static field should be reset");
        assertNull(getOptionBuilderStatic("type"), "type static field should be reset to null");
        assertEquals(false, getOptionBuilderStatic("optionalArg"), "optionalArg static field should be reset to false");
        assertEquals('\0', ((Character) getOptionBuilderStatic("valueSeparator")).charValue(), "valueSeparator static field should be reset to default");
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
