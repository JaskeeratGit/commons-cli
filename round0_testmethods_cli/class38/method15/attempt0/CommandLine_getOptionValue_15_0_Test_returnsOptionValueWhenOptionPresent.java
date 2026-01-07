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

public class CommandLine_getOptionValue_15_0_Test_returnsOptionValueWhenOptionPresent {

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
        String result = cmd.getOptionValue(opt, "unusedDefault");
        assertEquals("actualValue", result);
    }

}
