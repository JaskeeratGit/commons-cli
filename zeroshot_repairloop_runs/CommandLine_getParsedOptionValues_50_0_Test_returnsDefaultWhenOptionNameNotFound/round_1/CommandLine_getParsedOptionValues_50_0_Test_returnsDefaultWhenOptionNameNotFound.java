package org.apache.commons.cli;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 This test file provides a focused JUnit 5 test suite for CommandLine#getParsedOptionValues(String, T[]).
 It includes small local stubs for Option, Util, Builder and ParseException so the tests compile and exercise
 the target method and the resolveOption private helper via realistic scenarios.
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

// --- Supporting minimal stubs so the test file compiles and exercises the CommandLine code paths ---
/**
 * Minimal Converter stub used by tests that set a converter on Option.
 */
interface Converter<T, X extends RuntimeException> {
    T convert(String value) throws X;
}

/**
 * Minimal Option stub used by the tests. Provides constructors and methods commonly exercised by tests:
 * - multiple constructors
 * - getOpt, getLongOpt
 * - getValuesList returning a modifiable list
 * - setType and setConverter
 *
 * In production this would be org.apache.commons.cli.Option from the library.
 */
class Option {

    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;
    private final List<String> valuesList = new ArrayList<>();
    private Class<?> type;
    @SuppressWarnings("rawtypes")
    private Converter converter;

    // Original simple constructor
    Option(String opt, String longOpt) {
        this(opt, longOpt, false, null);
    }

    // Constructor used in many tests: Option(String opt, boolean hasArg, String description)
    Option(String opt, boolean hasArg, String description) {
        this(opt, null, hasArg, description);
    }

    // Full constructor used by some tests: Option(String opt, String longOpt, boolean hasArg, String description)
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

    /**
     * Returns a modifiable list of values associated with this Option (can be empty).
     * Tests populate this list directly.
     */
    public List<String> getValuesList() {
        return valuesList;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public String getDescription() {
        return description;
    }

    public void setType(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <T, X extends RuntimeException> void setConverter(final Converter<T, X> conv) {
        this.converter = conv;
    }

    @SuppressWarnings("unchecked")
    public <T, X extends RuntimeException> Converter<T, X> getConverter() {
        return converter;
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
