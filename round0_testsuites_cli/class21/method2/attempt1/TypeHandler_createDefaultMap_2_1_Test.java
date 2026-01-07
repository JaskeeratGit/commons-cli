package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.HashMap;
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
import java.util.Objects;

class TypeHandler_createDefaultMap_2_1_Test {

    @Test
    void testCreateDefaultMap_NotNullAndIsMap() {
        Map<Class<?>, ?> map = TypeHandler.createDefaultMap();
        assertNotNull(map, "createDefaultMap should not return null");
        assertTrue(map instanceof Map, "createDefaultMap should return a Map");
    }

    @Test
    void testCreateDefaultMap_ReturnsDistinctInstances() {
        Map<Class<?>, ?> first = TypeHandler.createDefaultMap();
        Map<Class<?>, ?> second = TypeHandler.createDefaultMap();
        // They should not be the same instance (fresh HashMap expected)
        assertNotSame(first, second, "createDefaultMap should return a new Map instance on each call");
    }

    @Test
    void testPutDefaultMap_PreservesExistingEntriesAndReturnsSameInstance() throws Exception {
        // Access the private static method putDefaultMap(Map)
        Method putDefaultMap = TypeHandler.class.getDeclaredMethod("putDefaultMap", Map.class);
        putDefaultMap.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Object> input = new HashMap<>();
        String marker = "dummyConverter";
        input.put(String.class, marker);
        // Invoke the private method
        @SuppressWarnings("unchecked")
        Map<Class<?>, Object> returned = (Map<Class<?>, Object>) putDefaultMap.invoke(null, input);
        // The implementation is expected to return the same map instance passed in
        assertSame(input, returned, "putDefaultMap should return the same Map instance that was passed in");
        // Our pre-existing mapping must be preserved
        assertEquals(marker, returned.get(String.class), "Existing entries should be preserved by putDefaultMap");
        // Also, expect that the map contains at least one mapping after defaults were put
        assertFalse(returned.isEmpty(), "putDefaultMap should populate the map with at least one default converter");
    }
}
