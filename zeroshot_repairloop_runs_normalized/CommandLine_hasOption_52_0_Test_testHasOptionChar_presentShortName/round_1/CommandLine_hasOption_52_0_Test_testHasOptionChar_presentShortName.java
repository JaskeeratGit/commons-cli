package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for CommandLine.hasOption(char)
 *
 * The tests instantiate CommandLine via its private constructor using reflection,
 * providing a controlled list of Option instances.
 */
public class CommandLine_hasOption_52_0_Test_testHasOptionChar_presentShortName {

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
    public void testHasOptionChar_presentShortName() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Option short name "a"
        options.add(Option.builder("a").build());
        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('a'), "Expected hasOption('a') to be true when an Option with opt 'a' is present");
        assertFalse(cmd.hasOption('b'), "Expected hasOption('b') to be false when no Option with opt 'b' is present");
    }


}
