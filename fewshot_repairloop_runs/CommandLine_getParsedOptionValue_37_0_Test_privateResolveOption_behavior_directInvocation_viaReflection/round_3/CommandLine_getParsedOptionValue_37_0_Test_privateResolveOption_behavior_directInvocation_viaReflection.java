package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;

/*
 This test file provides minimal supporting stubs (Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_privateResolveOption_behavior_directInvocation_viaReflection {

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

        public static final java.util.function.Consumer<org.apache.commons.cli.Option> DEPRECATED_HANDLER = o -> {
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
    void privateResolveOption_behavior_directInvocation_viaReflection() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        org.apache.commons.cli.Option opt1 = new org.apache.commons.cli.Option("a", "alpha");
        // create opt2 with a long option name "beta" so resolveOption("--beta") can match it
        org.apache.commons.cli.Option opt2 = new org.apache.commons.cli.Option("b", "beta", false, "beta");
        setOptionsField(cmd, new ArrayList<>(Arrays.asList(opt1, opt2)));
        // Invoke private resolveOption directly
        java.lang.reflect.Method m = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        m.setAccessible(true);
        Object resolvedShort = m.invoke(cmd, "-a");
        assertSame(opt1, resolvedShort);
        Object resolvedLong = m.invoke(cmd, "--beta");
        assertSame(opt2, resolvedLong);
        Object none = m.invoke(cmd, "--does-not-exist");
        assertNull(none);
        // edge: only hyphens -> empty string -> no match
        Object strippedEmpty = m.invoke(cmd, "----");
        assertNull(strippedEmpty);
    }

}
