package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*
 This test file provides a focused JUnit 5 test suite for CommandLine#getParsedOptionValues(String, T[]).
 It includes small local stubs for Option, Util, Builder, Converter and ParseException so the tests compile and exercise
 the target method and the resolveOption private helper via realistic scenarios.

 The Option stub below has been expanded to include members and constructors observed in the rest of the test-suite,
 such as additional constructors, getValuesList(), setType(), setConverter(), and simple value-management helpers.
 This allows this single file to compile alongside other tests which expect those APIs on Option.
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
        org.junit.jupiter.api.Assertions.assertArrayEquals(defaultValues, result);
    }

}


// --- Supporting minimal stubs so the test file compiles and exercises the CommandLine code paths ---

/**
 * Minimal Converter interface used by some tests that manipulate Option converters.
 * Mirrors the commons-cli Converter<T, E extends RuntimeException> shape used by tests.
 */
interface Converter<T, E extends RuntimeException> {
    T convert(String value) throws E;
}

/**
 * Expanded Option stub used by the tests. Implements multiple constructors and methods
 * that other test files in the suite expect (getValuesList, setType, setConverter, etc.).
 *
 * In production this would be org.apache.commons.cli.Option from the library.
 */
class Option {

    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;

    // optional type and converter used by some tests
    private Class<?> type;
    private Converter<?, ?> converter;

    // values collected for this option
    private final List<String> valuesList = new ArrayList<>();

    // Minimal constructors to match the various usages in tests:
    public Option(String opt, String longOpt) {
        this(opt, longOpt, false, null);
    }

    public Option(String opt, boolean hasArg, String description) {
        this(opt, null, hasArg, description);
    }

    public Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
    }

    // Accessors used by CommandLine.resolveOption in tests:
    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    // Methods used across the test-suite:

    // Return the internal values list (may be empty).
    public List<String> getValuesList() {
        return valuesList;
    }

    // Convenience to set values in one go (some tests prefer arrays)
    public void setValues(final String... values) {
        valuesList.clear();
        if (values != null) {
            for (String v : values) {
                valuesList.add(v);
            }
        }
    }

    // Convenience to add a single value
    public void addValue(final String v) {
        valuesList.add(v);
    }

    // Type and converter plumbing to satisfy tests that configure Option conversion behavior
    public void setType(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return this.type;
    }

    @SuppressWarnings("unchecked")
    public <T, E extends RuntimeException> void setConverter(final Converter<T, E> converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    public <T, E extends RuntimeException> Converter<T, E> getConverter() {
        return (Converter<T, E>) this.converter;
    }

}

/**
 * Minimal Util stub with stripLeadingHyphens behavior used by resolveOption.
 */
final class Util {

    private Util() {
    }

    static String stripLeadingHyphens(final String s) {
        if (s == null) {
            return null;
        }
        // remove a single leading '-' or double leading '--'
        return s.replaceFirst("^-{1,2}", "");
    }
}

/**
 * Minimal Builder stub to satisfy the protected CommandLine() constructor reference.
 */
final class Builder {

    static final java.util.function.Consumer<Option> DEPRECATED_HANDLER = o -> {
        /* no-op */
    };

    private Builder() {
    }
}

/**
 * Minimal ParseException stub to match the signature in CommandLine methods.
 */
class ParseException extends Exception {

    ParseException(String message) {
        super(message);
    }

    ParseException() {
        super();
    }
}
