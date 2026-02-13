package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;

/*
 This test file provides minimal supporting stubs (Option, ParseException, Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameIsNull_thenResolveOptionReceivesNull_andOverloadedMethodGetsNull {

    // Minimal stub of Option used by CommandLine.resolveOption
    public static class Option {

        private final String opt;

        private final String longOpt;

        public Option(final String opt) {
            this(opt, null);
        }

        public Option(final String opt, final String longOpt) {
            this.opt = opt;
            this.longOpt = longOpt;
        }

        public String getOpt() {
            return opt;
        }

        public String getLongOpt() {
            return longOpt;
        }
    }

    // Minimal stub for ParseException (checked exception used in signature)
    public static class ParseException extends Exception {

        public ParseException(String message) {
            super(message);
        }
    }

    // Minimal Util stub used by resolveOption
    public static class Util {

        public static String stripLeadingHyphens(final String s) {
            if (s == null) {
                return null;
            }
            int i = 0;
            while (i < s.length() && s.charAt(i) == '-') {
                i++;
            }
            return (i == 0) ? s : (i >= s.length() ? "" : s.substring(i));
        }
    }

    // Minimal Builder stub referred to by the no-arg constructor in CommandLine
    public static class Builder {

        public static final Consumer<Option> DEPRECATED_HANDLER = o -> {
            // no-op for tests
        };
    }

    // Subclass of CommandLine to intercept calls to the overloaded method getParsedOptionValue(Option, Supplier)
    public static class TestableCommandLine extends CommandLine {

        volatile org.apache.commons.cli.Option lastSeenOption;

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
        public <T> T getParsedOptionValue(final org.apache.commons.cli.Option opt, final Supplier<T> defaultValue) {
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
    void whenOptionNameIsNull_thenResolveOptionReceivesNull_andOverloadedMethodGetsNull() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Put an option just to ensure list is non-empty; resolveOption should still return null for null input
        Option opt = new Option("p", "pp");
        setOptionsField(cmd, new ArrayList<>(Collections.singletonList(opt)));
        Supplier<String> supplier = () -> "nil-default";
        String out = cmd.getParsedOptionValue((String) null, supplier);
        assertEquals("nil-default", out);
        assertNull(cmd.lastSeenOption, "resolveOption(null) should produce null; overloaded method should get null");
        assertSame(supplier, cmd.lastSeenSupplier);
    }


}
