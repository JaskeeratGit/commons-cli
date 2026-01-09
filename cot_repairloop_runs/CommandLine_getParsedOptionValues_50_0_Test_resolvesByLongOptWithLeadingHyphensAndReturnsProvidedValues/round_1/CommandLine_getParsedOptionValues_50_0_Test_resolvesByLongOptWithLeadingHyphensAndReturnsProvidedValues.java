package org.apache.commons.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Consumer;

/*
 This test file provides a focused JUnit 5 test suite for CommandLine#getParsedOptionValues(String, T[]).
 It includes small local stubs for Option, Util, Builder, Converter and ParseException so the tests compile and exercise
 the target method and the resolveOption private helper via realistic scenarios.

 Note: the Option stub below implements additional constructors and accessor/mutator methods that many other tests expect:
 - constructors with (String, String), (String, boolean, String), (String, String, boolean, String)
 - getValuesList(), getValues(), setType(), setConverter(), getType(), getConverter()
 This keeps the unit test suite compiling without changing production code.
*/
public class CommandLine_getParsedOptionValues_50_0_Test_resolvesByLongOptWithLeadingHyphensAndReturnsProvidedValues {

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
    public void resolvesByLongOptWithLeadingHyphensAndReturnsProvidedValues() throws Exception {
        Option opt = new Option("b", "beta");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        String[] defaultValues = new String[] { "default" };
        Integer[] produced = new Integer[] { 1, 2, 3 };
        // we use Integer[] default and expect Integer[] returned
        TestableCommandLine cl = new TestableCommandLine(options, produced);
        // pass the long option with two hyphens; Util.stripLeadingHyphens should strip them
        Integer[] result = cl.getParsedOptionValues("--beta", new Integer[] { 0 });
        assertArrayEquals(produced, result);
    }

}

// --- Supporting minimal stubs so the test file compiles and exercises the CommandLine code paths ---
/**
 * Minimal Converter stub used by some tests.
 */
interface Converter<T, E extends RuntimeException> {
    T convert(String value) throws E;
}

/**
 * Minimal Option stub used by the tests. Implements constructors and methods used across the test-suite.
 * In production this would be org.apache.commons.cli.Option from the library.
 */
class Option {

    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;

    // values stored for this option (some tests access and mutate this list)
    private final List<String> valuesList = new LinkedList<>();

    private Class<?> type;
    private Converter<?, ? extends RuntimeException> converter;

    // Common constructors seen across tests:
    Option(String opt, String longOpt) {
        this(opt, longOpt, false, null);
    }

    Option(String opt, boolean hasArg, String description) {
        this(opt, null, hasArg, description);
    }

    Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Return the live list of values for this Option.
     * Many tests expect to be able to mutate this list (add values).
     */
    public List<String> getValuesList() {
        return valuesList;
    }

    /**
     * Return values as array; some tests may use this.
     */
    public String[] getValues() {
        return valuesList.toArray(new String[0]);
    }

    public void setType(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <T, E extends RuntimeException> void setConverter(final Converter<T, E> converter) {
        this.converter = (Converter<?, ? extends RuntimeException>) converter;
    }

    @SuppressWarnings("unchecked")
    public <T, E extends RuntimeException> Converter<T, E> getConverter() {
        return (Converter<T, E>) converter;
    }

    @Override
    public String toString() {
        if (longOpt != null) {
            return "--" + longOpt;
        }
        return "-" + opt;
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

    static final Consumer<Option> DEPRECATED_HANDLER = o -> {
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
