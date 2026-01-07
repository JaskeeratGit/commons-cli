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
public class OptionBuilder_isRequired_11_0_Test_testMultipleCallsReturnSameInstanceAndToggleFlag {

    @BeforeEach
    public void beforeEach() throws Exception {
        invokeReset();
    }

    @AfterEach
    public void afterEach() throws Exception {
        invokeReset();
    }



    @Test
    public void testMultipleCallsReturnSameInstanceAndToggleFlag() throws Exception {
        Object instance = getInstance();
        OptionBuilder first = OptionBuilder.isRequired(true);
        assertSame(instance, first, "First call should return the singleton INSTANCE");
        assertTrue(getRequiredFlag(), "required should be true after first call");
        OptionBuilder second = OptionBuilder.isRequired(false);
        assertSame(instance, second, "Second call should return the same singleton INSTANCE");
        assertSame(first, second, "Both calls should return the identical instance");
        assertFalse(getRequiredFlag(), "required should be false after second call");
    }


    // ---------- Reflection helpers ----------
    private static boolean getRequiredFlag() throws Exception {
        Field requiredField = OptionBuilder.class.getDeclaredField("required");
        requiredField.setAccessible(true);
        return requiredField.getBoolean(null);
    }

    private static Object getInstance() throws Exception {
        Field instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        return instanceField.get(null);
    }

    private static void invokeReset() throws Exception {
        Method resetMethod = OptionBuilder.class.getDeclaredMethod("reset");
        resetMethod.setAccessible(true);
        resetMethod.invoke(null);
    }
}
