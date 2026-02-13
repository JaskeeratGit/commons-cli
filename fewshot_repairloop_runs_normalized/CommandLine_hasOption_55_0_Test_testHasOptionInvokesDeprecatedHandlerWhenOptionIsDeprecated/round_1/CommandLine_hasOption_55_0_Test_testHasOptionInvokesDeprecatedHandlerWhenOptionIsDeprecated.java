package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_55_0_Test_testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }

    @Test
    public void testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated() throws Exception {
        // use the constructor with longOpt, hasArg and description
        Option dep = new Option("d", "deprecated", true, "deprecated option");
        // mark option as deprecated
        dep.setDeprecated(true);
        List<Option> options = new ArrayList<>();
        options.add(dep);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // The option exists and is deprecated, so hasOption should return true and invoke the handler once
        assertTrue(cmd.hasOption("d"));
        assertEquals(1, invoked.get());
        // Calling again should also return true and invoke handler again (by longOpt)
        assertTrue(cmd.hasOption("deprecated"));
        assertEquals(2, invoked.get());
    }

}
