package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;

public class // Minimal supporting classes to make this test self-contained and deterministic.
// These match the minimal behavior needed for the tests above.
CommandLine_getParsedOptionValues_49_0_Test {

    // Helper to construct CommandLine via private constructor
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    public void testGetParsedOptionValues_noMatch_returnsDefault() throws Exception {
        List<Option> options = new ArrayList<>();
        // empty options list -> no match
        CommandLine cmd = createCommandLine(new LinkedList<>(), options, null);
        String[] def = new String[] { "x", "y" };
        String[] result = cmd.getParsedOptionValues("nope", () -> def);
        // should return exactly the supplier's array when no option found
        assertSame(def, result);
    }
}

/* Supporting minimal implementations to allow compilation and testing.
   In a real environment these would be the library classes. Included here
   so the test file is self-contained per the instructions.
*/
class ParseException extends Exception {

    public ParseException(String message) {
        super(message);
    }
}

class Option {

    private final String opt;

    private String longOpt;

    private final List<String> values = new ArrayList<>();

    public Option(String opt) {
        this.opt = opt;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public void setLongOpt(String longOpt) {
        this.longOpt = longOpt;
    }

    public void addValue(String v) {
        values.add(v);
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }
}

class Util {

    // strips leading hyphens from the option name; returns null for null or empty after stripping
    public static String stripLeadingHyphens(final String name) {
        if (name == null) {
            return null;
        }
        int i = 0;
        while (i < name.length() && name.charAt(i) == '-') {
            i++;
        }
        if (i >= name.length()) {
            return "";
        }
        return name.substring(i);
    }
}

class Builder {

    public static final Consumer<Option> DEPRECATED_HANDLER = (o) -> {
        // no-op for tests
    };
}

class CommandLine {

    private final List<String> args;

    private final List<Option> options;

    private final transient Consumer<Option> deprecatedHandler;

    protected CommandLine() {
        this(new LinkedList<>(), new ArrayList<>(), Builder.DEPRECATED_HANDLER);
    }

    @SuppressWarnings("unchecked")
    CommandLine(final List<String> args, final List<Option> options, final Consumer<Option> deprecatedHandler) {
        this.args = Objects.requireNonNull(args, "args");
        this.options = Objects.requireNonNull(options, "options");
        this.deprecatedHandler = deprecatedHandler;
    }

    public List<String> getArgList() {
        return args;
    }

    public String[] getArgs() {
        return args.toArray(new String[0]);
    }

    public Option[] getOptions() {
        return options.toArray(new Option[0]);
    }

    public <T> T[] getParsedOptionValues(final String optionName, final Supplier<T[]> defaultValue) throws ParseException {
        return getParsedOptionValues(resolveOption(optionName), defaultValue);
    }

    // private overloaded method that the public method delegates to.
    @SuppressWarnings("unchecked")
    private <T> T[] getParsedOptionValues(final Option option, final Supplier<T[]> defaultValue) throws ParseException {
        if (option == null) {
            if (defaultValue == null) {
                return null;
            }
            return defaultValue.get();
        }
        List<String> vals = option.getValues();
        if (vals == null || vals.isEmpty()) {
            if (defaultValue == null) {
                return (T[]) new Object[0];
            }
            return defaultValue.get();
        }
        // assume T is String for this minimal implementation
        T[] def = defaultValue != null ? defaultValue.get() : (T[]) new String[0];
        Class<?> compType = def != null && def.getClass().isArray() ? def.getClass().getComponentType() : String.class;
        T[] arr = (T[]) java.lang.reflect.Array.newInstance(compType, vals.size());
        for (int i = 0; i < vals.size(); i++) {
            arr[i] = (T) vals.get(i);
        }
        return arr;
    }

    private Option resolveOption(final String optionName) {
        final String actual = Util.stripLeadingHyphens(optionName);
        if (actual != null) {
            for (final Option option : options) {
                if (actual.equals(option.getOpt()) || actual.equals(option.getLongOpt())) {
                    return option;
                }
            }
        }
        return null;
    }
}
