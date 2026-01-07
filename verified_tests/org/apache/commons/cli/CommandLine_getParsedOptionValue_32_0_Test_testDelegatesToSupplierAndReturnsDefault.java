package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

public class CommandLine_getParsedOptionValue_32_0_Test_testDelegatesToSupplierAndReturnsDefault {

    // A small subclass to override the Supplier-taking overload and observe supplier usage.
    static class TestCommandLine extends CommandLine {

        boolean supplierInvoked = false;

        Option receivedOption = null;

        Supplier<?> capturedSupplier = null;

        protected TestCommandLine() {
            // use protected no-arg constructor
            super();
        }

        @Override
        public <T> T getParsedOptionValue(final Option option, final Supplier<T> defaultValueSupplier) throws ParseException {
            // record inputs and then return the supplier result
            supplierInvoked = true;
            receivedOption = option;
            capturedSupplier = defaultValueSupplier;
            return defaultValueSupplier.get();
        }
    }

    @Test
    public void testDelegatesToSupplierAndReturnsDefault() throws Exception {
        // create an Option and a TestCommandLine that records supplier invocation
        Option opt = new Option("o", "opt", false, "desc");
        TestCommandLine cl = new TestCommandLine();
        String defaultValue = "theDefault";
        String result = cl.getParsedOptionValue(opt, defaultValue);
        // verify that overridden supplier-taking method was invoked and returned the provided default
        assertTrue(cl.supplierInvoked, "supplier-taking overload must be invoked");
        assertSame(opt, cl.receivedOption, "the Option passed should be forwarded");
        assertNotNull(cl.capturedSupplier, "supplier must be captured");
        // captured supplier should produce the same default value
        @SuppressWarnings("unchecked")
        Supplier<String> supplier = (Supplier<String>) cl.capturedSupplier;
        assertEquals(defaultValue, supplier.get());
        assertEquals(defaultValue, result);
    }

}
