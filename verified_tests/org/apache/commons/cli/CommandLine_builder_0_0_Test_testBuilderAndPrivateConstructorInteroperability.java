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

class CommandLine_builder_0_0_Test_testBuilderAndPrivateConstructorInteroperability {

    @BeforeEach
    void ensureClassLoaded() {
        // No-op setup to ensure class is available; fails fast if not present.
        assertNotNull(CommandLine.class);
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
