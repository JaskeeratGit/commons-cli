package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 This test file provides minimal supporting stubs (Option, ParseException, Util, Builder)
 required for the CommandLine class to operate in the test environment and focuses on
 exercising CommandLine#getParsedOptionValue(String, Supplier).
*/
class CommandLine_getParsedOptionValue_37_0_Test_privateResolveOption_behavior_directInvocation_viaReflection {

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
    private static void setOptionsField(final CommandLine target, final List<?> options) throws Exception {
        Field f = CommandLine.class.getDeclaredField("options");
        f.setAccessible(true);
        f.set(target, options);
    }

    // Create an instance of the production org.apache.commons.cli.Option via reflection.
    private static Object createProdOption(final String opt, final String longOpt) throws Exception {
        Class<?> prodOptionClass = Class.forName("org.apache.commons.cli.Option");
        // Try various constructor signatures
        // 1) (String, String)
        try {
            Constructor<?> c = prodOptionClass.getConstructor(String.class, String.class);
            return c.newInstance(opt, longOpt);
        } catch (NoSuchMethodException ignored) {
        }
        // 2) (String, String, boolean, String)
        try {
            Constructor<?> c = prodOptionClass.getConstructor(String.class, String.class, boolean.class, String.class);
            return c.newInstance(opt, longOpt, false, null);
        } catch (NoSuchMethodException ignored) {
        }
        // 3) (String)
        try {
            Constructor<?> c = prodOptionClass.getConstructor(String.class);
            Object instance = c.newInstance(opt);
            // try setter for longOpt
            try {
                Method m = prodOptionClass.getMethod("setLongOpt", String.class);
                m.invoke(instance, longOpt);
                return instance;
            } catch (NoSuchMethodException nsme) {
                // try field access
                try {
                    Field f = prodOptionClass.getDeclaredField("longOpt");
                    f.setAccessible(true);
                    f.set(instance, longOpt);
                    return instance;
                } catch (NoSuchFieldException nsfe) {
                    // fallthrough
                }
            }
            return instance;
        } catch (NoSuchMethodException ignored) {
        }
        // 4) try builder(String) API if present
        try {
            Method builderMethod = prodOptionClass.getMethod("builder", String.class);
            Object builder = builderMethod.invoke(null, opt);
            // try longOpt(String)
            try {
                Method longOptMethod = builder.getClass().getMethod("longOpt", String.class);
                longOptMethod.invoke(builder, longOpt);
            } catch (NoSuchMethodException nsme) {
                // ignore
            }
            // build()
            Method buildMethod = builder.getClass().getMethod("build");
            return buildMethod.invoke(builder);
        } catch (NoSuchMethodException ignored) {
        }
        // 5) fallback: try constructor (char, String) where first param is char
        try {
            Constructor<?> c = prodOptionClass.getConstructor(char.class, String.class);
            char ch = (opt != null && !opt.isEmpty()) ? opt.charAt(0) : '\0';
            return c.newInstance(ch, longOpt);
        } catch (NoSuchMethodException ignored) {
        }
        throw new IllegalStateException("Could not instantiate production Option class");
    }

    @Test
    void privateResolveOption_behavior_directInvocation_viaReflection() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // create Option instances using the production Option so resolveOption can cast correctly
        Object prodOpt1 = createProdOption("a", "alpha");
        Object prodOpt2 = createProdOption("b", "beta");
        setOptionsField(cmd, new ArrayList<>(Arrays.asList(prodOpt1, prodOpt2)));
        // Invoke private resolveOption directly
        java.lang.reflect.Method m = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        m.setAccessible(true);
        Object resolvedShort = m.invoke(cmd, "-a");
        assertSame(prodOpt1, resolvedShort);
        Object resolvedLong = m.invoke(cmd, "--beta");
        assertSame(prodOpt2, resolvedLong);
        Object none = m.invoke(cmd, "--does-not-exist");
        assertNull(none);
        // edge: only hyphens -> empty string -> no match
        Object strippedEmpty = m.invoke(cmd, "----");
        assertNull(strippedEmpty);
    }

}
