package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLine_getOptionValues_24_0_Test_testNullOptionReturnsNull {

    // Helper to create a CommandLine instance by invoking the private constructor via reflection
    private CommandLine createCommandLine(final List<String> args, final List<Option> options, final Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    // Helper to set the private 'deprecated' field of an Option instance to a non-null instance
    // Attempts to instantiate the nested DeprecatedAttributes class if present, otherwise uses Unsafe as fallback.
    private void markOptionDeprecated(final Option option) throws Exception {
        Field deprecatedField = Option.class.getDeclaredField("deprecated");
        deprecatedField.setAccessible(true);
        Class<?> deprecatedClass = null;
        for (Class<?> c : Option.class.getDeclaredClasses()) {
            if ("DeprecatedAttributes".equals(c.getSimpleName())) {
                deprecatedClass = c;
                break;
            }
        }
        Object instance = null;
        if (deprecatedClass != null) {
            // Try to instantiate via no-arg constructor
            try {
                Constructor<?> dc = deprecatedClass.getDeclaredConstructor();
                dc.setAccessible(true);
                instance = dc.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                // Fallback to Unsafe.allocateInstance if available
                try {
                    Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                    Field f = unsafeClass.getDeclaredField("theUnsafe");
                    f.setAccessible(true);
                    Object unsafe = f.get(null);
                    java.lang.reflect.Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
                    instance = allocateInstance.invoke(unsafe, deprecatedClass);
                } catch (Exception ex) {
                    // As a last resort, try to instantiate via an anonymous subclass if it's an interface
                    if (deprecatedClass.isInterface()) {
                        instance = java.lang.reflect.Proxy.newProxyInstance(deprecatedClass.getClassLoader(), new Class<?>[] { deprecatedClass }, (proxy, method, args) -> {
                            // no-op for any invoked methods
                            return null;
                        });
                    } else {
                        // Give up with a descriptive exception for test failure
                        throw new IllegalStateException("Unable to instantiate DeprecatedAttributes nested class", ex);
                    }
                }
            }
        } else {
            // If there is no nested class (unexpected), set a simple Object if compatible (unlikely)
            // Throwing an exception to make the situation explicit.
            throw new IllegalStateException("DeprecatedAttributes nested class not found on Option; cannot mark deprecated");
        }
        deprecatedField.set(option, instance);
    }

    @Test
    void testNullOptionReturnsNull() throws Exception {
        CommandLine cl = createCommandLine(new LinkedList<>(), new ArrayList<>(), null);
        assertNull(cl.getOptionValues((Option) null), "getOptionValues should return null for null argument");
    }

}
