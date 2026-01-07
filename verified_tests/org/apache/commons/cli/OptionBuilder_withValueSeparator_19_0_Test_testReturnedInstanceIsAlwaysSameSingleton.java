package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_withValueSeparator_19_0_Test_testReturnedInstanceIsAlwaysSameSingleton {

    @BeforeEach
    public void beforeEach() throws Exception {
        invokeReset();
    }

    @AfterEach
    public void afterEach() throws Exception {
        invokeReset();
    }



    @Test
    public void testReturnedInstanceIsAlwaysSameSingleton() throws Exception {
        OptionBuilder first = OptionBuilder.withValueSeparator('x');
        OptionBuilder second = OptionBuilder.withValueSeparator('y');
        assertSame(first, second, "Repeated calls should return the same singleton instance");
        // also verify the singleton matches the private INSTANCE field
        OptionBuilder instance = getInstanceViaReflection();
        assertSame(instance, first, "Returned instance should be the private INSTANCE singleton");
    }

    // --- Reflection helpers ---
    private static char getValueSeparatorViaReflection() throws Exception {
        Field f = OptionBuilder.class.getDeclaredField("valueSeparator");
        f.setAccessible(true);
        // static field
        return f.getChar(null);
    }

    private static OptionBuilder getInstanceViaReflection() throws Exception {
        Field f = OptionBuilder.class.getDeclaredField("INSTANCE");
        f.setAccessible(true);
        return (OptionBuilder) f.get(null);
    }

    private static void invokeReset() throws Exception {
        Method m = OptionBuilder.class.getDeclaredMethod("reset");
        m.setAccessible(true);
        m.invoke(null);
    }
}
