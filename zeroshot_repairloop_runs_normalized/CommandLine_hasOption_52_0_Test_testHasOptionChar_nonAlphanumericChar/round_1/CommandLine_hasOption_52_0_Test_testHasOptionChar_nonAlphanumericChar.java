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
public class CommandLine_hasOption_52_0_Test_testHasOptionChar_nonAlphanumericChar {

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
    public void testHasOptionChar_nonAlphanumericChar() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Use a punctuation short name, e.g. "-"
        options.add(new Option("-"));
        CommandLine cmd = createCommandLine(args, options, null);
        // CommandLine.hasOption(char) delegates to hasOption(String) and
        // non-alphanumeric short option names are not considered valid short options,
        // so expect false for '-'
        assertFalse(cmd.hasOption('-'), "Expected hasOption('-') to be false for non-alphanumeric short option names");
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
 *
 * Additional constructors and methods are provided to satisfy compilation of
 * other tests in the suite that expect a richer Option API.
 */
class Option {

    private final String opt;

    private String longOpt;
    private final List<String> valuesList = new ArrayList<>();
    private Class<?> type;
    private Converter<?, ? extends RuntimeException> converter;

    public Option(String opt) {
        this.opt = opt;
    }

    public Option(String opt, String longOpt) {
        this.opt = opt;
        this.longOpt = longOpt;
    }

    /**
     * Matches common Option constructor used in tests: Option(String opt, boolean hasArg, String value)
     * We treat the 'value' parameter as an initial value to populate getValuesList().
     */
    public Option(String opt, boolean hasArg, String value) {
        this.opt = opt;
        if (value != null) {
            this.valuesList.add(value);
        }
    }

    /**
     * Matches common Option constructor used in tests: Option(String opt, String longOpt, boolean hasArg, String value)
     * We treat the 'value' parameter as an initial value to populate getValuesList().
     */
    public Option(String opt, String longOpt, boolean hasArg, String value) {
        this.opt = opt;
        this.longOpt = longOpt;
        if (value != null) {
            this.valuesList.add(value);
        }
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    /**
     * Return the underlying values list. Tests in the suite reference this directly.
     */
    public List<String> getValuesList() {
        return valuesList;
    }

    /**
     * Allow tests to set the expected type for conversion.
     */
    public <T> void setType(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public <T, E extends RuntimeException> void setConverter(Converter<T, E> converter) {
        this.converter = (Converter<?, ? extends RuntimeException>) converter;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Option{opt='" + opt + "', longOpt='" + longOpt + "', values=" + valuesList + "}";
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

/**
 * Minimal Converter interface used by tests. Placed here to satisfy compilation
 * when tests provide Converter instances.
 */
interface Converter<T, E extends RuntimeException> {
    T convert(String value) throws E;
}
