package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 This test file provides minimal supporting stubs (Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameResolvesToLongOpt_thenOverloadedMethodReceivesMatchingOption {

    // Minimal Util stub used by resolveOption (kept as inner class to avoid relying on other classes)
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

    // Minimal Builder stub referred to by the no-arg constructor in CommandLine (no-op for tests)
    public static class Builder {

        public static final java.util.function.Consumer<Option> DEPRECATED_HANDLER = o -> {
            // no-op for tests
        };
    }

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
        public <T> T getParsedOptionValue(final Option opt, final Supplier<T> defaultValue) throws org.apache.commons.cli.ParseException {
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
    void whenOptionNameResolvesToLongOpt_thenOverloadedMethodReceivesMatchingOption() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Use the real org.apache.commons.cli.Option constructor available in the library
        Option opt = new Option("x", "long-name", false, "desc");
        setOptionsField(cmd, new ArrayList<>(Collections.singletonList(opt)));
        Supplier<String> supplier = () -> "long-default";
        String out = cmd.getParsedOptionValue("--long-name", supplier);
        assertEquals("long-default", out);
        assertNotNull(cmd.lastSeenOption);
        assertEquals("long-name", cmd.lastSeenOption.getLongOpt());
        assertSame(supplier, cmd.lastSeenSupplier);
    }

}
