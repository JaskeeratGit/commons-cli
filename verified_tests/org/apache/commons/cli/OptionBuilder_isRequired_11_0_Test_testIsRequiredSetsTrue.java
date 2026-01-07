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
public class OptionBuilder_isRequired_11_0_Test_testIsRequiredSetsTrue {

    @BeforeEach
    public void beforeEach() throws Exception {
        invokeReset();
    }

    @AfterEach
    public void afterEach() throws Exception {
        invokeReset();
    }

    @Test
    public void testIsRequiredSetsTrue() throws Exception {
        // precondition: required should be default false
        assertFalse(getRequiredFlag(), "Precondition: required should start false");
        OptionBuilder returned = OptionBuilder.isRequired(true);
        // verify returned instance is the singleton INSTANCE
        Object instance = getInstance();
        assertSame(instance, returned, "isRequired should return the singleton INSTANCE");
        // verify the private static field 'required' was set to true
        assertTrue(getRequiredFlag(), "required should be set to true after isRequired(true)");
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
