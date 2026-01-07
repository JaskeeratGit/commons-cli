package org.apache.commons.cli;

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

/**
 * Generated tests for TypeHandler.createValue(String, Object) (deprecated overload).
 * Tests use reflection to reach the private overload and exercise casting behavior.
 */
public class TypeHandler_createValue_9_0_Test {

    @Test
    @SuppressWarnings("deprecation")
    public void testCreateValue_withStringClass_returnsInput() throws Throwable {
        // The deprecated overload simply delegates to the Class-based overload.
        // For String.class we expect the input string to be returned.
        Object result = TypeHandler.createValue("hello", String.class);
        assertEquals("hello", result);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testCreateValue_withNonClass_throwsClassCastException() {
        // Passing a non-Class object should cause a ClassCastException when the method casts.
        assertThrows(ClassCastException.class, () -> TypeHandler.createValue("value", new Object()));
    }

    @Test
    public void testPrivateCreateValue_viaReflection_stringClass() throws Throwable {
        // Use reflection to invoke the private static createValue(String, Class) overload directly.
        Method privateCreate = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        privateCreate.setAccessible(true);
        Object invoked = privateCreate.invoke(null, "reflect", String.class);
        assertEquals("reflect", invoked);
    }
}
