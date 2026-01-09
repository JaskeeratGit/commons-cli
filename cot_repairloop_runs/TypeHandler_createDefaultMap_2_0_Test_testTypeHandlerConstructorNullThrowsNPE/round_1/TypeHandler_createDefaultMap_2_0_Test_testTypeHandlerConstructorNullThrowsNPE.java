package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testTypeHandlerConstructorNullThrowsNPE {

    @Test
    public void testTypeHandlerConstructorNullThrowsNPE() {
        // Ensure passing null to the Map-based constructor throws NPE
        assertThrows(NullPointerException.class,
                () -> new TypeHandler((Map<Class<?>, Converter<?, ? extends Throwable>>) null));
    }

    @Test
    public void testCreateDefaultMapNotEmptyAndContainsCommonTypes() {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = TypeHandler.createDefaultMap();
        assertNotNull(map, "createDefaultMap must not return null");
        assertFalse(map.isEmpty(), "createDefaultMap should not return an empty map");

        // Every entry should have non-null key and non-null value
        for (Map.Entry<Class<?>, Converter<?, ? extends Throwable>> e : map.entrySet()) {
            assertNotNull(e.getKey(), "Map key should not be null");
            assertNotNull(e.getValue(), "Map value should not be null");
        }

        // Check a set of commonly expected conversions are present.
        // These assertions are intentionally conservative (common core types).
        assertTrue(map.containsKey(String.class), "Expected converter for String");
        assertTrue(map.containsKey(Integer.class), "Expected converter for Integer");
        assertTrue(map.containsKey(Long.class), "Expected converter for Long");
        assertTrue(map.containsKey(Boolean.class), "Expected converter for Boolean");
    }

    @Test
    public void testCreateDefaultMapReturnsIndependentMapsWithSameKeys() {
        Map<Class<?>, Converter<?, ? extends Throwable>> m1 = TypeHandler.createDefaultMap();
        Map<Class<?>, Converter<?, ? extends Throwable>> m2 = TypeHandler.createDefaultMap();

        // Should be separate map instances
        assertNotSame(m1, m2);

        // Keys should be the same between invocations (structure expected to be identical)
        assertEquals(m1.keySet(), m2.keySet(), "createDefaultMap should produce same set of keys on repeated calls");

        // Mutating one map should not affect the other
        Map<Class<?>, Converter<?, ? extends Throwable>> copy = new HashMap<>(m1);
        copy.remove(String.class);
        assertTrue(m1.containsKey(String.class), "Original map should not be affected by changes to the copy");
    }
}
