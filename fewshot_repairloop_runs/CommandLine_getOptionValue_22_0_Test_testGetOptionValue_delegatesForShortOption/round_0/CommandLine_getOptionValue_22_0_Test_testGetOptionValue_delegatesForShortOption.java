package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

/**
 * Unit tests for CommandLine#getOptionValue(String, Supplier)
 */
public class CommandLine_getOptionValue_22_0_Test_testGetOptionValue_delegatesForShortOption {

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
    public void testGetOptionValue_delegatesForShortOption() throws Exception {
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        Option optA = new Option("a", "alpha");
        options.add(optA);
        CommandLine cli = newCommandLine(args, options, null);
        Supplier<String> def = () -> "DEFAULT";
        // Call the public focal method
        String viaString = cli.getOptionValue("-a", def);
        // Call resolveOption + getOptionValue(Option,Supplier) directly via reflection
        Option resolved = invokeResolveOption(cli, "-a");
        assertNotNull(resolved, "resolveOption should find the short option 'a'");
        String viaOption = invokeGetOptionValueWithOption(cli, resolved, def);
        assertEquals(viaOption, viaString, "getOptionValue(String, Supplier) should delegate to getOptionValue(Option, Supplier)");
    }


}

/*
 * Minimal supporting Option and Util classes in the same package to allow testing of resolveOption.
 * These are intentionally simple and provide the required methods used by CommandLine.resolveOption.
 */
class Option {

    private final String opt;
    private final String longOpt;
    private final String description;
    private final boolean hasArg;
    private final List<String> values = new ArrayList<>();

    // Two-arg constructor (opt, description) as in commons-cli
    public Option(String opt, String description) {
        this(opt, null, false, description);
    }

    // Three-arg constructor (opt, hasArg, description)
    public Option(String opt, boolean hasArg, String description) {
        this(opt, null, hasArg, description);
    }

    // Four-arg constructor (opt, longOpt, hasArg, description)
    public Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.description = description;
        this.hasArg = hasArg;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasArg() {
        return hasArg;
    }

    /**
     * Return the internal values list (may be empty).
     */
    public List<String> getValuesList() {
        return values;
    }

    // helper to add a value (might be used at runtime by other tests)
    public void addValueForProcessing(String val) {
        values.add(val);
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
