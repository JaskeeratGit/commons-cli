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

public class Option_hasArgs_27_0_Test {

    @Test
    public void whenNewOption_thenHasArgsIsFalseByDefault() {
        Option opt = new Option("o", "description");
        assertFalse(opt.hasArgs(), "New option with UNINITIALIZED args should not have args");
    }

    @Test
    public void whenSetArgsToZeroOrOne_thenHasArgsIsFalse() {
        Option opt = new Option("a", "description");
        opt.setArgs(0);
        assertFalse(opt.hasArgs(), "argCount == 0 should not be treated as multiple args");
        opt.setArgs(1);
        assertFalse(opt.hasArgs(), "argCount == 1 should not be treated as multiple args");
    }

    @Test
    public void whenSetArgsToTwoOrMore_thenHasArgsIsTrue() {
        Option opt = new Option("b", "description");
        opt.setArgs(2);
        assertTrue(opt.hasArgs(), "argCount == 2 should be treated as multiple args");
        opt.setArgs(10);
        assertTrue(opt.hasArgs(), "argCount > 1 should be treated as multiple args");
    }

    @Test
    public void whenSetArgsToUnlimitedValueConstant_thenHasArgsIsTrue() {
        Option opt = new Option("c", "description");
        opt.setArgs(Option.UNLIMITED_VALUES);
        assertTrue(opt.hasArgs(), "UNLIMITED_VALUES should be treated as having args");
    }

    @Test
    public void reflection_canSetPrivateArgCount_toLargeValue_and_hasArgsIsTrue() throws Exception {
        Option opt = new Option("d", "description");
        Field argCountField = Option.class.getDeclaredField("argCount");
        argCountField.setAccessible(true);
        argCountField.setInt(opt, 100);
        assertTrue(opt.hasArgs(), "Directly setting private argCount > 1 should produce true from hasArgs()");
    }

    @Test
    public void reflection_canSetPrivateArgCount_toUnlimited_and_hasArgsIsTrue() throws Exception {
        Option opt = new Option("e", "description");
        Field argCountField = Option.class.getDeclaredField("argCount");
        argCountField.setAccessible(true);
        argCountField.setInt(opt, Option.UNLIMITED_VALUES);
        assertTrue(opt.hasArgs(), "Directly setting private argCount to UNLIMITED_VALUES should produce true from hasArgs()");
    }
}
