package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Supplier;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 Fixed unit tests for CommandLine.getParsedOptionValues(OptionGroup).
 
 Changes made:
 - Added a proper ParseException class (the original test file had an incorrectly named class).
 - Added additional tests to cover branches:
     - selected option present and has values
     - selected option present but values null -> private overload using Supplier supplies default
     - no selected option -> collect from CommandLine's options that are in the OptionGroup
     - null OptionGroup -> expect NullPointerException
     - no values found and supplier returns null -> empty Object[] result (verified via Object[] to avoid ClassCastException)
 - Carefully invoke the private overload via reflection when exercising supplier behavior.
 - Use reflection helper to add Option instances to the private 'options' list in CommandLine.
*/

/* Minimal supporting exceptions and classes to mirror the environment used by the tested method */

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

/* Minimal OptionGroup implementation */
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

/* Focal class CommandLine with provided signatures and private overload */
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

    @SuppressWarnings("unchecked")
    private <T> T[] getParsedOptionValues(final OptionGroup optionGroup, final Supplier<T> supplier) throws ParseException {
        Objects.requireNonNull(optionGroup, "optionGroup");
        final String sel = optionGroup.getSelected();
        List<T> collected = new ArrayList<>();
        if (sel != null) {
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
        Class<?> component = Object.class;
        if (!collected.isEmpty()) {
            component = collected.get(0).getClass();
        } else {
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
    public void testGetParsedOptionValues_selectedPresent_returnsValues() throws Exception {
        CommandLine cmd = new CommandLine();
        // Create an option and add to command line
        Option opt = new Option("x");
        opt.setValues("v1", "v2");
        addOptionToCommandLine(cmd, opt);
        OptionGroup group = new OptionGroup();
        group.add(opt);
        group.setSelected(opt);
        String[] result = cmd.getParsedOptionValues(group);
        assertNotNull(result);
        assertArrayEquals(new String[] { "v1", "v2" }, result);
    }

    @Test
    public void testGetParsedOptionValues_selectedPresent_nullValues_usesSupplier_privateOverload() throws Exception {
        CommandLine cmd = new CommandLine();
        // Option present in command line but has null values
        Option opt = new Option("y");
        // don't set values => null
        addOptionToCommandLine(cmd, opt);

        OptionGroup group = new OptionGroup();
        group.add(opt);
        group.setSelected(opt);

        // Invoke private overload via reflection with a supplier providing a default value
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", OptionGroup.class, Supplier.class);
        privateMethod.setAccessible(true);
        @SuppressWarnings("unchecked")
        String[] result = (String[]) privateMethod.invoke(cmd, group, (Supplier<String>) () -> "supplied");
        assertNotNull(result);
        assertArrayEquals(new String[] { "supplied" }, result);
    }

    @Test
    public void testGetParsedOptionValues_noSelected_collectsFromGroup() throws Exception {
        CommandLine cmd = new CommandLine();
        // Two options in the group; both present in command line with values
        Option a = new Option("a");
        a.setValues("aa1");
        Option b = new Option("b");
        b.setValues("bb1", "bb2");
        addOptionToCommandLine(cmd, a);
        addOptionToCommandLine(cmd, b);

        OptionGroup group = new OptionGroup();
        group.add(a);
        group.add(b);
        // no selected set

        // Assign to Object[] to avoid potential ClassCastException if no values were found.
        Object[] result = cmd.getParsedOptionValues(group);
        assertNotNull(result);
        // result may be String[] at runtime; compare as arrays of Object
        Object[] expected = new Object[] { "aa1", "bb1", "bb2" };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetParsedOptionValues_nullGroup_throwsNPE() throws Exception {
        CommandLine cmd = new CommandLine();
        assertThrows(NullPointerException.class, () -> {
            try {
                cmd.getParsedOptionValues(null);
            } catch (ParseException e) {
                // method signature includes ParseException; if it were thrown, fail test
                fail("Unexpected ParseException: " + e.getMessage());
            }
        });
    }

    @Test
    public void testGetParsedOptionValues_noValuesAndSupplierNull_returnsEmptyObjectArray() throws Exception {
        CommandLine cmd = new CommandLine();
        // OptionGroup contains an option that is not present in command line -> no collected values
        Option opt = new Option("z");
        OptionGroup group = new OptionGroup();
        group.add(opt);
        // call private overload providing a supplier that returns null
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", OptionGroup.class, Supplier.class);
        privateMethod.setAccessible(true);
        Object[] result = (Object[]) privateMethod.invoke(cmd, group, (Supplier<Object>) () -> null);
        assertNotNull(result);
        assertEquals(0, result.length);
    }
}
