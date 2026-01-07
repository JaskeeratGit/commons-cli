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

class CommandLine_builder_0_0_Test_testBuilderAddArgAndBuildReflectivelyProducesCommandLineWithArgs {

    @BeforeEach
    void ensureClassLoaded() {
        // No-op setup to ensure class is available; fails fast if not present.
        assertNotNull(CommandLine.class);
    }



    @Test
    void testBuilderAddArgAndBuildReflectivelyProducesCommandLineWithArgs() throws Exception {
        Object builder = CommandLine.builder();
        assertNotNull(builder);
        // Expecting a method addArg(String)
        Method addArgMethod = null;
        try {
            addArgMethod = builder.getClass().getMethod("addArg", String.class);
        } catch (NoSuchMethodException e) {
            fail("Expected Builder to have method addArg(String).");
        }
        assertNotNull(addArgMethod);
        Method buildMethod = builder.getClass().getMethod("build");
        assertNotNull(buildMethod);
        // Add multiple args
        addArgMethod.invoke(builder, "first");
        addArgMethod.invoke(builder, "second");
        Object built = buildMethod.invoke(builder);
        assertNotNull(built);
        assertTrue(built instanceof CommandLine);
        CommandLine cl = (CommandLine) built;
        List<String> argList = cl.getArgList();
        assertNotNull(argList);
        assertEquals(2, argList.size(), "Two args should have been added");
        assertEquals("first", argList.get(0));
        assertEquals("second", argList.get(1));
    }


}
