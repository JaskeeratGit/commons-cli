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
        options.add(new Option("a"));
        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('a'), "Expected hasOption('a') to be true when an Option with opt 'a' is present");
        assertFalse(cmd.hasOption('b'), "Expected hasOption('b') to be false when no Option with opt 'b' is present");
    }


}

/*
 * Minimal but more complete stub of Option class to enable compilation of the
 * wider test-suite which expects multiple constructors and accessors such as
 * getValuesList(), setType(), setConverter(), and getValues().
 *
 * Placed in the same package as CommandLine (org.apache.commons.cli) so that
 * the CommandLine implementation under test can interact with it.
 */
class Option {

    private final String opt;
    private String longOpt;
    private boolean hasArg;
    private String description;
    private final List<String> values = new ArrayList<>();
    private Class<?> type;
    private Converter<?, ?> converter; // uses production Converter if available
    private boolean deprecated;

    // Common constructors used across tests
    public Option(String opt) {
        this.opt = opt;
    }

    public Option(String opt, String description) {
        this.opt = opt;
        this.description = description;
    }

    public Option(String opt, boolean hasArg, String description) {
        this.opt = opt;
        this.hasArg = hasArg;
        this.description = description;
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

    /**
     * Return a mutable list of values associated with this Option.
     * Tests often call getValuesList().add(...) to populate values.
     */
    public List<String> getValuesList() {
        return values;
    }

    /**
     * Return values as array (convenience).
     */
    public String[] getValues() {
        return values.isEmpty() ? null : values.toArray(new String[0]);
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public void setConverter(Converter<?, ?> converter) {
        this.converter = converter;
    }

    /**
     * Indicate whether this option is deprecated.
     * CommandLine expects Option to provide isDeprecated(); default to false.
     */
    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String toString() {
        return "Option{opt='" + opt + "', longOpt='" + longOpt + "', hasArg=" + hasArg + ", desc='" + description + "', values=" + values + "}";
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
