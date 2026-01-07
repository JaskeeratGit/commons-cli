package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
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
import java.util.List;
import java.util.Objects;
import java.util.Properties;

class CommandLine_getOptionValue_19_0_Test {

    /**
     * A test subclass of CommandLine that lets us control the single-argument
     * getOptionValue(OptionGroup) behavior so we can exercise the two-arg method.
     */
    static class TestCommandLine extends CommandLine {

        private final String toReturn;

        private final AtomicBoolean calledFlag;

        TestCommandLine(String toReturn, AtomicBoolean calledFlag) {
            // calls protected no-arg constructor
            super();
            this.toReturn = toReturn;
            this.calledFlag = calledFlag;
        }

        @Override
        public String getOptionValue(final OptionGroup optionGroup) {
            if (calledFlag != null) {
                // mark that this override was invoked
                calledFlag.set(true);
            }
            return toReturn;
        }
    }

    @Test
    void whenSingleArgReturnsNonNull_thenTwoArgReturnsIt_andSupplierNotInvoked() {
        AtomicBoolean overrideCalled = new AtomicBoolean(false);
        AtomicBoolean supplierCalled = new AtomicBoolean(false);
        // override returns a non-null answer
        TestCommandLine cli = new TestCommandLine("explicitAnswer", overrideCalled);
        Supplier<String> defaultSupplier = () -> {
            supplierCalled.set(true);
            return "defaultValue";
        };
        String result = cli.getOptionValue(new OptionGroup(), defaultSupplier);
        assertEquals("explicitAnswer", result, "Should return value from single-arg getOptionValue");
        assertTrue(overrideCalled.get(), "Override single-arg should have been invoked");
        assertFalse(supplierCalled.get(), "Supplier should NOT have been invoked when answer is non-null");
    }

    @Test
    void whenSingleArgReturnsNull_thenTwoArgUsesSupplier_andSupplierInvoked() {
        AtomicBoolean overrideCalled = new AtomicBoolean(false);
        AtomicBoolean supplierCalled = new AtomicBoolean(false);
        // override returns null to force using the default supplier
        TestCommandLine cli = new TestCommandLine(null, overrideCalled);
        Supplier<String> defaultSupplier = () -> {
            supplierCalled.set(true);
            return "fromSupplier";
        };
        String result = cli.getOptionValue(new OptionGroup(), defaultSupplier);
        assertEquals("fromSupplier", result, "Should return value from supplier when single-arg returns null");
        assertTrue(overrideCalled.get(), "Override single-arg should have been invoked");
        assertTrue(supplierCalled.get(), "Supplier should have been invoked when answer is null");
    }

    @Test
    void canInstantiateViaPrivateConstructor_usingReflection_and_useSupplierFallback() throws Exception {
        // Use reflection to access the private constructor CommandLine(List<String>, List<Option>, Consumer<Option>)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(java.util.List.class, java.util.List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create an instance with empty args and options; deprecatedHandler set to null
        CommandLine reflectCli = ctor.newInstance(new LinkedList<String>(), new ArrayList<Option>(), null);
        // For a fresh instance with no options, single-arg getOptionValue is expected to return null
        // so the two-arg method should fall back to the supplier.
        Supplier<String> defaultSupplier = () -> "reflectDefault";
        String result = reflectCli.getOptionValue(new OptionGroup(), defaultSupplier);
        assertEquals("reflectDefault", result, "Instance created via private constructor should use supplier fallback when no option value present");
    }
}
