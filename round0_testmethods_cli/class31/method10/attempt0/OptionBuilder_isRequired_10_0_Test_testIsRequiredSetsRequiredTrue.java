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
public class OptionBuilder_isRequired_10_0_Test_testIsRequiredSetsRequiredTrue {

    @BeforeEach
    void setUp() throws Exception {
        resetOptionBuilder();
    }

    @Test
    void testIsRequiredSetsRequiredTrue() throws Exception {
        Class<OptionBuilder> cls = OptionBuilder.class;
        // ensure initial state is false
        assertFalse(getRequiredFlag(cls), "required should be false before calling isRequired()");
        // call focal method
        OptionBuilder result = OptionBuilder.isRequired();
        // returned instance should not be null
        assertNotNull(result, "isRequired() should return an OptionBuilder instance");
        // required must be set to true
        assertTrue(getRequiredFlag(cls), "required should be true after calling isRequired()");
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
