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
class OptionBuilder_hasOptionalArgs_9_0_Test_testHasOptionalArgsSetsArgCountAndOptionalFlagAndReturnsInstance {

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



}
