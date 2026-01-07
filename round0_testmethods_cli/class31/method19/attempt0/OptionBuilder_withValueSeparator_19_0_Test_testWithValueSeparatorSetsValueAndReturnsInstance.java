package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_withValueSeparator_19_0_Test_testWithValueSeparatorSetsValueAndReturnsInstance {

    @BeforeEach
    public void beforeEach() throws Exception {
        invokeReset();
    }

    @AfterEach
    public void afterEach() throws Exception {
        invokeReset();
    }

    @Test
    public void testWithValueSeparatorSetsValueAndReturnsInstance() throws Exception {
        char sep = '=';
        OptionBuilder returned = OptionBuilder.withValueSeparator(sep);
        OptionBuilder instance = getInstanceViaReflection();
        assertSame(instance, returned, "withValueSeparator should return the singleton INSTANCE");
        assertEquals(sep, getValueSeparatorViaReflection(), "valueSeparator field should be set to provided char");
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
