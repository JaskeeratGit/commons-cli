package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getOptionValue_15_0_Test {

    // Helper to instantiate CommandLine via its private constructor:
    private CommandLine newCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    // Helper to add an internal value to Option via reflection
    @SuppressWarnings("unchecked")
    private void addOptionValue(Option option, String value) throws Exception {
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        List<String> values = (List<String>) valuesField.get(option);
        if (values == null) {
            values = new ArrayList<>();
            valuesField.set(option, values);
        }
        values.add(value);
    }

    // Helper to invoke the exact overload getOptionValue(Option, String) via reflection
    private String invokeGetOptionValue_String(CommandLine cmd, Option opt, String defaultValue) throws Exception {
        Method m = CommandLine.class.getMethod("getOptionValue", Option.class, String.class);
        return (String) m.invoke(cmd, opt, defaultValue);
    }

    @Test
    public void returnsDefaultWhenOptionNotPresent() throws Exception {
        // create empty options list for CommandLine
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        CommandLine cmd = newCommandLine(args, options, null);
        Option opt = new Option("o", true, "option o");
        // option not present in command line -> should return provided default
        String result = invokeGetOptionValue_String(cmd, opt, "theDefault");
        assertEquals("theDefault", result);
    }

    @Test
    public void returnsNullWhenOptionNotPresentAndDefaultIsNull() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        CommandLine cmd = newCommandLine(args, options, null);
        Option opt = new Option("x", false, "option x");
        String result = invokeGetOptionValue_String(cmd, opt, null);
        assertNull(result);
    }

    @Test
    public void returnsOptionValueWhenOptionPresent() throws Exception {
        // prepare an Option and inject a value into its private values list
        Option opt = new Option("v", true, "option v");
        addOptionValue(opt, "actualValue");
        // place the option into the CommandLine's options list via private ctor
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = newCommandLine(args, options, null);
        // expecting the option's actual value rather than the default
        String result = invokeGetOptionValue_String(cmd, opt, "unusedDefault");
        assertEquals("actualValue", result);
    }

    @Test
    public void invokePublicMethodViaReflection_and_checkResults() throws Exception {
        // instantiate command line with no options
        CommandLine cmd = newCommandLine(new ArrayList<>(), new ArrayList<>(), null);
        Option opt = new Option("r", false, "option r");
        // obtain the getOptionValue(Option,String) method reflectively and invoke it
        Method m = CommandLine.class.getMethod("getOptionValue", Option.class, String.class);
        Object invoked = m.invoke(cmd, opt, "reflectDefault");
        assertTrue(invoked == null || invoked instanceof String);
        assertEquals("reflectDefault", invoked);
    }
}
