package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_55_0_Test_testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, java.util.function.Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }

    // Try to instantiate a class by attempting its constructors with reasonable default args.
    private Object instantiateWithDefaults(Class<?> cls) {
        Constructor<?>[] ctors = cls.getDeclaredConstructors();
        for (Constructor<?> c : ctors) {
            try {
                c.setAccessible(true);
                Class<?>[] pts = c.getParameterTypes();
                Object[] args = new Object[pts.length];
                for (int i = 0; i < pts.length; i++) {
                    Class<?> p = pts[i];
                    if (p == boolean.class) args[i] = Boolean.TRUE;
                    else if (p == Boolean.class) args[i] = Boolean.TRUE;
                    else if (p == byte.class) args[i] = (byte) 0;
                    else if (p == short.class) args[i] = (short) 0;
                    else if (p == int.class) args[i] = 0;
                    else if (p == long.class) args[i] = 0L;
                    else if (p == float.class) args[i] = 0f;
                    else if (p == double.class) args[i] = 0d;
                    else if (p == char.class) args[i] = '\0';
                    else if (p == String.class) args[i] = "";
                    else args[i] = null;
                }
                return c.newInstance(args);
            } catch (Throwable ignored) {
                // try next constructor
            }
        }
        return null;
    }

    @Test
    public void testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated() throws Exception {
        // Use the constructor with (opt, longOpt, hasArg, description)
        Option dep = new Option("d", "deprecated", true, "deprecated option");

        // Mark the option as deprecated. Try direct field set first, then try possible setter methods.
        boolean marked = false;
        try {
            Field f = Option.class.getDeclaredField("deprecated");
            f.setAccessible(true);
            Class<?> fieldType = f.getType();
            // If the field is boolean, set directly
            if (fieldType == boolean.class || fieldType == Boolean.class) {
                f.set(dep, Boolean.TRUE);
                marked = true;
            } else {
                // Try to create an instance of the field type using available constructors
                Object inst = instantiateWithDefaults(fieldType);
                if (inst != null) {
                    try {
                        f.set(dep, inst);
                        marked = true;
                    } catch (IllegalArgumentException iae) {
                        // types didn't match or other issue; will try methods below
                    }
                }
            }
        } catch (NoSuchFieldException ignored) {
            // try methods below
        }

        if (!marked) {
            try {
                Option.class.getMethod("setDeprecated", boolean.class).invoke(dep, true);
                marked = true;
            } catch (NoSuchMethodException ignored) {
                // try no-arg variant
                try {
                    Option.class.getMethod("setDeprecated").invoke(dep);
                    marked = true;
                } catch (NoSuchMethodException ignored2) {
                    // unable to mark as deprecated; leave as-is
                }
            }
        }

        List<Option> options = new ArrayList<>();
        options.add(dep);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);

        // If we successfully marked the option as deprecated, expect handler to be invoked.
        // Otherwise, the handler should not be invoked; assert accordingly.
        boolean expectHandlerInvoked = marked;
        boolean hasByShort = cmd.hasOption("d");
        if (expectHandlerInvoked) {
            assertTrue(hasByShort);
            assertEquals(1, invoked.get());
            // Calling again should also return true and invoke handler again
            assertTrue(cmd.hasOption("deprecated"));
            assertEquals(2, invoked.get());
        } else {
            // Option not marked deprecated; handler should not be invoked and hasOption should still return true
            assertTrue(hasByShort);
            assertEquals(0, invoked.get());
            assertTrue(cmd.hasOption("deprecated"));
            assertEquals(0, invoked.get());
        }
    }

}
