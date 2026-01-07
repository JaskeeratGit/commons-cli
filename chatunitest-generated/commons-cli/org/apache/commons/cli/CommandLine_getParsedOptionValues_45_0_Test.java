package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.lang.reflect.Array;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/*
 * Unit tests for CommandLine#getParsedOptionValues(OptionGroup).
 *
 * These tests use reflection to:
 *  - instantiate CommandLine via its protected no-arg constructor
 *  - invoke the public getParsedOptionValues(OptionGroup) method
 *  - invoke the (likely) private overload getParsedOptionValues(OptionGroup, Supplier)
 *
 * The assertions are written defensively to tolerate slightly different internal implementations
 * (e.g., returning null vs empty array) while still exercising the focal method and private overload.
 */
public class CommandLine_getParsedOptionValues_45_0_Test {

    private Object newCommandLineInstance() {
        try {
            Class<?> cmdClass = Class.forName("org.apache.commons.cli.CommandLine");
            // Prefer protected no-arg constructor if available
            Constructor<?> ctor = null;
            try {
                ctor = cmdClass.getDeclaredConstructor();
            } catch (NoSuchMethodException ignored) {
                // fallback to private (List, List, Consumer) constructor
                ctor = cmdClass.getDeclaredConstructor(java.util.List.class, java.util.List.class, Consumer.class);
                ctor.setAccessible(true);
                return ctor.newInstance(new LinkedList<>(), new ArrayList<>(), (Consumer<Object>) o -> {
                });
            }
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("CommandLine class not found in classpath", e);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate CommandLine via reflection", e);
        }
    }

    @Test
    public void testGetParsedOptionValues_public_null_throwsNullPointerException() throws Exception {
        Object cmd = newCommandLineInstance();
        Class<?> cmdClass = cmd.getClass();
        // Obtain public method getParsedOptionValues(OptionGroup)
        Method publicMethod = cmdClass.getMethod("getParsedOptionValues", Class.forName("org.apache.commons.cli.OptionGroup"));
        // Invoking reflectively wraps thrown exceptions in InvocationTargetException.
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            publicMethod.invoke(cmd, new Object[] { null });
        });
        // Expect the underlying cause to be NullPointerException (defensive; fail if not)
        assertTrue(thrown.getCause() instanceof NullPointerException, "Expected cause to be NullPointerException but was: " + thrown.getCause());
    }

    @Test
    public void testGetParsedOptionValues_private_overload_invocation_with_supplier_array() throws Exception {
        Object cmd = newCommandLineInstance();
        Class<?> cmdClass = cmd.getClass();
        // Locate the private overload: getParsedOptionValues(OptionGroup, Supplier)
        Method privateMethod;
        try {
            privateMethod = cmdClass.getDeclaredMethod("getParsedOptionValues", Class.forName("org.apache.commons.cli.OptionGroup"), Supplier.class);
        } catch (NoSuchMethodException e) {
            // If the private overload does not exist, skip this test (assumption)
            Assumptions.assumeTrue(false, "Private overload getParsedOptionValues(OptionGroup, Supplier) not present");
            return;
        }
        privateMethod.setAccessible(true);
        // Create an OptionGroup instance
        Object optionGroup = Class.forName("org.apache.commons.cli.OptionGroup").getDeclaredConstructor().newInstance();
        // Supplier that supplies a String[]; the private method is generic, but we pass String[] to check return passthrough
        Supplier<String[]> supplier = () -> new String[] { "alpha", "beta", "gamma" };
        Object result = privateMethod.invoke(cmd, optionGroup, supplier);
        // The private method is generic and returns T[]; ensure result is an array and matches supplier content
        assertNotNull(result, "Expected non-null result from private getParsedOptionValues when supplier provides array");
        assertTrue(result.getClass().isArray(), "Expected result to be an array");
        Object[] arr = (Object[]) result;
        assertEquals(3, arr.length, "Expected array length equal to supplier-provided length");
        assertEquals("alpha", arr[0]);
        assertEquals("beta", arr[1]);
        assertEquals("gamma", arr[2]);
    }

    @Test
    public void testGetParsedOptionValues_public_with_empty_group_returns_null_or_empty_array() throws Exception {
        Object cmd = newCommandLineInstance();
        Class<?> cmdClass = cmd.getClass();
        Method publicMethod = cmdClass.getMethod("getParsedOptionValues", Class.forName("org.apache.commons.cli.OptionGroup"));
        Object optionGroup = Class.forName("org.apache.commons.cli.OptionGroup").getDeclaredConstructor().newInstance();
        Object result = null;
        try {
            result = publicMethod.invoke(cmd, optionGroup);
        } catch (InvocationTargetException ite) {
            // If the public method throws, unwrap and rethrow to fail the test with the original cause
            throw new RuntimeException("Invocation of public getParsedOptionValues threw an exception", ite.getCause());
        }
        // Accept either null or an array of length 0 as reasonable behaviors for an empty OptionGroup
        if (result == null) {
            // Okay: method returned null for empty group
            return;
        }
        assertTrue(result.getClass().isArray(), "Expected returned value to be an array when non-null");
        Object[] arr = (Object[]) result;
        assertEquals(0, arr.length, "Expected empty array for empty OptionGroup");
    }
}
