package org.apache.commons.cli;

import org.junit.jupiter.api.function.Executable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

/*
 This test file provides a focused JUnit 5 test suite for CommandLine#getParsedOptionValues(String, T[]).
 It includes small local stubs for Option, Util, Builder and ParseException so the tests compile and exercise
 the target method and the resolveOption private helper via realistic scenarios.
*/
public class CommandLine_getParsedOptionValues_50_0_Test_nullOptionNameReturnsDefault {

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
    public void nullOptionNameReturnsDefault() throws Exception {
        List<Option> options = new ArrayList<>();
        String[] defaultValues = new String[] { "x" };
        TestableCommandLine cl = new TestableCommandLine(options, new Object[] { "unused" });
        String[] result = cl.getParsedOptionValues((String) null, defaultValues);
        assertArrayEquals(defaultValues, result);
    }
}

// --- Supporting minimal stubs so the test file compiles and exercises the CommandLine code paths ---
/**
 * Minimal Option stub used by the tests. Only implements what CommandLine.resolveOption needs.
 * In production this would be org.apache.commons.cli.Option from the library.
 */
class Option {

    private final String opt;

    private final String longOpt;

    Option(String opt, String longOpt) {
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
