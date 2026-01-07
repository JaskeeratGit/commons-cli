package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class CommandLine_getOptionValues_24_0_Test {

    // Helper to create a CommandLine instance by invoking the private constructor via reflection
    private CommandLine createCommandLine(final List<String> args, final List<Option> options, final Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    // Helper to set the private 'deprecated' field of an Option instance to a non-null instance
    @Test
    void testNullOptionReturnsNull() throws Exception {
        CommandLine cl = createCommandLine(new LinkedList<>(), new ArrayList<>(), null);
        // Cast null to Option to disambiguate overloaded getOptionValues methods (e.g., String/OptionGroup overloads)
        assertNull(cl.getOptionValues((Option) null), "getOptionValues should return null for null argument");
    }

    @Test
    void testNoMatchingOptionReturnsNull() throws Exception {
        List<Option> options = new ArrayList<>();
        Option o1 = new Option("a", false, "opt a");
        options.add(o1);
        CommandLine cl = createCommandLine(new LinkedList<>(), options, null);
        // Create an option that is different (different short opt)
        Option query = new Option("b", false, "opt b");
        assertNull(cl.getOptionValues(query), "No matching processed option -> should return null");
    }

    @Test
    void testMatchingOptionWithEmptyValuesReturnsNull() throws Exception {
        List<Option> options = new ArrayList<>();
        // values list initially empty
        Option processed = new Option("x", false, "opt x");
        options.add(processed);
        CommandLine cl = createCommandLine(new LinkedList<>(), options, null);
        // equals by opt string
        Option query = new Option("x", false, "another opt x");
        assertNull(cl.getOptionValues(query), "Matching option but no values -> should return null");
    }

    @Test
    void testReturnsValuesForMatchingOption() throws Exception {
        List<Option> options = new ArrayList<>();
        Option processed = new Option("v", false, "opt v");
        processed.getValuesList().add("one");
        processed.getValuesList().add("two");
        options.add(processed);
        CommandLine cl = createCommandLine(new LinkedList<>(), options, null);
        // Use a different Option instance but equal by option string
        Option query = new Option("v", false, "other");
        String[] values = cl.getOptionValues(query);
        assertNotNull(values, "Should return array of values");
        assertArrayEquals(new String[] { "one", "two" }, values, "Returned values should match those in processed option");
    }

    @Test
    void testAggregatesValuesFromMultipleProcessedOptions() throws Exception {
        List<Option> options = new ArrayList<>();
        Option p1 = new Option("m", false, "m1");
        p1.getValuesList().add("a");
        p1.getValuesList().add("b");
        Option p2 = new Option("m", false, "m2");
        p2.getValuesList().add("c");
        options.add(p1);
        options.add(p2);
        CommandLine cl = createCommandLine(new LinkedList<>(), options, null);
        // Query option equal to both p1 and p2
        Option query = new Option("m", false, "q");
        String[] values = cl.getOptionValues(query);
        assertNotNull(values, "Should return aggregated values");
        assertArrayEquals(new String[] { "a", "b", "c" }, values, "Values should be aggregated from all matching processed options in order");
    }
}
