package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("deprecation")
class OptionBuilder_hasOptionalArgs_9_0_Test {

    @BeforeEach
    void resetOptionBuilder() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    @Test
    void testHasOptionalArgsSetsArgCountAndOptionalFlagAndReturnsInstance() throws Exception {
        // act
        OptionBuilder returned = OptionBuilder.hasOptionalArgs(3);
        // reflectively read private static fields
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        Field instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        argCountField.setAccessible(true);
        optionalArgField.setAccessible(true);
        instanceField.setAccessible(true);
        int argCountValue = argCountField.getInt(null);
        boolean optionalArgValue = optionalArgField.getBoolean(null);
        Object instanceValue = instanceField.get(null);
        // asserts
        assertEquals(3, argCountValue, "argCount should be set to the provided value");
        assertTrue(optionalArgValue, "optionalArg should be set to true");
        assertSame(instanceValue, returned, "hasOptionalArgs should return the singleton INSTANCE");
    }

    @Test
    void testHasOptionalArgsWithZeroAndNegativeValues() throws Exception {
        // zero
        OptionBuilder.hasOptionalArgs(0);
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        argCountField.setAccessible(true);
        optionalArgField.setAccessible(true);
        assertEquals(0, argCountField.getInt(null), "argCount should be 0 when called with 0");
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should be true after call");
        // negative
        OptionBuilder.hasOptionalArgs(-1);
        assertEquals(-1, argCountField.getInt(null), "argCount should accept negative values as-is");
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should remain true after call");
    }

    @Test
    void testMultipleCallsOverwriteArgCount() throws Exception {
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        argCountField.setAccessible(true);
        optionalArgField.setAccessible(true);
        OptionBuilder.hasOptionalArgs(2);
        assertEquals(2, argCountField.getInt(null));
        assertTrue(optionalArgField.getBoolean(null));
        OptionBuilder.hasOptionalArgs(5);
        assertEquals(5, argCountField.getInt(null), "later calls should overwrite argCount");
        assertTrue(optionalArgField.getBoolean(null));
    }

    @Test
    void testResetClearsOptionalFlagAfterHasOptionalArgs() throws Exception {
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        optionalArgField.setAccessible(true);
        // set state to optional
        OptionBuilder.hasOptionalArgs(4);
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should be true after hasOptionalArgs");
        // invoke private reset()
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
        // after reset optionalArg should be back to default (false)
        assertFalse(optionalArgField.getBoolean(null), "optionalArg should be false after reset");
    }
}
