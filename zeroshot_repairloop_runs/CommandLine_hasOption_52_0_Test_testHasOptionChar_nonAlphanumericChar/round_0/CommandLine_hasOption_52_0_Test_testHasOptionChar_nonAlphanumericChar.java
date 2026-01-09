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
        assertTrue(cmd.hasOption('-'), "Expected hasOption('-') to be true when Option with opt '-' is present");
        assertFalse(cmd.hasOption('_'), "Expected hasOption('_') to be false when it's not present");
    }
}

/*
 * Minimal, but feature-complete-enough, stub of Option class to enable testing of CommandLine behavior.
 * Placed in the same package as CommandLine (org.apache.commons.cli) so that
 * the CommandLine implementation under test can interact with it.
 *
 * This stub provides multiple constructors and accessors used across tests, including:
 * - constructors matching common Option signatures used in tests
 * - getOpt(), getLongOpt()
 * - getValuesList()
 * - setType(), setConverter()
 *
 * Note: This is a simplified stand-in for the real Option class sufficient for unit tests.
 */
class Option {

    private final String opt;
    private String longOpt;
    private boolean hasArg;
    private String description;
    private Class<?> type;
    private Converter<?, ? extends RuntimeException> converter;
    private final List<String> values = new ArrayList<>();

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

    public boolean hasArg() {
        return hasArg;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getValuesList() {
        return values;
    }

    public void addValue(String v) {
        values.add(v);
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public <T, E extends RuntimeException> void setConverter(Converter<T, E> converter) {
        // store as raw; used only for tests that set and expect converter to be present
        @SuppressWarnings("unchecked")
        Converter<?, ? extends RuntimeException> raw = (Converter<?, ? extends RuntimeException>) converter;
        this.converter = raw;
    }

    public Converter<?, ? extends RuntimeException> getConverter() {
        return converter;
    }

    @Override
    public String toString() {
        return "Option{opt='" + opt + "', longOpt='" + longOpt + "', hasArg=" + hasArg + ", description='" + description + "'}";
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

/*
 * Minimal Converter interface used by tests. Matches the generic shape used by tests:
 * Converter<T, E extends RuntimeException>
 */
interface Converter<T, E extends RuntimeException> {
    T convert(String value) throws E;
}
