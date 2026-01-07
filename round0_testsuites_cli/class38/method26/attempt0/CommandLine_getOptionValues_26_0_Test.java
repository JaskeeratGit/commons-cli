package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.Supplier;

/**
 * Unit tests for CommandLine#getOptionValues(String)
 */
public class CommandLine_getOptionValues_26_0_Test {

    // Helper: create a CommandLine using the private constructor (List<String>, List<Option>, Consumer<Option>)
    private static CommandLine createCommandLineWithOptions(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // args can be empty list
        return ctor.newInstance(new LinkedList<String>(), options, deprecatedHandler);
    }

    // Helper: add values to an Option instance using reflection to call addValueForProcessing(String)
    private static void addValuesToOption(Option option, String... values) throws Exception {
        // Try the known method name used in commons-cli implementations
        Method addMethod = null;
        try {
            addMethod = option.getClass().getDeclaredMethod("addValueForProcessing", String.class);
        } catch (NoSuchMethodException e) {
            // fallback: try "addValue" (some versions might differ)
            try {
                addMethod = option.getClass().getDeclaredMethod("addValue", String.class);
            } catch (NoSuchMethodException ex) {
                // give up and throw original
                throw e;
            }
        }
        addMethod.setAccessible(true);
        for (String v : values) {
            addMethod.invoke(option, v);
        }
    }

    @Test
    public void testGetOptionValues_NullName_ReturnsNull() throws Exception {
        // create an empty CommandLine (no options)
        CommandLine cmd = createCommandLineWithOptions(new ArrayList<>(), null);
        String[] result = cmd.getOptionValues((String) null);
        assertNull(result, "Expected null when optionName is null");
    }

    @Test
    public void testGetOptionValues_NoMatchingOption_ReturnsNull() throws Exception {
        // create a command line with one option that doesn't match the query
        Option opt = new Option("a", "alpha", false, "desc");
        addValuesToOption(opt, "v1");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts, null);
        // no option 'b'
        String[] result = cmd.getOptionValues("b");
        assertNull(result, "Expected null when no matching option present");
    }

    @Test
    public void testGetOptionValues_MatchingShortAndLongOption_ReturnsValues() throws Exception {
        Option opt = new Option("a", "alpha", false, "desc");
        addValuesToOption(opt, "value1", "value2");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts, null);
        // direct short name
        String[] resShort = cmd.getOptionValues("a");
        assertNotNull(resShort);
        assertArrayEquals(new String[] { "value1", "value2" }, resShort);
        // with single hyphen
        String[] resShortHyphen = cmd.getOptionValues("-a");
        assertNotNull(resShortHyphen);
        assertArrayEquals(new String[] { "value1", "value2" }, resShortHyphen);
        // long opt without hyphens
        String[] resLong = cmd.getOptionValues("alpha");
        assertNotNull(resLong);
        assertArrayEquals(new String[] { "value1", "value2" }, resLong);
        // long opt with double hyphen
        String[] resLongHyphen = cmd.getOptionValues("--alpha");
        assertNotNull(resLongHyphen);
        assertArrayEquals(new String[] { "value1", "value2" }, resLongHyphen);
    }

    @Test
    public void testGetOptionValues_DeprecatedHandlerInvoked() throws Exception {
        Option opt = new Option("d", "deprecated", false, "deprecated option");
        addValuesToOption(opt, "x");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        AtomicBoolean handlerInvoked = new AtomicBoolean(false);
        Consumer<Option> handler = (o) -> handlerInvoked.set(true);
        // create command line with custom deprecated handler via private constructor
        CommandLine cmd = createCommandLineWithOptions(opts, handler);
        // mark option as deprecated via reflection (Option.isDeprecated() should reflect that)
        // We try to set the deprecated flag if available, otherwise attempt to call setDeprecated method.
        try {
            Method setDeprecated = opt.getClass().getDeclaredMethod("setDeprecated", boolean.class);
            setDeprecated.setAccessible(true);
            setDeprecated.invoke(opt, true);
        } catch (NoSuchMethodException ignored) {
            // try to access a field named "deprecated"
            try {
                java.lang.reflect.Field f = opt.getClass().getDeclaredField("deprecated");
                f.setAccessible(true);
                f.set(opt, Boolean.TRUE);
            } catch (NoSuchFieldException | IllegalAccessException ignored2) {
                // If neither is available, attempt to construct Option as deprecated via constructor
                // (some Option constructors accept a deprecated flag; but already used above).
            }
        }
        String[] result = cmd.getOptionValues("d");
        assertNotNull(result);
        assertArrayEquals(new String[] { "x" }, result);
        assertTrue(handlerInvoked.get(), "Deprecated handler should have been invoked for deprecated option");
    }

    @Test
    public void testResolveOption_PrivateMethodBehavior() throws Exception {
        // Prepare options with two distinct options
        Option o1 = new Option("a", "alpha", false, "one");
        addValuesToOption(o1, "1");
        Option o2 = new Option("b", "beta", false, "two");
        addValuesToOption(o2, "2");
        List<Option> opts = new ArrayList<>();
        opts.add(o1);
        opts.add(o2);
        CommandLine cmd = createCommandLineWithOptions(opts, null);
        // Access private resolveOption method via reflection
        Method resolve = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        resolve.setAccessible(true);
        // null yields null (after stripLeadingHyphens)
        Object r0 = resolve.invoke(cmd, (Object) null);
        assertNull(r0);
        // short name
        Object r1 = resolve.invoke(cmd, "a");
        assertSame(o1, r1);
        // hyphenated short name
        Object r1h = resolve.invoke(cmd, "-a");
        assertSame(o1, r1h);
        // long name
        Object r2 = resolve.invoke(cmd, "beta");
        assertSame(o2, r2);
        // non-existent
        Object r3 = resolve.invoke(cmd, "z");
        assertNull(r3);
    }
}
