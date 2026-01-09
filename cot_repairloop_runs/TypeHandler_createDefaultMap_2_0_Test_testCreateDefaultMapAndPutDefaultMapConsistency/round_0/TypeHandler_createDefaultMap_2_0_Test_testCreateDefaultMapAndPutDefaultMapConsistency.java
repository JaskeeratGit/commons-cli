package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 *
 * This test avoids declaring a local Converter type so it doesn't conflict
 * with any Converter type used by TypeHandler. It treats the map values
 * opaquely as unknown (?) and only verifies general properties.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testCreateDefaultMapAndPutDefaultMapConsistency {

    @Test
    public void testCreateDefaultMapAndPutDefaultMapConsistency() throws Exception {
        // Call the public API
        Map<Class<?>, ?> created = TypeHandler.createDefaultMap();

        // Find the putDefaultMap(...) method reflectively and invoke it on a fresh map
        Method putDefaultMap = null;
        for (Method m : TypeHandler.class.getDeclaredMethods()) {
            if ("putDefaultMap".equals(m.getName()) && m.getParameterCount() == 1) {
                Class<?>[] params = m.getParameterTypes();
                if (Map.class.isAssignableFrom(params[0])) {
                    putDefaultMap = m;
                    break;
                }
            }
        }
        assertNotNull(putDefaultMap, "Expected a method named putDefaultMap(Map) in TypeHandler");
        putDefaultMap.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<Class<?>, ?> fromPut = (Map<Class<?>, ?>) putDefaultMap.invoke(null, new HashMap<>());

        // Basic sanity checks: both maps should be non-empty and contain non-null keys/values
        assertNotNull(created, "createDefaultMap() returned null");
        assertNotNull(fromPut, "putDefaultMap(...) returned null");

        assertFalse(created.isEmpty(), "createDefaultMap() should produce a non-empty map");
        assertFalse(fromPut.isEmpty(), "putDefaultMap(...) should produce a non-empty map");

        for (Class<?> k : created.keySet()) {
            assertNotNull(k, "Map contains a null key");
            assertNotNull(created.get(k), "Map contains a null value for key: " + k);
        }
        for (Class<?> k : fromPut.keySet()) {
            assertNotNull(k, "Map contains a null key");
            assertNotNull(fromPut.get(k), "Map contains a null value for key: " + k);
        }
    }
}
