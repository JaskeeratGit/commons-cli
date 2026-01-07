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
public class OptionBuilder_isRequired_10_0_Test_testIsRequiredReturnsSingletonInstance {

    @BeforeEach
    void setUp() throws Exception {
        resetOptionBuilder();
    }


    @Test
    void testIsRequiredReturnsSingletonInstance() throws Exception {
        Class<OptionBuilder> cls = OptionBuilder.class;
        // obtain the private INSTANCE field
        Field instanceField = cls.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        Object instance = instanceField.get(null);
        // call isRequired() and ensure the same instance is returned
        Object returned = OptionBuilder.isRequired();
        assertSame(instance, returned, "isRequired() should return the singleton INSTANCE");
        // subsequent calls return same instance
        Object returned2 = OptionBuilder.isRequired();
        assertSame(instance, returned2, "subsequent isRequired() calls should return the same INSTANCE");
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
