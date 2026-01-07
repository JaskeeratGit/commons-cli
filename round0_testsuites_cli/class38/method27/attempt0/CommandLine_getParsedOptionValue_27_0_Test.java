package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValue_27_0_Test {

    @Test
    public void testDelegatesToStringVersion_returnsValue() throws Exception {
        final AtomicReference<String> captured = new AtomicReference<>();
        // Use the protected no-arg constructor by subclassing to override the String-version method.
        CommandLine cmd = new CommandLine() {

            @Override
            public <T> T getParsedOptionValue(final String optionName) throws ParseException {
                captured.set(optionName);
                // return a typed value to verify generics behavior
                return (T) ("parsed:" + optionName);
            }
        };
        // should delegate to getParsedOptionValue(String)
        String result = cmd.getParsedOptionValue('a');
        assertEquals("parsed:a", result);
        assertEquals("a", captured.get());
    }

    @Test
    public void testDelegatesToStringVersion_propagatesParseException() {
        CommandLine cmd = new CommandLine() {

            @Override
            public <T> T getParsedOptionValue(final String optionName) throws ParseException {
                throw new ParseException("simulated failure for: " + optionName);
            }
        };
        assertThrows(ParseException.class, () -> cmd.getParsedOptionValue('z'));
    }

    @Test
    public void testPrivateConstructor_assignsFields_andAccessibleViaReflection() throws Exception {
        List<String> args = new LinkedList<>();
        args.add("arg1");
        final List<Option> options = new ArrayList<>();
        options.add(new Option());
        Consumer<Option> handler = o -> {
            // no-op for test
        };
        // Obtain the private constructor and create an instance without invoking the protected default ctor.
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance(args, options, handler);
        // Reflectively access private fields to ensure they were set by the constructor
        Field argsField = CommandLine.class.getDeclaredField("args");
        argsField.setAccessible(true);
        Object actualArgs = argsField.get(cmd);
        assertSame(args, actualArgs);
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        Object actualOptions = optionsField.get(cmd);
        assertSame(options, actualOptions);
        Field deprecatedHandlerField = CommandLine.class.getDeclaredField("deprecatedHandler");
        deprecatedHandlerField.setAccessible(true);
        Object actualHandler = deprecatedHandlerField.get(cmd);
        assertSame(handler, actualHandler);
    }
}

/*
 * Minimal supporting stubs to allow this test to compile and run independently.
 * These match the minimal signatures referenced by CommandLine and the tests.
 * In a real project these would be provided by the library under test.
 */
class Option {
    // minimal stub
}

class ParseException extends Exception {

    public ParseException(final String message) {
        super(message);
    }
}

class Builder {

    // Provide a default deprecated handler used by the protected CommandLine() constructor.
    static final Consumer<Option> DEPRECATED_HANDLER = o -> {
        // no-op stub
    };
}
