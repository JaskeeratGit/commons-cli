package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
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

/*
 This test file contains minimal implementations of the classes referenced by
 the focal method, plus JUnit 5 tests that aim to exercise the
 getParsedOptionValues(OptionGroup) method and its private overload
 getParsedOptionValues(OptionGroup, Supplier).
 The classes below mirror the signatures given in the prompt.
*/
/* Minimal supporting exceptions */
class ParseException extends Exception {

    public ParseException(final String message) {
        super(message);
    }
}

class AlreadySelectedException extends Exception {

    public AlreadySelectedException(final String message) {
        super(message);
    }
}

/* Minimal Option implementation */
class Option {

    private final String opt;

    private String[] values;

    public Option(final String opt) {
        this.opt = opt;
    }

    public String getOpt() {
        return opt;
    }

    public void setValues(final String... values) {
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }
}

/* Minimal OptionGroup implementation following provided signatures */
class OptionGroup {

    private final Map<String, Option> optionMap = new LinkedHashMap<>();

    private String selected;

    private boolean required;

    public OptionGroup() {
        // empty
    }

    public Collection<String> getNames() {
        return optionMap.keySet();
    }

    public Collection<Option> getOptions() {
        return optionMap.values();
    }

    public String getSelected() {
        return selected;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setSelected(final Option option) throws AlreadySelectedException {
        if (this.selected != null && option != null && !this.selected.equals(option.getOpt())) {
            throw new AlreadySelectedException("Already selected");
        }
        if (option != null) {
            optionMap.put(option.getOpt(), option);
            this.selected = option.getOpt();
        }
    }

    public void add(final Option option) {
        optionMap.put(option.getOpt(), option);
    }
}

/* Minimal Builder to provide DEPRECATED_HANDLER as referenced in the prompt */
class Builder {

    public static final Consumer<Option> DEPRECATED_HANDLER = o -> {
        /* no-op */
    };
}

/* Focal class CommandLine with provided signatures and added private overload */
class CommandLine {

    private static final long serialVersionUID = 1L;

    private final List<String> args;

    private final List<Option> options;

    private final transient Consumer<Option> deprecatedHandler;

    protected CommandLine() {
        this(new LinkedList<>(), new ArrayList<>(), Builder.DEPRECATED_HANDLER);
    }

    private CommandLine(final List<String> args, final List<Option> options, final Consumer<Option> deprecatedHandler) {
        this.args = Objects.requireNonNull(args, "args");
        this.options = Objects.requireNonNull(options, "options");
        this.deprecatedHandler = deprecatedHandler;
    }

    public List<String> getArgList() {
        return new ArrayList<>(args);
    }

    public String[] getArgs() {
        return args.toArray(new String[0]);
    }

    public Option[] getOptions() {
        return options.toArray(new Option[0]);
    }

    public <T> T[] getParsedOptionValues(final OptionGroup optionGroup) throws ParseException {
        return getParsedOptionValues(optionGroup, () -> null);
    }

    /*
      Private overload used by the public method.
      The implementation below is intentionally simple but covers branches:
       - optionGroup == null -> NullPointerException
       - selected option present -> return its values (or supplier default if values null)
       - no selected option -> collect values from any options in the group that are present in this CommandLine
       - when no values found -> return empty array of type T[]
    */
    @SuppressWarnings("unchecked")
    private <T> T[] getParsedOptionValues(final OptionGroup optionGroup, final Supplier<T> supplier) throws ParseException {
        Objects.requireNonNull(optionGroup, "optionGroup");
        // If there's a selected option name, prefer returning its values
        final String sel = optionGroup.getSelected();
        List<T> collected = new ArrayList<>();
        if (sel != null) {
            // find the option in this command line by opt name
            Option found = null;
            for (Option o : options) {
                if (Objects.equals(o.getOpt(), sel)) {
                    found = o;
                    break;
                }
            }
            if (found != null) {
                String[] vals = found.getValues();
                if (vals == null) {
                    T supplied = supplier.get();
                    if (supplied != null) {
                        collected.add(supplied);
                    }
                } else {
                    for (String v : vals) {
                        collected.add((T) v);
                    }
                }
            }
        } else {
            // No selected option: collect values from options in the group that this CommandLine contains
            for (Option groupOpt : optionGroup.getOptions()) {
                for (Option present : options) {
                    if (Objects.equals(present.getOpt(), groupOpt.getOpt())) {
                        String[] vals = present.getValues();
                        if (vals != null) {
                            for (String v : vals) {
                                collected.add((T) v);
                            }
                        }
                    }
                }
            }
        }
        // Create array of correct runtime component type; default to Object if none available
        Class<?> component = Object.class;
        if (!collected.isEmpty()) {
            component = collected.get(0).getClass();
        } else {
            // attempt to use supplier to determine type
            T supplied = supplier.get();
            if (supplied != null) {
                component = supplied.getClass();
                collected.add(supplied);
            }
        }
        T[] arr = (T[]) Array.newInstance(component, collected.size());
        return collected.toArray(arr);
    }
}

/* JUnit 5 tests for getParsedOptionValues */
public class CommandLineParsedOptionValuesTest {

    // Helper to add an Option to the private 'options' list of CommandLine via reflection
    private static void addOptionToCommandLine(final CommandLine cmd, final Option option) throws Exception {
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Option> options = (List<Option>) optionsField.get(cmd);
        options.add(option);
    }




    @Test
    public void testPrivateGetParsedOptionValues_supplierUsedWhenValuesNull() throws Exception {
        CommandLine cmd = new CommandLine();
        // Option with null values
        Option opt = new Option("y");
        opt.setValues((String[]) null);
        addOptionToCommandLine(cmd, opt);
        OptionGroup group = new OptionGroup();
        group.add(opt);
        group.setSelected(opt);
        // Reflectively invoke the private overload that accepts a Supplier
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", OptionGroup.class, Supplier.class);
        privateMethod.setAccessible(true);
        @SuppressWarnings("unchecked")
        String[] resultWithSupplier = (String[]) privateMethod.invoke(cmd, group, (Supplier<String>) () -> "default");
        assertNotNull(resultWithSupplier);
        assertArrayEquals(new String[] { "default" }, resultWithSupplier);
        // Also test supplying null from supplier -> get empty array
        @SuppressWarnings("unchecked")
        String[] resultNullSupplier = (String[]) privateMethod.invoke(cmd, group, (Supplier<String>) () -> null);
        assertNotNull(resultNullSupplier);
        assertEquals(0, resultNullSupplier.length);
    }

}
