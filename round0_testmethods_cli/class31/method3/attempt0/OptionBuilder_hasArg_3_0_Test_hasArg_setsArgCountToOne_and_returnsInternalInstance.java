package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_hasArg_3_0_Test_hasArg_setsArgCountToOne_and_returnsInternalInstance {

    @BeforeEach
    void setUp() throws Exception {
        invokeReset();
    }

    @AfterEach
    void tearDown() throws Exception {
        invokeReset();
    }

    @Test
    void hasArg_setsArgCountToOne_and_returnsInternalInstance() throws Exception {
        // Ensure argCount starts with a non-1 value
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        argCountField.setAccessible(true);
        // set to something else
        argCountField.setInt(null, 99);
        // obtain the private INSTANCE for identity comparison
        Field instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        Object internalInstance = instanceField.get(null);
        OptionBuilder returned = OptionBuilder.hasArg();
        // returned object should be the same singleton INSTANCE
        assertSame(internalInstance, returned, "hasArg() should return the internal INSTANCE");
        // argCount should be set to 1
        assertEquals(1, argCountField.getInt(null), "argCount must be set to 1 by hasArg()");
    }


    // Helper to invoke the private static reset() method reflectively
    private static void invokeReset() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }
}
