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

class CommandLine_getOptionValue_21_0_Test {

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

    @Test
    void nullOptionNameReturnsDefault() throws Exception {
        CommandLine cmd = newCommandLineWithOptions(new ArrayList<>());
        // cast null to String to avoid ambiguity with other overloads
        String result = cmd.getOptionValue((String) null, "default");
        assertEquals("default", result, "When optionName is null should return default");
    }

    @Test
    void unknownOptionReturnsDefault() throws Exception {
        CommandLine cmd = newCommandLineWithOptions(new ArrayList<>());
        // option not present in options list -> should return default
        String result = cmd.getOptionValue("--doesnotexist", "fallback");
        assertEquals("fallback", result, "Unknown option should return provided default");
    }

    @Test
    void optionWithoutValueReturnsDefault() throws Exception {
        // option exists but has no value -> should return default
        Option opt = new Option("c", "gamma", false, "desc");
        // do NOT add a value
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = newCommandLineWithOptions(options);
        String result = cmd.getOptionValue("c", "myDefault");
        assertEquals("myDefault", result, "Option without value should yield provided default");
    }
}
