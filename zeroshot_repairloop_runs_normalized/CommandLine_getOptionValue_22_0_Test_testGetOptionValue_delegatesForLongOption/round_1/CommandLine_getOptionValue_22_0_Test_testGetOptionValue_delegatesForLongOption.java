package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

/**
 * Unit tests for CommandLine#getOptionValue(String, Supplier)
 */
public class CommandLine_getOptionValue_22_0_Test_testGetOptionValue_delegatesForLongOption {

    // Helper to construct a CommandLine instance via the non-public constructor
    private CommandLine newCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    // Helper to invoke private resolveOption(String) via reflection
    private Option invokeResolveOption(CommandLine cli, String optionName) throws Exception {
        Method resolve = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        resolve.setAccessible(true);
        return (Option) resolve.invoke(cli, optionName);
    }

    // Helper to invoke getOptionValue(Option, Supplier) via reflection
    private String invokeGetOptionValueWithOption(CommandLine cli, Option opt, Supplier<String> supplier) throws Exception {
        Method gv = CommandLine.class.getDeclaredMethod("getOptionValue", Option.class, Supplier.class);
        gv.setAccessible(true);
        return (String) gv.invoke(cli, opt, supplier);
    }


    @Test
    public void testGetOptionValue_delegatesForLongOption() throws Exception {
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        Option optB = new Option("b", "beta");
        options.add(optB);
        CommandLine cli = newCommandLine(args, options, null);
        Supplier<String> def = () -> "DEFAULT-B";
        // Call the public focal method with leading double hyphen
        String viaString = cli.getOptionValue("--beta", def);
        // Call resolveOption + getOptionValue(Option,Supplier) directly via reflection
        Option resolved = invokeResolveOption(cli, "--beta");
        assertNotNull(resolved, "resolveOption should find the long option 'beta'");
        String viaOption = invokeGetOptionValueWithOption(cli, resolved, def);
        assertEquals(viaOption, viaString, "getOptionValue(String, Supplier) should delegate to getOptionValue(Option, Supplier)");
    }

}

/*
 * Minimal supporting Option and Util classes in the same package to allow testing of resolveOption.
 * These are intentionally simple and provide the required methods used by CommandLine.resolveOption.
 * Expanded to include additional constructors and getValuesList to match usages across the test-suite.
 */
class Option {

    private final String opt;

    private final String longOpt;

    private final boolean hasArg;

    private final String description;

    private final List<String> values = new ArrayList<>();

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

    /**
     * Returns the list of values associated with this option.
     */
    public List<String> getValuesList() {
        return values;
    }

    /**
     * Convenience to return values as array.
     */
    public String[] getValues() {
        return values.toArray(new String[0]);
    }

    /**
     * Add a value (simulate parsing behavior).
     */
    public void addValueForProcessing(final String value) {
        values.add(value);
    }

    public void addValue(final String value) {
        values.add(value);
    }
}

class Util {

    /**
     * Strips leading hyphens from the option name.
     * Returns null if input is null or the result is empty.
     */
    public static String stripLeadingHyphens(final String option) {
        if (option == null) {
            return null;
        }
        int i = 0;
        while (i < option.length() && option.charAt(i) == '-') {
            i++;
        }
        String actual = option.substring(i);
        return actual.isEmpty() ? null : actual;
    }
}
