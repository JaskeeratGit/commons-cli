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
 * providing a controlled list of Option instances (a small local stub of Option is
 * provided below to satisfy compilation/runtime expectations).
 */
public class CommandLine_hasOption_52_0_Test_testHasOptionChar_absent {

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

}

/*
 * Minimal stub of Option class to enable testing of CommandLine behavior.
 * Placed in the same package as CommandLine (org.apache.commons.cli) so that
 * the CommandLine implementation under test can interact with it.
 *
 * This stub provides the simple accessor getOpt() that typical CommandLine
 * implementations consult when resolving short option names.
 *
 * Additional constructors and methods are provided to satisfy other tests that
 * reference these members.
 */
class Option {

    private final String opt;
    private String longOpt;

    // Support holding values as some tests call getValuesList() and mutate it.
    private final List<String> valuesList = new ArrayList<>();

    // Support for type/converter setters used in some tests
    private Class<?> type;
    private Converter<?, ?> converter;

    public Option(String opt) {
        this.opt = opt;
    }

    public Option(String opt, String longOpt) {
        this.opt = opt;
        this.longOpt = longOpt;
    }

    // Additional constructors matching signatures used in tests.
    public Option(String opt, boolean hasArg, String description) {
        this.opt = opt;
    }

    public Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public List<String> getValuesList() {
        return valuesList;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setConverter(Converter<?, ?> converter) {
        this.converter = converter;
    }

    // Added to match the runtime expectation of CommandLine calling isDeprecated()
    public boolean isDeprecated() {
        return false;
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
