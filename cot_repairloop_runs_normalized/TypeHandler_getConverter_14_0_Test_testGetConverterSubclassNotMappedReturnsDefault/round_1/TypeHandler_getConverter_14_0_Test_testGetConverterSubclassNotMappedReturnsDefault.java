package org.apache.commons.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;

class TypeHandler_getConverter_14_0_Test_testGetConverterSubclassNotMappedReturnsDefault {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, Exception> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Exception>) Proxy.newProxyInstance(Converter.class.getClassLoader(),
                new Class[] { Converter.class }, handler);
    }

    @Test
    void testGetConverterSubclassNotMappedReturnsDefault() {
        Converter<Object, Exception> numberConverter = createConverterProxy();
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        // map Number.class only
        map.put(Number.class, (Converter<?, ? extends Throwable>) numberConverter);
        TypeHandler handler = new TypeHandler(map);
        // Integer.class is not mapped exactly, expect DEFAULT
        Converter<?, ?> result = handler.getConverter(Integer.class);
        assertSame(Converter.DEFAULT, result, "Expected Converter.DEFAULT when subclass key is not present");
    }

}
