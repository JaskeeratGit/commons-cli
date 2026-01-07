package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_hasArg_3_0_Test_hasArg_returnsSameInstance_onMultipleCalls {

    @BeforeEach
    void setUp() throws Exception {
        invokeReset();
    }

    @AfterEach
    void tearDown() throws Exception {
        invokeReset();
    }


    @Test
    void hasArg_returnsSameInstance_onMultipleCalls() throws Exception {
        // obtain the private INSTANCE for identity comparison
        Field instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        Object internalInstance = instanceField.get(null);
        OptionBuilder first = OptionBuilder.hasArg();
        OptionBuilder second = OptionBuilder.hasArg();
        // both calls must return the same object and equal the internal INSTANCE
        assertSame(first, second, "Multiple hasArg() calls must return the same object");
        assertSame(internalInstance, first, "Returned object must be the internal INSTANCE");
    }

    // Helper to invoke the private static reset() method reflectively
    private static void invokeReset() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }
}
