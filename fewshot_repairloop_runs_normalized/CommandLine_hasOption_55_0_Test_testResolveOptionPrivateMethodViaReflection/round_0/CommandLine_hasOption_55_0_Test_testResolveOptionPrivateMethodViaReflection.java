package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_55_0_Test_testResolveOptionPrivateMethodViaReflection {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }

    @Test
    public void testResolveOptionPrivateMethodViaReflection() throws Exception {
        Option opt1 = new Option("x", "xlong", false, "desc");
        Option opt2 = new Option("y", "ylong", false, "desc");
        List<Option> options = new ArrayList<>();
        options.add(opt1);
        options.add(opt2);
        CommandLine cmd = createCommandLine(options, o -> {
        });
        // Invoke private resolveOption method reflectively
        Method resolve = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        resolve.setAccessible(true);
        // Should find by short opt (with or without hyphen)
        Object found1 = resolve.invoke(cmd, "-x");
        assertSame(opt1, found1);
        Object found1b = resolve.invoke(cmd, "x");
        assertSame(opt1, found1b);
        // Should find by long opt (with or without double hyphen)
        Object found2 = resolve.invoke(cmd, "--ylong");
        assertSame(opt2, found2);
        Object found2b = resolve.invoke(cmd, "ylong");
        assertSame(opt2, found2b);
        // Non-existent option should return null
        Object notFound = resolve.invoke(cmd, "nope");
        assertNull(notFound);
    }
}
