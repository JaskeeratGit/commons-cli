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

class CommandLine_builder_0_0_Test_testPrivateConstructorViaReflectionCreatesCommandLineWithProvidedLists {

    @BeforeEach
    void ensureClassLoaded() {
        // No-op setup to ensure class is available; fails fast if not present.
        assertNotNull(CommandLine.class);
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

}
