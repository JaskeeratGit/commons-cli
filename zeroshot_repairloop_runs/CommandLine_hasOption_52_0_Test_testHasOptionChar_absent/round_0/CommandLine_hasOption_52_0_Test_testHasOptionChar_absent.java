package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
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
 * Minimal stub of Option class (and Converter) to enable testing of CommandLine behavior.
 * Placed in the same package as CommandLine (org.apache.commons.cli) so that
 * the CommandLine implementation under test can interact with it.
 *
 * This stub provides common constructors and accessors used by the tests.
 */

interface Converter<T, E extends RuntimeException> {
    T convert(String value) throws E;
}

class Option {

    private final String opt;
    private String longOpt;
    private final boolean hasArg;
    private String description;
    private final List<String> valuesList = new ArrayList<>();
    private Class<?> type;
    private Converter<?, ? extends RuntimeException> converter;

    public Option(String opt) {
        this(opt, null, false, null);
    }

    public Option(String opt, String longOpt) {
        this(opt, longOpt, false, null);
    }

    public Option(String opt, boolean hasArg, String description) {
        this(opt, null, hasArg, description);
    }

    public Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
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

    public Class<?> getType() {
        return type;
    }

    public <T, E extends RuntimeException> void setConverter(Converter<T, E> conv) {
        this.converter = conv;
    }

    public Converter<?, ? extends RuntimeException> getConverter() {
        return converter;
    }

    @Override
    public String toString() {
        return "Option{opt='" + opt + "', longOpt='" + longOpt + "', hasArg=" + hasArg + ", desc='" + description + "'}";
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
