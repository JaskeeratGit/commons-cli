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

class CommandLine_getOptionValue_19_0_Test_whenSingleArgReturnsNonNull_thenTwoArgReturnsIt_andSupplierNotInvoked {

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


}
