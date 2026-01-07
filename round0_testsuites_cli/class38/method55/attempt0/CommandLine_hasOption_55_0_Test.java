package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

public class CommandLine_hasOption_55_0_Test {

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

    @Test
    public void testHasOptionFindsShortAndLongOptAndDoesNotInvokeHandlerForNonDeprecated() throws Exception {
        Option opt = new Option("a", "alpha", false);
        List<Option> options = new ArrayList<>();
        options.add(opt);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // Using short name
        assertTrue(cmd.hasOption("a"));
        // Using long name
        assertTrue(cmd.hasOption("alpha"));
        // Ensure deprecated handler was not invoked
        assertEquals(0, invoked.get());
    }

    @Test
    public void testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated() throws Exception {
        Option dep = new Option("d", "deprecated", true);
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

    @Test
    public void testResolveOptionPrivateMethodViaReflection() throws Exception {
        Option opt1 = new Option("x", "xlong", false);
        Option opt2 = new Option("y", "ylong", false);
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

/*
 Minimal Option class to support testing. Placed in the same package as CommandLine
 so it matches the expected type used by the CommandLine class under test.
*/
class Option {

    private final String opt;

    private final String longOpt;

    private final boolean deprecated;

    public Option(String opt, String longOpt, boolean deprecated) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.deprecated = deprecated;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}
