package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.Supplier;

class CommandLine_builder_0_0_Test_testBuilderReturnsNonNullAndIsNewInstanceEachCall {

    @BeforeEach
    void ensureClassLoaded() {
        // No-op setup to ensure class is available; fails fast if not present.
        assertNotNull(CommandLine.class);
    }

    @Test
    void testBuilderReturnsNonNullAndIsNewInstanceEachCall() {
        Object builder1 = CommandLine.builder();
        Object builder2 = CommandLine.builder();
        assertNotNull(builder1, "builder() should not return null");
        assertNotNull(builder2, "builder() should not return null");
        assertNotSame(builder1, builder2, "builder() should return a new Builder instance each call");
        // Class name should indicate it's a Builder (best-effort check, robust to inner-class naming)
        String className = builder1.getClass().getSimpleName().toLowerCase(Locale.ROOT);
        assertTrue(className.contains("builder") || className.contains("builder$") || builder1.getClass().getName().toLowerCase(Locale.ROOT).contains("builder"), "Returned instance class name should indicate a Builder");
    }




}
