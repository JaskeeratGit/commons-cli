package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/*
 Fixed unit test for CommandLine#getParsedOptionValue(String, Supplier).
 The original test attempted to override a method using a nested Option type,
 which caused a compile-time mismatch with the real CommandLine's Option type.
 This version uses the library's org.apache.commons.cli.Option and
 org.apache.commons.cli.ParseException so the override correctly matches.
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameResolvesToLongOpt_thenOverloadedMethodReceivesMatchingOption {

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
    void whenOptionNameResolvesToLongOpt_thenOverloadedMethodReceivesMatchingOption() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Use the real org.apache.commons.cli.Option constructor (opt, longOpt, hasArg, description)
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
