package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Focused JUnit 5 test for CommandLine#getParsedOptionValues(String, T[]).
 *
 * NOTE: This test uses the real org.apache.commons.cli.Option/ParseException/etc. classes
 * from the library and therefore must not declare package-level stubs that would
 * conflict with the production classes (those stubs caused compilation failures
 * across the test-suite).
 */
public class CommandLine_getParsedOptionValues_50_0_Test_resolvesByShortOptAndReturnsProvidedValues {

    /**
     * Subclass of the production CommandLine that allows injecting the internal 'options' list
     * via reflection and that overrides the Option-variant of getParsedOptionValues so we can
     * control the returned values deterministically for the test.
     */
    static class TestableCommandLine extends CommandLine {

        private final Object[] resultsToReturn;

        TestableCommandLine(List<Option> options, Object[] resultsToReturn) {
            // call protected no-arg constructor in CommandLine
            super();
            this.resultsToReturn = resultsToReturn != null ? resultsToReturn : new Object[0];
            // inject the private 'options' field to use our list of Option instances
            try {
                Field optionsField = CommandLine.class.getDeclaredField("options");
                optionsField.setAccessible(true);
                optionsField.set(this, options);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Override the delegating variant so that calls to the public getParsedOptionValues(String, T[])
        // dispatch here and we can return predictable results for assertions.
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] getParsedOptionValues(final Option option, final T[] defaultValue) throws ParseException {
            if (option == null) {
                // mimic expected behavior: return the provided default
                return defaultValue;
            }
            // build an array with the same component type as defaultValue and populate with our results
            Class<?> componentType = (defaultValue != null) ? defaultValue.getClass().getComponentType() : Object.class;
            T[] arr = (T[]) Array.newInstance(componentType, resultsToReturn.length);
            for (int i = 0; i < resultsToReturn.length; i++) {
                arr[i] = (T) resultsToReturn[i];
            }
            return arr;
        }
    }

    @Test
    public void resolvesByShortOptAndReturnsProvidedValues() throws Exception {
        // use the real Option class from the library
        Option opt = new Option("a", "alpha");
        List<Option> options = new ArrayList<>();
        options.add(opt);

        String[] defaultValues = new String[] { "d" };
        String[] produced = new String[] { "v1", "v2" };

        TestableCommandLine cl = new TestableCommandLine(options, produced);

        // passing the short option name ("a") should resolve to the injected Option via CommandLine.resolveOption
        String[] result = cl.getParsedOptionValues("a", defaultValues);

        assertArrayEquals(produced, result);
    }
}
