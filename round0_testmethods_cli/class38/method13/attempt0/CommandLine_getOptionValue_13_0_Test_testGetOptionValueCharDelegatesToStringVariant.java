package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
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
import java.util.Objects;
import java.util.Properties;

class CommandLine_getOptionValue_13_0_Test_testGetOptionValueCharDelegatesToStringVariant {

    /**
     * Verifies that getOptionValue(char, Supplier) delegates to getOptionValue(String, Supplier)
     * by overriding the String-based variant in a subclass and observing the values received.
     */
    @Test
    void testGetOptionValueCharDelegatesToStringVariant() {
        class DelegatingCommandLine extends CommandLine {

            boolean called = false;

            String receivedOpt = null;

            Supplier<String> receivedSupplier = null;

            // The real CommandLine class defines a String-based getOptionValue. We override it
            // in this test subclass to observe delegation and return a sentinel value.
            @Override
            public String getOptionValue(final String opt, final Supplier<String> defaultValue) {
                called = true;
                receivedOpt = opt;
                receivedSupplier = defaultValue;
                return "sentinel:" + opt;
            }
        }
        DelegatingCommandLine cl = new DelegatingCommandLine();
        Supplier<String> supplier = () -> "defaultValue";
        String result = cl.getOptionValue('a', supplier);
        // Delegation happened and returned the sentinel constructed by the override
        assertTrue(cl.called, "Expected overridden getOptionValue(String, Supplier) to be called");
        assertEquals("a", cl.receivedOpt, "Expected the char to be converted to a String and passed");
        assertSame(supplier, cl.receivedSupplier, "Expected the same Supplier instance to be forwarded");
        assertEquals("sentinel:a", result, "Expected the overridden method's return value to be returned");
    }

    /**
     * Uses reflection to invoke the private 3-arg constructor and to invoke the focal method
     * on the instance created reflectively. This ensures the class can be instantiated via its
     * private constructor and that the char-based method can be invoked reflectively without errors.
     */

}
