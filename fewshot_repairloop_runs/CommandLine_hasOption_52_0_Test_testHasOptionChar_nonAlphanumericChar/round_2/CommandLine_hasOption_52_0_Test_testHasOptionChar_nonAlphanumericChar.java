package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_52_0_Test_testHasOptionChar_nonAlphanumericChar {

    /**
     * Helper to create a CommandLine instance using the private constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    public void testHasOptionChar_nonAlphanumericChar() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Create a mock Option that reports a non-alphanumeric short name (e.g. "-")
        Option opt = org.mockito.Mockito.mock(Option.class);
        org.mockito.Mockito.when(opt.getOpt()).thenReturn("-");
        org.mockito.Mockito.when(opt.getLongOpt()).thenReturn(null);
        options.add(opt);

        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('-'), "Expected hasOption('-') to be true when Option with opt '-' is present");
        assertFalse(cmd.hasOption('_'), "Expected hasOption('_') to be false when it's not present");
    }
}
