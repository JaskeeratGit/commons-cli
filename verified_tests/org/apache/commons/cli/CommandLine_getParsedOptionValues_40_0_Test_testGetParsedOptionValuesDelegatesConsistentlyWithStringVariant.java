package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;

/**
 * Generated tests for CommandLine#getParsedOptionValues(char, Supplier)
 */
public class CommandLine_getParsedOptionValues_40_0_Test_testGetParsedOptionValuesDelegatesConsistentlyWithStringVariant {

    /**
     * Helper to create a CommandLine instance using the private constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    private CommandLine newCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Consumer<Option> handler = opt -> {
            // no-op deprecated handler for tests
        };
        return ctor.newInstance(args, options, handler);
    }


    @Test
    public void testGetParsedOptionValuesDelegatesConsistentlyWithStringVariant() throws Exception {
        CommandLine cmd = newCommandLineInstance();
        Method charMethod = CommandLine.class.getMethod("getParsedOptionValues", char.class, Supplier.class);
        Method stringMethod = CommandLine.class.getMethod("getParsedOptionValues", String.class, Supplier.class);
        Supplier<Integer[]> supplierForChar = () -> new Integer[] { 1, 2, 3 };
        Supplier<Integer[]> supplierForString = () -> new Integer[] { 1, 2, 3 };
        Object resChar = invokeMethodAndUnwrap(charMethod, cmd, 'x', supplierForChar);
        Object resString = invokeMethodAndUnwrap(stringMethod, cmd, "x", supplierForString);
        assertNotNull(resChar);
        assertNotNull(resString);
        assertTrue(resChar instanceof Integer[]);
        assertTrue(resString instanceof Integer[]);
        Integer[] arrChar = (Integer[]) resChar;
        Integer[] arrString = (Integer[]) resString;
        // Both should produce equal contents when given equivalent suppliers and key
        assertArrayEquals(arrString, arrChar);
    }

    /**
     * Utility to invoke a reflective Method and unwrap InvocationTargetException to throw the underlying cause.
     */
    private static Object invokeMethodAndUnwrap(Method m, Object target, Object... args) throws Exception {
        try {
            return m.invoke(target, args);
        } catch (InvocationTargetException ite) {
            // Rethrow the underlying exception to make test failures clear
            Throwable cause = ite.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            } else {
                throw ite;
            }
        }
    }
}
