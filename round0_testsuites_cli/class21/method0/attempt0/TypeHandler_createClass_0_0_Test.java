package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class TypeHandler_createClass_0_0_Test {

    @Test
    void testCreateClassWithFullyQualifiedName() throws Exception {
        // direct public API
        Class<?> cls = TypeHandler.createClass("java.lang.String");
        assertEquals(String.class, cls);
    }

    @Test
    void testCreateClassWithOwnClassName() throws Exception {
        // ensure it can resolve the focal class itself
        Class<?> cls = TypeHandler.createClass("org.apache.commons.cli.TypeHandler");
        assertEquals(TypeHandler.class, cls);
    }

    @Test
    void testCreateClassInvalidNameThrowsParseException() {
        assertThrows(ParseException.class, () -> TypeHandler.createClass("no.such.package.DoesNotExist"));
    }

    @Test
    void testPrivateCreateValueViaReflectionReturnsExpectedClass() throws Exception {
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        Object result = createValue.invoke(null, "java.lang.Integer", Class.class);
        assertNotNull(result);
        assertTrue(result instanceof Class);
        assertEquals(Integer.class, result);
    }

    @Test
    void testPrivateCreateValueInvalidNameViaReflectionThrowsParseExceptionWrapped() throws Exception {
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> createValue.invoke(null, "com.example.DoesNotExist", Class.class));
        // underlying cause should be a ParseException (as per focal method contract)
        assertNotNull(ite.getCause());
        assertTrue(ite.getCause() instanceof ParseException);
    }

    @Test
    void testGetDefaultReturnsNonNull() {
        // Sanity check for other provided API; ensures static initialization path is exercised
        TypeHandler defaultHandler = TypeHandler.getDefault();
        assertNotNull(defaultHandler);
    }
}
