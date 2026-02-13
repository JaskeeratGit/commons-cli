package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_55_0_Test_testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }

    @Test
    public void testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated() throws Exception {
        // use the constructor with longOpt, hasArg and description
        Option dep = new Option("d", "deprecated", true, "deprecated option") {
            // ensure we report deprecated regardless of underlying Option implementation
            @Override
            public boolean isDeprecated() {
                return true;
            }
        };
        // mark option as deprecated - some versions expose a setter, others have a private field
        try {
            Method m = Option.class.getMethod("setDeprecated", boolean.class);
            m.invoke(dep, true);
        } catch (NoSuchMethodException nsme) {
            // try setting a private field named 'deprecated' or 'isDeprecated'
            boolean marked = false;
            try {
                Field f = Option.class.getDeclaredField("deprecated");
                f.setAccessible(true);
                Class<?> ft = f.getType();
                try {
                    if (ft == boolean.class) {
                        f.setBoolean(dep, true);
                        marked = true;
                    } else if (ft == Boolean.class) {
                        f.set(dep, Boolean.TRUE);
                        marked = true;
                    } else {
                        // try to instantiate the field type with a (boolean) constructor
                        try {
                            Constructor<?> cc = ft.getDeclaredConstructor(boolean.class);
                            cc.setAccessible(true);
                            Object instance = cc.newInstance(true);
                            f.set(dep, instance);
                            marked = true;
                        } catch (NoSuchMethodException e1) {
                            // try no-arg constructor
                            try {
                                Constructor<?> cc2 = ft.getDeclaredConstructor();
                                cc2.setAccessible(true);
                                Object instance2 = cc2.newInstance();
                                f.set(dep, instance2);
                                marked = true;
                            } catch (NoSuchMethodException e2) {
                                // can't construct, ignore and fall through to try 'isDeprecated' field
                            }
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException iae) {
                    // fall through to try 'isDeprecated' field
                }
            } catch (NoSuchFieldException nsfe) {
                // try alternative field name below
            }

            if (!marked) {
                try {
                    Field f2 = Option.class.getDeclaredField("isDeprecated");
                    f2.setAccessible(true);
                    Class<?> ft2 = f2.getType();
                    if (ft2 == boolean.class) {
                        f2.setBoolean(dep, true);
                        marked = true;
                    } else if (ft2 == Boolean.class) {
                        f2.set(dep, Boolean.TRUE);
                        marked = true;
                    } else {
                        // best effort: try to instantiate as above
                        try {
                            Constructor<?> cc = ft2.getDeclaredConstructor(boolean.class);
                            cc.setAccessible(true);
                            Object instance = cc.newInstance(true);
                            f2.set(dep, instance);
                            marked = true;
                        } catch (Exception e) {
                            try {
                                Constructor<?> cc2 = ft2.getDeclaredConstructor();
                                cc2.setAccessible(true);
                                Object instance2 = cc2.newInstance();
                                f2.set(dep, instance2);
                                marked = true;
                            } catch (Exception ignored) {
                                // give up marking deprecated
                            }
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    // give up if neither field exists or cannot be set
                }
            }
        }

        List<Option> options = new ArrayList<>();
        options.add(dep);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // The option exists and is deprecated, so hasOption should return true and invoke the handler once
        assertTrue(cmd.hasOption("d"));
        assertEquals(1, invoked.get());
        // Calling again should also return true and invoke handler again (by longOpt)
        assertTrue(cmd.hasOption("deprecated"));
        assertEquals(2, invoked.get());
    }

}
