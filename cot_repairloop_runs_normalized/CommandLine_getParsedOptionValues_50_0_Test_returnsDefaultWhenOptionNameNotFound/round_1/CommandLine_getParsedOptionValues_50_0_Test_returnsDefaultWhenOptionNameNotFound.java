package org.apache.commons.cli;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/*
 This test file provides a focused JUnit 5 test suite for CommandLine#getParsedOptionValues(String, T[]).
 It includes a small TestableCommandLine subclass that injects the internal 'options' list via reflection
 and overrides getParsedOptionValues(Option, T[]) to provide predictable outputs for testing.
*/
public class CommandLine_getParsedOptionValues_50_0_Test_returnsDefaultWhenOptionNameNotFound {

    // A small subclass that allows injecting the internal 'options' list via reflection
    // and overrides getParsedOptionValues(Option, T[]) to provide predictable outputs for testing.
    static class TestableCommandLine extends CommandLine {

        private final Object[] resultsToReturn;

        TestableCommandLine(List<Option> options, Object[] resultsToReturn) {
            // call protected no-arg constructor
            super();
            this.resultsToReturn = resultsToReturn != null ? resultsToReturn : new Object[0];
            // inject the private final 'options' field to the desired list
            try {
                Field optionsField = CommandLine.class.getDeclaredField("options");
                optionsField.setAccessible(true);
                optionsField.set(this, options);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Override the delegating variant so that the public getParsedOptionValues(String, T[]) call
        // will dispatch here and we can control the returned values for testing.
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] getParsedOptionValues(final Option option, final T[] defaultValue) throws ParseException {
            if (option == null) {
                // mimic expected behavior: return the provided default
                return defaultValue;
            }
            // build an array of the same component type as defaultValue and populate with our resultsToReturn
            Class<?> componentType = (defaultValue != null) ? defaultValue.getClass().getComponentType() : Object.class;
            T[] arr = (T[]) Array.newInstance(componentType, resultsToReturn.length);
            for (int i = 0; i < resultsToReturn.length; i++) {
                arr[i] = (T) resultsToReturn[i];
            }
            return arr;
        }
    }

    @Test
    public void returnsDefaultWhenOptionNameNotFound() throws Exception {
        // empty list so resolveOption returns null
        List<Option> options = new ArrayList<>();
        String[] defaultValues = new String[] { "def1", "def2" };
        TestableCommandLine cl = new TestableCommandLine(options, new Object[] { "should", "not", "be", "used" });
        String[] result = cl.getParsedOptionValues("nonexistent", defaultValues);
        // expect the default to be returned (content equal)
        assertArrayEquals(defaultValues, result);
    }

}
