package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
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
import java.util.Objects;

public class TypeHandler_createObject_6_0_Test {

    @Test
    public void testDefaultFieldNotNullAndConverterMapAccessible() throws Exception {
        // Access private static DEFAULT field
        Field defaultField = TypeHandler.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        Object defaultInstance = defaultField.get(null);
        assertNotNull(defaultInstance, "DEFAULT instance should not be null");
        // Access private instance field converterMap on DEFAULT
        Field converterMapField = TypeHandler.class.getDeclaredField("converterMap");
        converterMapField.setAccessible(true);
        Object converterMapObj = converterMapField.get(defaultInstance);
        assertNotNull(converterMapObj, "converterMap should not be null");
        assertTrue(converterMapObj instanceof Map, "converterMap should be a Map");
    }

    @Test
    public void testHexRadixConstant() throws Exception {
        // Access private static HEX_RADIX field
        Field hexField = TypeHandler.class.getDeclaredField("HEX_RADIX");
        hexField.setAccessible(true);
        int hexValue = hexField.getInt(null);
        assertEquals(16, hexValue, "HEX_RADIX should be 16");
    }

    @Test
    public void testCreateObjectDelegatesToPrivateCreateValue_forNormalInput() throws Exception {
        Method createValue = findCreateValueMethod();
        createValue.setAccessible(true);
        String input = "testInput";
        // Try invoking private createValue and handle if it throws; ensure createObject behaves the same
        Object expected;
        try {
            expected = createValue.invoke(null, input, Object.class);
        } catch (InvocationTargetException ite) {
            // private method threw; ensure createObject throws same type of exception
            Throwable cause = ite.getCause();
            assertNotNull(cause, "Cause should be present when createValue throws");
            Class<? extends Throwable> expectedType = cause.getClass();
            // createObject may throw ParseException or propagate the same cause; assert an exception of same type is thrown
            Throwable thrown = assertThrows(expectedType, () -> {
                try {
                    TypeHandler.createObject(input);
                } catch (Throwable t) {
                    // createObject signature throws ParseException, but may throw other runtime exceptions
                    throw t;
                }
            });
            // If both threw, test passes
            return;
        }
        // If private method returned a value, ensure createObject returns equal value
        Object actual = TypeHandler.createObject(input);
        assertEquals(expected, actual, "createObject result should equal result from private createValue");
    }

    @Test
    public void testCreateObjectHandlesEmptyStringConsistently() throws Exception {
        Method createValue = findCreateValueMethod();
        createValue.setAccessible(true);
        String input = "";
        try {
            Object expected = createValue.invoke(null, input, Object.class);
            Object actual = TypeHandler.createObject(input);
            assertEquals(expected, actual, "createObject should match createValue for empty string");
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            assertNotNull(cause);
            Class<? extends Throwable> expectedType = cause.getClass();
            assertThrows(expectedType, () -> {
                try {
                    TypeHandler.createObject(input);
                } catch (Throwable t) {
                    throw t;
                }
            });
        }
    }

    @Test
    public void testCreateObjectWithNullInput_throwsConsistently() throws Exception {
        Method createValue = findCreateValueMethod();
        createValue.setAccessible(true);
        String input = null;
        try {
            createValue.invoke(null, input, Object.class);
            // If private method unexpectedly returns, ensure createObject also returns (or throws) the same behavior
            Object actual = TypeHandler.createObject(input);
            // If both returned, they should be equal (likely both null or same instance)
            assertNull(actual, "If createValue returns, createObject should return the same (likely null)");
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            assertNotNull(cause);
            Class<? extends Throwable> expectedType = cause.getClass();
            // Ensure createObject throws the same type of exception (or a ParseException)
            try {
                assertThrows(expectedType, () -> {
                    try {
                        TypeHandler.createObject(input);
                    } catch (Throwable t) {
                        throw t;
                    }
                });
            } catch (AssertionError ae) {
                // If createObject throws a ParseException instead, accept that as consistent behavior
                assertThrows(ParseException.class, () -> TypeHandler.createObject(input));
            }
        }
    }

    // Helper to find private static createValue(String, Class) method by reflection
    private Method findCreateValueMethod() throws NoSuchMethodException {
        // Try the expected signature: (String, Class)
        try {
            return TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        } catch (NoSuchMethodException e) {
            // If not found, rethrow with clearer message
            throw new NoSuchMethodException("Private method createValue(String, Class) not found in TypeHandler");
        }
    }
}
