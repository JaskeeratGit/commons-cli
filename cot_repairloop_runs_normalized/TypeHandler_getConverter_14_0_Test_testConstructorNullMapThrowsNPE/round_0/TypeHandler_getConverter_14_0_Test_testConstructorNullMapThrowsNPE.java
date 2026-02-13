package org.apache.commons.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_getConverter_14_0_Test_testConstructorNullMapThrowsNPE {

    // Helper to create a Converter proxy instance
    @SuppressWarnings("unchecked")
    private Converter<Object, Exception> createConverterProxy() {
        InvocationHandler handler = (proxy, method, args) -> null;
        return (Converter<Object, Exception>) Proxy.newProxyInstance(Converter.class.getClassLoader(),
                new Class[] { Converter.class }, handler);
    }

    @Test
    void testConstructorNullMapThrowsNPE() {
        assertThrows(NullPointerException.class, () -> new TypeHandler(null));
    }

}
