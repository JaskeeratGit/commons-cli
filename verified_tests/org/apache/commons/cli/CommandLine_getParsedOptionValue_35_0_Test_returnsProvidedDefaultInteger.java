package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
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
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValue_35_0_Test_returnsProvidedDefaultInteger {

    /**
     * Create a CommandLine instance by reflectively invoking the non-public constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    private CommandLine createCommandLine() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Provide empty lists and a null deprecatedHandler to avoid depending on Builder
        return ctor.newInstance(new LinkedList<String>(), new ArrayList<>(), null);
    }


    @Test
    public void returnsProvidedDefaultInteger() throws Exception {
        CommandLine cmd = createCommandLine();
        OptionGroup group = new OptionGroup();
        Integer defaultValue = Integer.valueOf(42);
        Integer result = cmd.getParsedOptionValue(group, defaultValue);
        assertSame(defaultValue, result);
        assertEquals(42, result.intValue());
    }



}
