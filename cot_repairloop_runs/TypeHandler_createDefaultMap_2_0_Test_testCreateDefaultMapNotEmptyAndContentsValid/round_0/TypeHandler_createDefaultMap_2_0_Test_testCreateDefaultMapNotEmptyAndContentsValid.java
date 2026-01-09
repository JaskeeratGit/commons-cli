package org.apache.commons.cli;

import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testCreateDefaultMapNotEmptyAndContentsValid {

    @Test
    public void testCreateDefaultMapNotEmptyAndContentsValid() {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = TypeHandler.createDefaultMap();
        assertNotNull(map, "createDefaultMap() should not return null");
        assertFalse(map.isEmpty(), "createDefaultMap() should populate default converters");

        // Validate each entry has non-null key and value, and key is a Class
        for (Map.Entry<Class<?>, Converter<?, ? extends Throwable>> e : map.entrySet()) {
            assertNotNull(e.getKey(), "map key should not be null");
            assertNotNull(e.getValue(), "map value (converter) should not be null");
            // e.getKey() is of type Class<?>, so instanceof Class is always true; keep it as a sanity check
            assertTrue(e.getKey() instanceof Class, "map key should be a Class");
        }
    }
}
