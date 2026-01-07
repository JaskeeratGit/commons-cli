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

class CommandLine_builder_0_0_Test {

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

    @Test
    void testPrivateConstructorViaReflectionCreatesCommandLineWithProvidedLists() throws Exception {
        // Prepare arguments for private constructor: List<String>, List<Option>, Consumer<Option>
        List<String> args = new ArrayList<>();
        args.add("r1");
        args.add("r2");
        // Use raw List for options; contents aren't required for this test
        List<Object> options = new ArrayList<>();
        // deprecatedHandler can be null
        Consumer<Object> handler = null;
        // Find the private constructor: (List, List, Consumer)
        Constructor<CommandLine> ctor = null;
        Constructor<?>[] ctors = CommandLine.class.getDeclaredConstructors();
        for (Constructor<?> c : ctors) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 3 && List.class.isAssignableFrom(params[0]) && List.class.isAssignableFrom(params[1]) && Consumer.class.isAssignableFrom(params[2])) {
                // noinspection unchecked
                ctor = (Constructor<CommandLine>) c;
                break;
            }
        }
        assertNotNull(ctor, "Expected to find a private constructor with signature (List, List, Consumer)");
        ctor.setAccessible(true);
        // Invoke private constructor
        CommandLine cl = ctor.newInstance(args, options, handler);
        assertNotNull(cl);
        // Verify arg list contents
        List<String> argList = cl.getArgList();
        assertNotNull(argList);
        assertEquals(2, argList.size());
        assertEquals("r1", argList.get(0));
        assertEquals("r2", argList.get(1));
    }

    @Test
    void testBuilderAndPrivateConstructorInteroperability() throws Exception {
        // Build by reflection: addArg, then build
        Object builder = CommandLine.builder();
        Method addArgMethod = builder.getClass().getMethod("addArg", String.class);
        Method buildMethod = builder.getClass().getMethod("build");
        addArgMethod.invoke(builder, "alpha");
        Object built = buildMethod.invoke(builder);
        assertTrue(built instanceof CommandLine);
        CommandLine clFromBuilder = (CommandLine) built;
        // Create via private constructor with same args list and compare
        List<String> args = new ArrayList<>(clFromBuilder.getArgList());
        List<Object> options = new ArrayList<>();
        Constructor<CommandLine> ctor = null;
        for (Constructor<?> c : CommandLine.class.getDeclaredConstructors()) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 3 && List.class.isAssignableFrom(params[0]) && List.class.isAssignableFrom(params[1]) && Consumer.class.isAssignableFrom(params[2])) {
                // noinspection unchecked
                ctor = (Constructor<CommandLine>) c;
                break;
            }
        }
        assertNotNull(ctor);
        ctor.setAccessible(true);
        CommandLine clFromCtor = ctor.newInstance(args, options, null);
        // Compare their argument lists
        assertEquals(clFromBuilder.getArgList(), clFromCtor.getArgList());
    }
}
