package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for OptionBuilder.hasOptionalArg()
 */
public class OptionBuilder_hasOptionalArg_7_0_Test_testHasOptionalArgReturnsSingletonInstance {

    @BeforeEach
    public void resetState() throws Exception {
        // Invoke private static reset() to ensure deterministic starting state.
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }


    @Test
    public void testHasOptionalArgReturnsSingletonInstance() throws Exception {
        // Act
        OptionBuilder first = OptionBuilder.hasOptionalArg();
        OptionBuilder second = OptionBuilder.hasOptionalArg();
        // Assert that repeated calls return the same instance (singleton)
        assertSame(first, second, "hasOptionalArg() should return the same singleton instance on subsequent calls");
        // Also verify the returned instance is the private INSTANCE field
        Object instanceField = getStaticField("INSTANCE");
        assertSame(instanceField, first, "Returned instance should be identical to private INSTANCE field");
    }

    // Reflection helpers
    private Object getStaticField(String fieldName) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(null);
    }

    private void setStaticField(String fieldName, Object value) throws Exception {
        Field f = OptionBuilder.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, value);
    }
}
