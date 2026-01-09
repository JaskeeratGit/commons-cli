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
        // Use the constructor with (opt, longOpt, hasArg, description)
        Option dep = new Option("d", "deprecated", true, "deprecated option");

        // Mark the option as deprecated. Try direct field set first, then try possible setter methods.
        boolean marked = false;
        try {
            java.lang.reflect.Field f = Option.class.getDeclaredField("deprecated");
            f.setAccessible(true);
            f.set(dep, Boolean.TRUE);
            marked = true;
        } catch (NoSuchFieldException ignored) {
            // try methods below
        }
        if (!marked) {
            try {
                Option.class.getMethod("setDeprecated", boolean.class).invoke(dep, true);
                marked = true;
            } catch (NoSuchMethodException ignored) {
                // try no-arg variant
                try {
                    Option.class.getMethod("setDeprecated").invoke(dep);
                    marked = true;
                } catch (NoSuchMethodException ignored2) {
                    // unable to mark as deprecated; leave as-is
                }
            }
        }

        List<Option> options = new ArrayList<>();
        options.add(dep);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // The option exists and is deprecated, so hasOption should return true and invoke the handler once
        assertTrue(cmd.hasOption("d"));
        assertEquals(1, invoked.get());
        // Calling again should also return true and invoke handler again
        assertTrue(cmd.hasOption("deprecated"));
        assertEquals(2, invoked.get());
    }

}
