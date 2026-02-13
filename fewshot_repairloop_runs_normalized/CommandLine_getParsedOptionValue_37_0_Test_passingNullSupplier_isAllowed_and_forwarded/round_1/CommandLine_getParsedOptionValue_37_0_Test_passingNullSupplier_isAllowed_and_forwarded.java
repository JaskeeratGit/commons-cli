package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;

/*
 This test file provides minimal supporting stubs (Option, ParseException, Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_passingNullSupplier_isAllowed_and_forwarded {

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
        public <T> T getParsedOptionValue(final org.apache.commons.cli.Option opt, final Supplier<T> defaultValue) throws org.apache.commons.cli.ParseException {
            this.lastSeenOption = opt;
            this.lastSeenSupplier = defaultValue;
            if (returnValue != null) {
                return (T) returnValue;
            }
            return (defaultValue == null) ? null : defaultValue.get();
        }
    }

    // Helper to set the private final 'options' field in CommandLine
    private static void setOptionsField(final CommandLine target, final List<org.apache.commons.cli.Option> options) throws Exception {
        Field f = CommandLine.class.getDeclaredField("options");
        f.setAccessible(true);
        f.set(target, options);
    }

    @Test
    void passingNullSupplier_isAllowed_and_forwarded() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // ensure no option matches -> null option
        setOptionsField(cmd, new ArrayList<org.apache.commons.cli.Option>());
        // pass null as supplier
        String out = cmd.getParsedOptionValue("--unknown", null);
        assertNull(out, "When supplier is null and overloaded method returns null for null supplier, result should be null");
        assertNull(cmd.lastSeenSupplier, "Supplier forwarded should be null");
    }
}
