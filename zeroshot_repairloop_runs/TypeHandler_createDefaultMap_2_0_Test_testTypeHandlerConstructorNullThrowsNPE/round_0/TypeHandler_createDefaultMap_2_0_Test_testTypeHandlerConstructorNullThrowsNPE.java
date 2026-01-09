package org.apache.commons.cli;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TypeHandler#createDefaultMap() and related behavior.
 *
 * Note: A minimal Converter interface is provided below to satisfy compilation
 * of the TypeHandler signatures used in these tests.
 */
public class TypeHandler_createDefaultMap_2_0_Test_testTypeHandlerConstructorNullThrowsNPE {

    @Test
    public void testTypeHandlerConstructorNullThrowsNPE() {
        assertThrows(NullPointerException.class, () -> new TypeHandler((Map<Class<?>, Converter<?, ? extends Throwable>>) null));
    }
}

// Minimal stub for Converter to satisfy compilation when running tests.
// This is a package-private top-level interface so its fully-qualified name
// is org.apache.commons.cli.Converter, matching the TypeHandler signatures used
// in the tests.
interface Converter<S, T extends Throwable> {
    // no methods needed for tests
}
