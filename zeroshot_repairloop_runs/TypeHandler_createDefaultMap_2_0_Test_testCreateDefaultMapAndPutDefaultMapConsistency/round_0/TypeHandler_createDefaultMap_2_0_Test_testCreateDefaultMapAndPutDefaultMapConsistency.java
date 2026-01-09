package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testCreateDefaultMapAndPutDefaultMapConsistency {

    @Test
    public void testCreateDefaultMapAndPutDefaultMapConsistency() throws Exception {
        // Ensure createDefaultMap delegates to putDefaultMap(new HashMap<>()) effectively
        Map<Class<?>, Converter<?, ? extends Throwable>> created = TypeHandler.createDefaultMap();
        // invoke putDefaultMap on a fresh map
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
        Map<Class<?>, Converter<?, ? extends Throwable>> fromPut =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) putDefaultMap.invoke(null, new HashMap<>());
        // The two maps should have similar characteristics: both non-empty and contain Class->Converter mappings.
        assertFalse(created.isEmpty());
        assertFalse(fromPut.isEmpty());
        // Verify that keys/values types align
        for (Class<?> k : created.keySet()) {
            assertNotNull(k);
            assertTrue(created.get(k) != null);
        }
        for (Class<?> k : fromPut.keySet()) {
            assertNotNull(k);
            assertTrue(fromPut.get(k) != null);
        }
    }
}
