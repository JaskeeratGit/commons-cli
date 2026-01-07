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

class CommandLine_getOptionValue_21_0_Test_shortOptionReturnsItsValue {

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
    void shortOptionReturnsItsValue() throws Exception {
        // create an Option with short name "a" and long name "alpha"
        Option opt = new Option("a", "alpha", true, "desc");
        // put value into option as if parsed
        addValueToOption(opt, "valueA");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = newCommandLineWithOptions(options);
        // Using short option name (no hyphens) should resolve and return its value
        String resultShort = cmd.getOptionValue("a", "fallback");
        assertEquals("valueA", resultShort, "Short option should return the set value");
        // Using short option with leading hyphen also should work (stripLeadingHyphens)
        String resultShortWithHyphen = cmd.getOptionValue("-a", "fallback");
        assertEquals("valueA", resultShortWithHyphen, "Short option with hyphen should return the set value");
    }


}
