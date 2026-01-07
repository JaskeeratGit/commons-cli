package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class TypeHandler_createDate_1_0_Test {

    private static Method createValueUncheckedMethod;

    @BeforeAll
    static void setUpReflection() throws Exception {
        // locate the private static method createValueUnchecked(String, Class)
        createValueUncheckedMethod = TypeHandler.class.getDeclaredMethod("createValueUnchecked", String.class, Class.class);
        createValueUncheckedMethod.setAccessible(true);
    }

    @Test
    void testCreateValueUncheckedMethodSignature_isPrivateAndStatic() {
        int mods = createValueUncheckedMethod.getModifiers();
        assertTrue(Modifier.isPrivate(mods), "createValueUnchecked should be private");
        assertTrue(Modifier.isStatic(mods), "createValueUnchecked should be static");
        Class<?>[] params = createValueUncheckedMethod.getParameterTypes();
        assertEquals(2, params.length);
        assertEquals(String.class, params[0]);
        assertEquals(Class.class, params[1]);
    }

    @Test
    void testCreateDateDelegatesToCreateValueUnchecked_withSampleString() throws Exception {
        String input = "sample-input-123";
        // Invoke via reflection
        Object reflectResult = null;
        Throwable reflectThrowable = null;
        try {
            reflectResult = createValueUncheckedMethod.invoke(null, input, Date.class);
        } catch (InvocationTargetException ite) {
            reflectThrowable = ite.getCause();
        }
        // Invoke via public API
        Object publicResult = null;
        Throwable publicThrowable = null;
        try {
            publicResult = TypeHandler.createDate(input);
        } catch (Throwable t) {
            publicThrowable = t;
        }
        // If both threw exceptions, ensure they are of the same type (delegation)
        if (reflectThrowable != null || publicThrowable != null) {
            assertNotNull(reflectThrowable, "Reflective invocation should have produced an exception when public invocation did");
            assertNotNull(publicThrowable, "Public invocation should have produced an exception when reflective invocation did");
            assertEquals(reflectThrowable.getClass(), publicThrowable.getClass(), "createDate should delegate to createValueUnchecked (exception types differ)");
        } else {
            // both returned normally; results should be equal
            assertEquals(reflectResult, publicResult, "createDate should return the same value as createValueUnchecked");
            // Additionally assert result is either null or a Date instance
            if (publicResult != null) {
                assertTrue(publicResult instanceof Date, "Result should be a Date when non-null");
            }
        }
    }

    @Test
    void testCreateDateDelegatesToCreateValueUnchecked_withNullInput() throws Exception {
        String input = null;
        // Invoke via reflection
        Object reflectResult = null;
        Throwable reflectThrowable = null;
        try {
            reflectResult = createValueUncheckedMethod.invoke(null, input, Date.class);
        } catch (InvocationTargetException ite) {
            reflectThrowable = ite.getCause();
        }
        // Invoke via public API
        Object publicResult = null;
        Throwable publicThrowable = null;
        try {
            publicResult = TypeHandler.createDate(input);
        } catch (Throwable t) {
            publicThrowable = t;
        }
        // Ensure both behaviors match (either both throw same exception class or both return equal)
        if (reflectThrowable != null || publicThrowable != null) {
            assertNotNull(reflectThrowable, "Reflective invocation should have produced an exception when public invocation did");
            assertNotNull(publicThrowable, "Public invocation should have produced an exception when reflective invocation did");
            assertEquals(reflectThrowable.getClass(), publicThrowable.getClass(), "createDate should delegate to createValueUnchecked (exception types differ) with null input");
        } else {
            assertEquals(reflectResult, publicResult, "createDate should return the same value as createValueUnchecked with null input");
        }
    }
}
