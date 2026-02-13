package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

class TypeHandler_getConverter_14_0_Test_testGetDefaultMatchesPrivateDefaultField {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, Exception> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Exception>) Proxy.newProxyInstance(Converter.class.getClassLoader(), new Class[] { Converter.class }, handler);
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
