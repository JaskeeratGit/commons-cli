package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * JUnit 5 tests for CommandLine.hasOption(char)
 *
 * The tests instantiate CommandLine via its private constructor using reflection,
 * providing a controlled list of Option instances. Mockito is used to create Option
 * instances with non-alphanumeric short names without triggering Option validation.
 */
public class CommandLine_hasOption_52_0_Test_testHasOptionChar_nonAlphanumericChar {

    /**
     * Helper to create a CommandLine instance using the private constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    @SuppressWarnings("unchecked")
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    public void testHasOptionChar_nonAlphanumericChar() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        // Create a mock Option that reports its short opt as "-"
        Option dashOpt = mock(Option.class);
        when(dashOpt.getOpt()).thenReturn("-");

        options.add(dashOpt);

        CommandLine cmd = createCommandLine(args, options, null);

        // '-' was provided by the mocked Option, so hasOption('-') should be true
        assertTrue(cmd.hasOption('-'), "Expected hasOption('-') to be true when Option with opt '-' is present");

        // '_' was not provided, so hasOption('_') should be false
        assertFalse(cmd.hasOption('_'), "Expected hasOption('_') to be false when it's not present");
    }
}
