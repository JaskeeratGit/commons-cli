package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 Fixed unit test for CommandLine#getParsedOptionValue(String, Supplier)

 Notes on fixes:
 - Removed nested stub classes for Option, ParseException, Util, Builder so the test uses the real
   org.apache.commons.cli.Option and org.apache.commons.cli.ParseException types defined by the
   library. This ensures the overridden method signature in the subclass matches the supertype and
   thus @Override is valid.
 - Constructed an Option using the constructor that accepts (opt, longOpt, hasArg, description)
   so getOpt() and getLongOpt() return the expected values.
 - Kept the reflection helper to set the private 'options' field in CommandLine.
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameResolvesToShortOpt_thenOverloadedMethodReceivesMatchingOption {

    // Subclass of CommandLine to intercept calls to the overloaded method getParsedOptionValue(Option, Supplier)
    public static class TestableCommandLine extends CommandLine {

        volatile Option lastSeenOption;
        volatile Supplier<?> lastSeenSupplier;

        private final Object returnValue;

        // Use protected no-arg constructor of CommandLine
        public TestableCommandLine() {
            super();
            this.returnValue = null;
        }

        // Allow specifying a value to return from the overridden method
        public TestableCommandLine(final Object returnValue) {
            super();
            this.returnValue = returnValue;
        }

        // Override the overloaded method to record inputs and return a controlled value
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getParsedOptionValue(final Option opt, final Supplier<T> defaultValue) throws ParseException {
            this.lastSeenOption = opt;
            this.lastSeenSupplier = defaultValue;
            if (returnValue != null) {
                return (T) returnValue;
            }
            return (defaultValue == null) ? null : defaultValue.get();
        }
    }

    // Helper to set the private final 'options' field in CommandLine
    private static void setOptionsField(final CommandLine target, final List<Option> options) throws Exception {
        Field f = CommandLine.class.getDeclaredField("options");
        f.setAccessible(true);
        f.set(target, options);
    }

    @Test
    void whenOptionNameResolvesToShortOpt_thenOverloadedMethodReceivesMatchingOption() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Use the real Option class constructor that sets longOpt (opt, longOpt, hasArg, description)
        Option opt = new Option("a", "alpha", false, "desc");
        setOptionsField(cmd, new ArrayList<>(Collections.singletonList(opt)));
        Supplier<Integer> defaultSupplier = () -> 42;
        Integer val = cmd.getParsedOptionValue("-a", defaultSupplier);
        assertEquals(Integer.valueOf(42), val, "Should return supplier value when overloaded method returns default supplier result");
        assertNotNull(cmd.lastSeenOption, "Option should not be null for a matching short option");
        assertEquals("a", cmd.lastSeenOption.getOpt());
        assertEquals("alpha", cmd.lastSeenOption.getLongOpt());
        assertSame(defaultSupplier, cmd.lastSeenSupplier);
    }
}
