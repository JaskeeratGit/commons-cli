package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * JUnit 5 tests for CommandLine.hasOption(char)
 *
 * The tests instantiate CommandLine via its private constructor using reflection,
 * providing a controlled list of Option instances (a small local stub of Option is
 * provided below to satisfy compilation/runtime expectations).
 */
public class CommandLine_hasOption_52_0_Test {

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
        options.add(new Option("a"));
        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('a'), "Expected hasOption('a') to be true when an Option with opt 'a' is present");
        assertFalse(cmd.hasOption('b'), "Expected hasOption('b') to be false when no Option with opt 'b' is present");
    }

    @Test
    public void testHasOptionChar_absent() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Option with a different short name
        options.add(new Option("x"));
        options.add(new Option("y"));
        CommandLine cmd = createCommandLine(args, options, o -> {
            /* noop deprecated handler */
        });
        assertFalse(cmd.hasOption('a'), "Expected hasOption('a') to be false when no matching Option exists");
        assertTrue(cmd.hasOption('x'), "Expected hasOption('x') to be true for matching option 'x'");
    }

    @Test
    public void testHasOptionChar_nonAlphanumericChar() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Use a punctuation short name, e.g. "-"
        options.add(new Option("-"));
        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('-'), "Expected hasOption('-') to be true when Option with opt '-' is present");
        assertFalse(cmd.hasOption('_'), "Expected hasOption('_') to be false when it's not present");
    }
}

/*
 * Minimal stub of Option class to enable testing of CommandLine behavior.
 * Placed in the same package as CommandLine (org.apache.commons.cli) so that
 * the CommandLine implementation under test can interact with it.
 *
 * This stub provides the simple accessor getOpt() that typical CommandLine
 * implementations consult when resolving short option names.
 */
class Option {

    private final String opt;

    private String longOpt;

    public Option(String opt) {
        this.opt = opt;
    }

    public Option(String opt, String longOpt) {
        this.opt = opt;
        this.longOpt = longOpt;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    @Override
    public String toString() {
        return "Option{opt='" + opt + "', longOpt='" + longOpt + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Option))
            return false;
        Option other = (Option) obj;
        if (opt == null)
            return other.opt == null;
        return opt.equals(other.opt);
    }

    @Override
    public int hashCode() {
        return opt == null ? 0 : opt.hashCode();
    }
}
