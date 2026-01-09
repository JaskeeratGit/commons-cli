package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 This test file focuses on exercising CommandLine#getParsedOptionValue(String, Supplier).
 It subclasses the real CommandLine to intercept calls to the overloaded method
 getParsedOptionValue(Option, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameIsNull_thenResolveOptionReceivesNull_andOverloadedMethodGetsNull {

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
    void whenOptionNameIsNull_thenResolveOptionReceivesNull_andOverloadedMethodGetsNull() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Put an option just to ensure list is non-empty; resolveOption should still return null for null input
        Option opt = new Option("p", "pp");
        setOptionsField(cmd, new ArrayList<>(Collections.singletonList(opt)));
        Supplier<String> supplier = () -> "nil-default";
        // Cast null to String to avoid ambiguous overload resolution between String and Option
        String out = cmd.getParsedOptionValue((String) null, supplier);
        assertEquals("nil-default", out);
        assertNull(cmd.lastSeenOption, "resolveOption(null) should produce null; overloaded method should get null");
        assertSame(supplier, cmd.lastSeenSupplier);
    }

}
