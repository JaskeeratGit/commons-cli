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

class CommandLine_builder_0_0_Test_testBuilderHasBuildMethodReturningCommandLineAndCanBuildWithoutArgs {

    @BeforeEach
    void ensureClassLoaded() {
        // No-op setup to ensure class is available; fails fast if not present.
        assertNotNull(CommandLine.class);
    }


    @Test
    void testBuilderHasBuildMethodReturningCommandLineAndCanBuildWithoutArgs() throws Exception {
        Object builder = CommandLine.builder();
        assertNotNull(builder);
        Method buildMethod = builder.getClass().getMethod("build");
        assertNotNull(buildMethod, "Builder should expose a public build() method");
        assertEquals(CommandLine.class, buildMethod.getReturnType(), "build() return type should be CommandLine");
        // Invoke build directly without adding args/options
        Object built = buildMethod.invoke(builder);
        assertNotNull(built);
        assertTrue(built instanceof CommandLine, "build() should return an instance of CommandLine");
        CommandLine cl = (CommandLine) built;
        List<String> argList = cl.getArgList();
        assertNotNull(argList, "CommandLine.getArgList() should not return null");
        assertTrue(argList.isEmpty(), "A freshly built CommandLine without added args should have empty arg list");
    }



}
