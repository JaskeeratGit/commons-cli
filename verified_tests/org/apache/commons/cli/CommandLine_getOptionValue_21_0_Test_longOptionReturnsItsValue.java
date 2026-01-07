package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

class CommandLine_getOptionValue_21_0_Test_longOptionReturnsItsValue {

    /**
     * Helper to instantiate a CommandLine using its private constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    private CommandLine newCommandLineWithOptions(List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // pass empty args list and null deprecatedHandler
        return ctor.newInstance(new ArrayList<String>(), options, null);
    }

    /**
     * Helper to add a processed value to an Option by invoking
     * Option.addValueForProcessing(String) via reflection.
     */
    private void addValueToOption(Option opt, String value) throws Exception {
        Method m = null;
        // try the commonly used method name
        try {
            m = Option.class.getDeclaredMethod("addValueForProcessing", String.class);
        } catch (NoSuchMethodException e) {
            // try alternative common names if present
            try {
                m = Option.class.getDeclaredMethod("addValue", String.class);
            } catch (NoSuchMethodException ex) {
                // last resort: try setValue (less likely)
                m = Option.class.getDeclaredMethod("setValue", String.class);
            }
        }
        m.setAccessible(true);
        m.invoke(opt, value);
    }




    @Test
    void longOptionReturnsItsValue() throws Exception {
        // create an Option with short name "b" and long name "beta"
        Option opt = new Option("b", "beta", true, "desc");
        addValueToOption(opt, "valueB");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = newCommandLineWithOptions(options);
        // Using long option with two leading hyphens should resolve to longOpt
        String resultLong = cmd.getOptionValue("--beta", "fallback");
        assertEquals("valueB", resultLong, "Long option should return the set value");
        // Using long option without hyphens should also work
        String resultLongNoHyphen = cmd.getOptionValue("beta", "fallback");
        assertEquals("valueB", resultLongNoHyphen, "Long option without hyphens should return the set value");
    }

}
