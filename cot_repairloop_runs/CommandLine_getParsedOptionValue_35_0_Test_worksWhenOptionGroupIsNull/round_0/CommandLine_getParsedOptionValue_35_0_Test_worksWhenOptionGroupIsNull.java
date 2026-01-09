package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class CommandLine_getParsedOptionValue_35_0_Test_worksWhenOptionGroupIsNull {

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
    public void worksWhenOptionGroupIsNull() throws Exception {
        CommandLine cmd = createCommandLine();
        String defaultValue = "noGroup";
        // cast null to OptionGroup to avoid ambiguity with overload that takes String
        String result = cmd.getParsedOptionValue((OptionGroup) null, defaultValue);
        // Even with a null OptionGroup, method should delegate to the supplier and return the default
        assertSame(defaultValue, result);
    }

}
