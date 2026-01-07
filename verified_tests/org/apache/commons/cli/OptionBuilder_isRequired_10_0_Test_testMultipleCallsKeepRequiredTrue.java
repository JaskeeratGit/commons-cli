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
public class OptionBuilder_isRequired_10_0_Test_testMultipleCallsKeepRequiredTrue {

    @BeforeEach
    void setUp() throws Exception {
        resetOptionBuilder();
    }



    @Test
    void testMultipleCallsKeepRequiredTrue() throws Exception {
        Class<OptionBuilder> cls = OptionBuilder.class;
        // call multiple times
        OptionBuilder.isRequired();
        OptionBuilder.isRequired();
        OptionBuilder.isRequired();
        // required remains true
        assertTrue(getRequiredFlag(cls), "required should remain true after multiple isRequired() calls");
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
