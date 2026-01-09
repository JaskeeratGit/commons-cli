package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 This test file focuses on exercising CommandLine#getParsedOptionValue(String, Supplier)
 and resolving options via the private resolveOption method.
*/
class CommandLine_getParsedOptionValue_37_0_Test_privateResolveOption_behavior_directInvocation_viaReflection {

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
    void privateResolveOption_behavior_directInvocation_viaReflection() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // Use constructor that sets a long option name: Option(String opt, String longOpt, boolean hasArg, String description)
        Option opt1 = new Option("a", "alpha", false, "alpha option");
        Option opt2 = new Option("b", "beta", false, "beta option");
        setOptionsField(cmd, new ArrayList<>(Arrays.asList(opt1, opt2)));
        // Invoke private resolveOption directly
        java.lang.reflect.Method m = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        m.setAccessible(true);
        Object resolvedShort = m.invoke(cmd, "-a");
        assertSame(opt1, resolvedShort);
        // The implementation under test strips a single leading hyphen before matching;
        // use the single-hyphen form to resolve the long option name.
        Object resolvedLong = m.invoke(cmd, "-beta");
        assertSame(opt2, resolvedLong);
        Object none = m.invoke(cmd, "--does-not-exist");
        assertNull(none);
        // edge: only hyphens -> empty string -> no match
        Object strippedEmpty = m.invoke(cmd, "----");
        assertNull(strippedEmpty);
    }

}
