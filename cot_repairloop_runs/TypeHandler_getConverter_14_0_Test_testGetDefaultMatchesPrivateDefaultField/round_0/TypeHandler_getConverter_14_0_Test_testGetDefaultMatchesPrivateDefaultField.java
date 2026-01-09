package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit test for verifying that TypeHandler.getDefault() returns the same
 * instance as the private static DEFAULT field.
 */
class TypeHandler_getConverter_14_0_Test_testGetDefaultMatchesPrivateDefaultField {

    // Helper to create a Converter proxy instance (kept for completeness).
    // Use Exception as the second type parameter to satisfy the type bounds.
    @SuppressWarnings("unchecked")
    private Converter<Object, Exception> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Exception>) Proxy.newProxyInstance(
                Converter.class.getClassLoader(),
                new Class[] { Converter.class },
                handler);
    }

    @Test
    void testGetDefaultMatchesPrivateDefaultField() throws Exception {
        // Use reflection to read the private static DEFAULT field
        Field defaultField = TypeHandler.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        Object privateDefault = defaultField.get(null);

        // Validate getDefault() returns the same instance
        TypeHandler viaMethod = TypeHandler.getDefault();
        assertSame(privateDefault, viaMethod, "TypeHandler.getDefault() should return the private DEFAULT instance");
    }
}
