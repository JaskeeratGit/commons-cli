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
public class OptionBuilder_isRequired_11_0_Test_testIsRequiredSetsFalseAfterTrue {

    @BeforeEach
    public void beforeEach() throws Exception {
        invokeReset();
    }

    @AfterEach
    public void afterEach() throws Exception {
        invokeReset();
    }


    @Test
    public void testIsRequiredSetsFalseAfterTrue() throws Exception {
        // set to true first
        OptionBuilder.isRequired(true);
        assertTrue(getRequiredFlag(), "required should be true after isRequired(true)");
        // then set to false
        OptionBuilder.isRequired(false);
        assertFalse(getRequiredFlag(), "required should be false after isRequired(false)");
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
