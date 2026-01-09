package org.apache.commons.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for TypeHandler.getConverter when a null key is present in the map.
 */
class TypeHandler_getConverter_14_0_Test_testGetConverterWithNullKeyReturnsMappedConverter {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, Throwable> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Throwable>) Proxy.newProxyInstance(
                Converter.class.getClassLoader(),
                new Class[]{Converter.class},
                handler);
    }

    @Test
    void testGetConverterWithNullKeyReturnsMappedConverter() {
        Converter<Object, Throwable> proxy = createConverterProxy();

        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        // put the proxy for the null key
        map.put(null, proxy);

        TypeHandler handler = new TypeHandler(map);
        Converter<?, ?> result = handler.getConverter(null);

        assertSame(proxy, result, "Expected the converter mapped to null key to be returned");
    }
}
