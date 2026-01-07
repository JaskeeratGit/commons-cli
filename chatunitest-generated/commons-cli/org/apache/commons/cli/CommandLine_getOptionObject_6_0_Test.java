package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandLine_getOptionObject_6_0_Test {

    @Test
    void delegatesToStringOverload() {
        AtomicReference<String> captured = new AtomicReference<>();
        // Use an anonymous subclass to override getOptionObject(String) and capture the argument
        CommandLine cmd = new CommandLine() {

            @Override
            public Object getOptionObject(final String optionName) {
                captured.set(optionName);
                return "VALUE:" + optionName;
            }
        };
        Object result = cmd.getOptionObject('a');
        assertEquals("VALUE:a", result, "Should return the overridden method's result");
        assertEquals("a", captured.get(), "Should pass the char converted to a String to the String overload");
    }

    @Test
    void reflectionInvocationOfCharMethod() throws Exception {
        // Ensure the char overload can be invoked via reflection and still delegates
        CommandLine cmd = new CommandLine() {

            @Override
            public Object getOptionObject(final String optionName) {
                return "R:" + optionName;
            }
        };
        Method method = CommandLine.class.getMethod("getOptionObject", char.class);
        Object invoked = method.invoke(cmd, 'z');
        assertEquals("R:z", invoked);
    }

    @Test
    void handlesNullReturnFromStringOverloadAndSpecialChar() {
        AtomicReference<String> captured = new AtomicReference<>();
        CommandLine cmd = new CommandLine() {

            @Override
            public Object getOptionObject(final String optionName) {
                captured.set(optionName);
                // simulate underlying logic returning null
                return null;
            }
        };
        // special null char
        Object result = cmd.getOptionObject('\u0000');
        assertNull(result, "If the String overload returns null the char overload should return null");
        assertEquals("\u0000", captured.get(), "Should pass the exact single-character string");
    }
}
