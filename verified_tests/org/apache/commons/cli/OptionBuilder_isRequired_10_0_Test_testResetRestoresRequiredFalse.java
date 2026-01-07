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
public class OptionBuilder_isRequired_10_0_Test_testResetRestoresRequiredFalse {

    @BeforeEach
    void setUp() throws Exception {
        resetOptionBuilder();
    }




    @Test
    void testResetRestoresRequiredFalse() throws Exception {
        Class<OptionBuilder> cls = OptionBuilder.class;
        // set required to true
        OptionBuilder.isRequired();
        assertTrue(getRequiredFlag(cls), "precondition: required should be true after isRequired()");
        // call private reset() via reflection
        resetOptionBuilder();
        // required should be reset to false
        assertFalse(getRequiredFlag(cls), "required should be false after reset()");
    }

    // Helper to read the private static boolean 'required' field
    private boolean getRequiredFlag(Class<OptionBuilder> cls) throws Exception {
        Field requiredField = cls.getDeclaredField("required");
        requiredField.setAccessible(true);
        return requiredField.getBoolean(null);
    }

    // Helper to invoke the private static reset() method
    private void resetOptionBuilder() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }
}
