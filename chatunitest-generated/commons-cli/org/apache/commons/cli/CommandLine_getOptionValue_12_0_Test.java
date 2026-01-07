package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

class CommandLine_getOptionValue_12_0_Test {

    /**
     * Subclass of CommandLine used to capture the arguments passed to
     * getOptionValue(String, Supplier<String>) and to return a recognizable value.
     *
     * Placed in the same package so it can access protected no-arg constructor.
     */
    static class TestCommandLine extends CommandLine {

        String capturedOpt;

        Supplier<String> capturedSupplier;

        // relies on the protected no-arg constructor being available
        TestCommandLine() {
            super();
        }

        // Intentionally do not use @Override to remain resilient in case of signature differences,
        // but if the base class has this exact signature, this will override it.
        public String getOptionValue(final String opt, final Supplier<String> defaultValue) {
            this.capturedOpt = opt;
            this.capturedSupplier = defaultValue;
            // call the supplier to emulate typical behavior of using the default
            return "OVERRIDE:" + defaultValue.get();
        }
    }

    @Test
    void testDelegatesToStringVariant_withNonNullDefault() {
        TestCommandLine cmd = new TestCommandLine();
        String res = cmd.getOptionValue('a', "def");
        // The subclass implementation prefixes "OVERRIDE:" to the supplied default
        assertEquals("OVERRIDE:def", res, "Expected overridden getOptionValue(String,Supplier) to be invoked and to return prefixed default");
        assertEquals("a", cmd.capturedOpt, "Expected the char to be converted to its string representation");
        assertNotNull(cmd.capturedSupplier, "Expected a non-null Supplier to be passed");
        assertEquals("def", cmd.capturedSupplier.get(), "Expected the supplier to return the original default value");
    }

    @Test
    void testDelegatesToStringVariant_withNullDefault() {
        TestCommandLine cmd = new TestCommandLine();
        // Disambiguate null between overloads by casting to String
        String res = cmd.getOptionValue('Z', (String) null);
        // When supplier.get() returns null, concatenation will produce "OVERRIDE:null"
        assertEquals("OVERRIDE:null", res, "Expected overridden method to be called and to handle null default via supplier");
        assertEquals("Z", cmd.capturedOpt);
        assertNull(cmd.capturedSupplier.get(), "Supplier should return null when defaultValue was null");
    }

    @Test
    void testInvokeViaReflection_usingPrivateConstructor_and_getOptionValue() throws Exception {
        // Use reflection to access the private constructor: CommandLine(List, List, Consumer)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create instance using empty lists and null consumer (types erased at runtime)
        CommandLine cmd = ctor.newInstance(new ArrayList<>(), new ArrayList<>(), (Consumer) null);
        // Invoke the public focal method getOptionValue(char, String) via reflection
        Method focal = CommandLine.class.getMethod("getOptionValue", char.class, String.class);
        Object result = focal.invoke(cmd, 'b', "myDefault");
        // The method should return the default value when no options are present
        assertTrue(result == null || result instanceof String, "Result should be a String or null");
        assertEquals("myDefault", result, "Expected the default value to be returned by getOptionValue when no option is present");
    }
}
