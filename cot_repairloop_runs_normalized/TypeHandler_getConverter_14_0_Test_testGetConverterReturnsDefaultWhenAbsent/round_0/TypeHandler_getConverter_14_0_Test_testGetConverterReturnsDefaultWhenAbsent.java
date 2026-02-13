package org.apache.commons.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_getConverter_14_0_Test_testGetConverterReturnsDefaultWhenAbsent {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, Exception> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Exception>) Proxy.newProxyInstance(Converter.class.getClassLoader(),
                new Class[] { Converter.class }, handler);
    }

    @Test
    void testGetConverterReturnsDefaultWhenAbsent() {
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Exception>> map = new HashMap<>();
        TypeHandler handler = new TypeHandler(map);
        Converter<?, ?> result = handler.getConverter(Integer.class);
        assertSame(Converter.DEFAULT, result, "Expected Converter.DEFAULT when no mapping exists");
    }

}
