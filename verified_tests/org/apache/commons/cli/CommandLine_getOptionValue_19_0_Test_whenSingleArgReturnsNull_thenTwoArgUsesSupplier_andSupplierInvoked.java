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

class CommandLine_getOptionValue_19_0_Test_whenSingleArgReturnsNull_thenTwoArgUsesSupplier_andSupplierInvoked {

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

}
