package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for CommandLine.hasOption(String).
 *
 * Note: Do NOT declare a test-local Option class in this package — doing so
 * would shadow the real org.apache.commons.cli.Option used across the test-suite
 * and cause many unrelated compilation errors. This test intentionally uses the
 * Option class from the library under test.
 */
public class CommandLine_hasOption_55_0_Test_testHasOptionReturnsFalseWhenOptionNotPresent {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }

    @Test
    public void testHasOptionReturnsFalseWhenOptionNotPresent() throws Exception {
        List<Option> options = new ArrayList<>();
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // Unknown option name should resolve to null and return false
        assertFalse(cmd.hasOption("unknown"));
        // Ensure deprecated handler was not invoked
        assertEquals(0, invoked.get());
    }

}
