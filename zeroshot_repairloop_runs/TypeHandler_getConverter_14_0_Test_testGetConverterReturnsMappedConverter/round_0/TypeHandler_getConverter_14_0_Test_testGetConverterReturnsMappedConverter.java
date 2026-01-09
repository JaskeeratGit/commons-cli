package org.apache.commons.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_getConverter_14_0_Test_testGetConverterReturnsMappedConverter {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, ?> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, ?>) Proxy.newProxyInstance(
                Converter.class.getClassLoader(),
                new Class[] { Converter.class },
                handler);
    }

    @Test
    void testGetConverterReturnsMappedConverter() {
        Converter<Object, ?> proxy = createConverterProxy();
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        // cast to the map's expected type
        map.put(String.class, (Converter<?, ? extends Throwable>) proxy);
        TypeHandler handler = new TypeHandler(map);
        Converter<?, ?> result = handler.getConverter(String.class);
        assertSame(proxy, result, "Expected the mapped converter to be returned");
    }
}
