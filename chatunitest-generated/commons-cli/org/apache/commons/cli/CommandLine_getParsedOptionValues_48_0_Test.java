package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
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
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Unit tests for CommandLine#getParsedOptionValues(String)
 */
public class CommandLine_getParsedOptionValues_48_0_Test {

    @Test
    public void testGetParsedOptionValues_withNullName_returnsNull() throws Exception {
        // protected no-arg constructor is accessible in same package
        CommandLine cmd = new CommandLine();
        // calling with null should not throw and expected behaviour (delegates to resolveOption -> null)
        Object[] values = cmd.getParsedOptionValues((String) null);
        assertNull(values, "Expected null when option name is null and no option resolved");
    }

    @Test
    public void testGetParsedOptionValues_optionNotFound_returnsNull() throws Exception {
        CommandLine cmd = new CommandLine();
        // option name that cannot be resolved
        Object[] values = cmd.getParsedOptionValues("nonexistent");
        assertNull(values, "Expected null when option name does not match any option");
    }

    @Test
    public void testGetParsedOptionValues_resolvesShortAndLongOption_andUsesPrivateResolveOption() throws Exception {
        // Create two Option instances (short and long)
        // Using commonly available Option constructor: Option(String opt, String longOpt, boolean hasArg, String description)
        Option optShort = new Option("a", "alpha", true, "alpha option");
        Option optLong = new Option("b", "beta", false, "beta option");
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        options.add(optShort);
        options.add(optLong);
        // Use private constructor CommandLine(List, List, Consumer<Option>) via reflection
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, java.util.function.Consumer.class);
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        // Use reflection to invoke private resolveOption to assert it finds the correct Option
        Method resolveMethod = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        resolveMethod.setAccessible(true);
        // Check short form without hyphen
        Object resolved1 = resolveMethod.invoke(cmd, "a");
        assertSame(optShort, resolved1, "resolveOption should match by short opt without hyphen");
        // Check short form with single hyphen
        Object resolved2 = resolveMethod.invoke(cmd, "-a");
        assertSame(optShort, resolved2, "resolveOption should match by short opt with single hyphen");
        // Check long form with double hyphen
        Object resolved3 = resolveMethod.invoke(cmd, "--alpha");
        assertSame(optShort, resolved3, "resolveOption should match by long opt with double hyphen");
        // Check other option by its short and long names
        Object resolved4 = resolveMethod.invoke(cmd, "b");
        assertSame(optLong, resolved4, "resolveOption should match second option by short name");
        Object resolved5 = resolveMethod.invoke(cmd, "--beta");
        assertSame(optLong, resolved5, "resolveOption should match second option by long name");
        // Finally call the public getParsedOptionValues(String) for various names; ensure it runs and returns null
        // (under the provided implementation it delegates and ultimately returns null for no parsed values)
        Object[] valsShort = cmd.getParsedOptionValues("a");
        assertNull(valsShort, "Expected null parsed values for option 'a' (no values provided)");
        Object[] valsShortHyphen = cmd.getParsedOptionValues("-a");
        assertNull(valsShortHyphen, "Expected null parsed values for option '-a' (no values provided)");
        Object[] valsLong = cmd.getParsedOptionValues("--alpha");
        assertNull(valsLong, "Expected null parsed values for option '--alpha' (no values provided)");
    }
}
