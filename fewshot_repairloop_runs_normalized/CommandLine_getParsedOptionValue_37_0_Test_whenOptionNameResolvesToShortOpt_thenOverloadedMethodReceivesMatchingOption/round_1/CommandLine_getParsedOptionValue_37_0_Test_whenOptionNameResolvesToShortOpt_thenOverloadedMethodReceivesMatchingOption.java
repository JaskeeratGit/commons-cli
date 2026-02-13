package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;

/*
 This test file provides minimal supporting stubs (Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_whenOptionNameResolvesToShortOpt_thenOverloadedMethodReceivesMatchingOption {

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

        public static final Consumer<org.apache.commons.cli.Option> DEPRECATED_HANDLER = o -> {
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
    private static void setOptionsField(final CommandLine target, final List<org.apache.commons.cli.Option> options) throws Exception {
        Field f = CommandLine.class.getDeclaredField("options");
        f.setAccessible(true);
        f.set(target, options);
    }


    @Test
    void whenOptionNameResolvesToShortOpt_thenOverloadedMethodReceivesMatchingOption() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        org.apache.commons.cli.Option opt = mock(org.apache.commons.cli.Option.class);
        when(opt.getOpt()).thenReturn("a");
        when(opt.getLongOpt()).thenReturn("alpha");
        setOptionsField(cmd, new ArrayList<>(Collections.singletonList(opt)));
        Supplier<Integer> defaultSupplier = () -> 42;
        Integer val = cmd.getParsedOptionValue("-a", defaultSupplier);
        assertEquals(Integer.valueOf(42), val, "Should return supplier value when overloaded method returns default supplier result");
        assertNotNull(cmd.lastSeenOption, "Option should not be null for a matching short option");
        assertEquals("a", cmd.lastSeenOption.getOpt());
        assertSame(defaultSupplier, cmd.lastSeenSupplier);
    }




}
