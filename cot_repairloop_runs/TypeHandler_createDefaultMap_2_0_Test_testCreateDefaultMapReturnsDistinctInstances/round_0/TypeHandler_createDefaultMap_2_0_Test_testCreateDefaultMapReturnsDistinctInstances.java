package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testCreateDefaultMapReturnsDistinctInstances {

    @Test
    public void testCreateDefaultMapReturnsDistinctInstances() {
        Map<Class<?>, Converter<?, ? extends Throwable>> a = TypeHandler.createDefaultMap();
        Map<Class<?>, Converter<?, ? extends Throwable>> b = TypeHandler.createDefaultMap();

        // ensure each invocation returns a fresh Map instance
        assertNotSame(a, b, "createDefaultMap() should return a new Map instance each call");

        // but both maps should be populated (non-empty)
        assertFalse(a.isEmpty(), "first map should not be empty");
        assertFalse(b.isEmpty(), "second map should not be empty");
    }
}
