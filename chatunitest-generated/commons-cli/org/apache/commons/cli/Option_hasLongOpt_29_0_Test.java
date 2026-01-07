package org.apache.commons.cli;

import java.lang.reflect.Field;
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

class Option_hasLongOpt_29_0_Test {

    @Test
    void testHasLongOpt_defaultConstructor_noLongOpt() throws Exception {
        // create option with only short option and description
        Option opt = new Option("o", "option description");
        assertFalse(opt.hasLongOpt(), "Expected no long option by default");
        assertNull(opt.getLongOpt(), "getLongOpt should return null when none set");
    }

    @Test
    void testHasLongOpt_constructorWithLongOpt_present() throws Exception {
        // create option with a long option via constructor
        Option opt = new Option("o", "long-opt", false, "option description");
        assertTrue(opt.hasLongOpt(), "Expected hasLongOpt to be true when constructed with a long option");
        assertEquals("long-opt", opt.getLongOpt(), "getLongOpt should return the long option set in constructor");
    }

    @Test
    void testHasLongOpt_setLongOpt_toNull_becomesFalse() throws Exception {
        Option opt = new Option("o", "long-opt", false, "option description");
        assertTrue(opt.hasLongOpt());
        // set long option to null via setter
        opt.setLongOpt(null);
        assertFalse(opt.hasLongOpt(), "hasLongOpt should be false after setting long option to null");
        assertNull(opt.getLongOpt(), "getLongOpt should return null after setting to null");
    }

    @Test
    void testHasLongOpt_setLongOpt_toEmptyString_isTrue() throws Exception {
        Option opt = new Option("o", "option description");
        assertFalse(opt.hasLongOpt());
        // empty string is still non-null -> should be considered as having a long option
        opt.setLongOpt("");
        assertTrue(opt.hasLongOpt(), "hasLongOpt should be true when long option is an empty string");
        assertEquals("", opt.getLongOpt(), "getLongOpt should return the empty string set");
    }

    @Test
    void testHasLongOpt_reflection_modifyPrivateField_toNonNull() throws Exception {
        Option opt = new Option("x", "option description");
        assertFalse(opt.hasLongOpt());
        // Use reflection to set the private field 'longOption'
        Field longOptField = Option.class.getDeclaredField("longOption");
        longOptField.setAccessible(true);
        longOptField.set(opt, "reflected-long");
        // now hasLongOpt should reflect the private field change
        assertTrue(opt.hasLongOpt(), "hasLongOpt should be true after reflection sets private longOption");
        assertEquals("reflected-long", opt.getLongOpt());
    }

    @Test
    void testHasLongOpt_reflection_modifyPrivateField_toNull() throws Exception {
        Option opt = new Option("x", "ref-long", false, "desc");
        assertTrue(opt.hasLongOpt());
        Field longOptField = Option.class.getDeclaredField("longOption");
        longOptField.setAccessible(true);
        longOptField.set(opt, null);
        assertFalse(opt.hasLongOpt(), "hasLongOpt should be false after reflection sets private longOption to null");
        assertNull(opt.getLongOpt());
    }
}
