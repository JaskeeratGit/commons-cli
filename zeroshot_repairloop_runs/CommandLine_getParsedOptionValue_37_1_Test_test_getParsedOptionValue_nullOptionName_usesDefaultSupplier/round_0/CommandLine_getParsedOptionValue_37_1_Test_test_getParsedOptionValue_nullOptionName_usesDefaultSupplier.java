package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommandLine#getParsedOptionValue(String, Supplier)
 */
public class CommandLine_getParsedOptionValue_37_1_Test_test_getParsedOptionValue_nullOptionName_usesDefaultSupplier {

    /**
     * A testable subclass that overrides getParsedOptionValue(Option, Supplier)
     * so we can observe how the string-based wrapper resolves and delegates.
     */
    private static class TestableCommandLine extends CommandLine {

        volatile Option lastOptionSeen;

        volatile Supplier<?> lastDefaultSupplierSeen;

        TestableCommandLine() {
            // protected no-arg constructor
            super();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getParsedOptionValue(final Option option, final Supplier<T> defaultValue) {
            this.lastOptionSeen = option;
            this.lastDefaultSupplierSeen = defaultValue;
            if (option == null) {
                // when option could not be resolved, return the default supplier value
                return defaultValue != null ? defaultValue.get() : null;
            }
            // For resolved option return a sentinel based on option.getOpt()
            return (T) ("received:" + option.getOpt());
        }
    }

    /**
     * Helper to inject the options list into a CommandLine instance using reflection.
     */
    private static void injectOptions(final CommandLine cmd, final List<Option> options) throws Exception {
        final Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(cmd, options);
    }



    @Test
    public void test_getParsedOptionValue_nullOptionName_usesDefaultSupplier() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine();
        // even with options present, passing null should lead resolveOption to return null
        List<Option> options = new ArrayList<>();
        options.add(new Option("x", "xlong", false, "desc"));
        injectOptions(cmd, options);
        String result = cmd.getParsedOptionValue((String) null, () -> "NULL_DEFAULT");
        assertNull(cmd.lastOptionSeen, "Expected resolved option to be null when optionName is null");
        assertEquals("NULL_DEFAULT", result, "Expected default supplier value when optionName is null");
    }
}
