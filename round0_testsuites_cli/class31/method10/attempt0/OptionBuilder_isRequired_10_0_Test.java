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
public class OptionBuilder_isRequired_10_0_Test {

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
