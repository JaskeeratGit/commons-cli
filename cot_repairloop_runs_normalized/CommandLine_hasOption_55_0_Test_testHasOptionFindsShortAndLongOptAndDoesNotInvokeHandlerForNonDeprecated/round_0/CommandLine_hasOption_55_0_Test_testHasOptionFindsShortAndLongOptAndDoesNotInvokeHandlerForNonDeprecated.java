package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_hasOption_55_0_Test_testHasOptionFindsShortAndLongOptAndDoesNotInvokeHandlerForNonDeprecated {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
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


}

/*
 Minimal Option and Converter classes to support testing. Placed in the same package as CommandLine
 so it matches the expected type used by the CommandLine class under test.

 This implementation provides a superset of commonly-used constructors and methods the tests expect.
*/
class Option {

    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;
    private boolean deprecated;
    private final List<String> values = new ArrayList<>();
    private Class<?> type;
    private Converter<?, ? extends RuntimeException> converter;

    // Common constructors used by tests:
    public Option(final String opt) {
        this(opt, null, false, null);
    }

    public Option(final String opt, final String description) {
        this(opt, null, false, description);
    }

    public Option(final String opt, final boolean hasArg) {
        this(opt, null, hasArg, null);
    }

    public Option(final String opt, final boolean hasArg, final String description) {
        this(opt, null, hasArg, description);
    }

    public Option(final String opt, final String longOpt) {
        this(opt, longOpt, false, null);
    }

    /**
     * Three-arg constructor frequently used in tests. Semantics: (opt, longOpt, hasArg)
     * Deprecated flag remains false by default.
     */
    public Option(final String opt, final String longOpt, final boolean hasArg) {
        this(opt, longOpt, hasArg, null);
    }

    /**
     * Four-arg constructor used in many tests: (opt, longOpt, hasArg, description)
     */
    public Option(final String opt, final String longOpt, final boolean hasArg, final String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
        this.deprecated = false;
    }

    // Accessors
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

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(final boolean d) {
        this.deprecated = d;
    }

    // Values handling used by tests
    public List<String> getValuesList() {
        return values;
    }

    public String[] getValues() {
        return values.isEmpty() ? null : values.toArray(new String[0]);
    }

    public void addValue(final String v) {
        values.add(v);
    }

    public void setValue(final String v) {
        values.clear();
        values.add(v);
    }

    // Type/conversion helpers used by some tests
    public void setType(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public <T, X extends RuntimeException> void setConverter(final Converter<T, X> conv) {
        // unchecked assignment but sufficient for tests
        @SuppressWarnings("unchecked")
        Converter<?, ? extends RuntimeException> c = (Converter<?, ? extends RuntimeException>) conv;
        this.converter = c;
    }

    @SuppressWarnings("unchecked")
    public <T, X extends RuntimeException> Converter<T, X> getConverter() {
        return (Converter<T, X>) converter;
    }

    @Override
    public String toString() {
        return "Option[opt=" + opt + ", longOpt=" + longOpt + ", hasArg=" + hasArg + ", desc=" + description + "]";
    }
}

/**
 * Minimal Converter interface to match usages in tests.
 */
interface Converter<T, X extends RuntimeException> {
    T convert(String value) throws X;
}
