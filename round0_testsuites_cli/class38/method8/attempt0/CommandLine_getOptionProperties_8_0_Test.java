package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import java.util.function.Supplier;

public class CommandLine_getOptionProperties_8_0_Test {

    @SuppressWarnings("unchecked")
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    public void testEmptyWhenOptionNotPresent() throws Exception {
        // prepare an options list with one option
        Option present = new Option("a", "desc");
        List<Option> options = new ArrayList<>();
        options.add(present);
        CommandLine cmd = createCommandLine(new ArrayList<>(), options, null);
        // create an option that is not present in the command line
        Option notPresent = new Option("b", "desc");
        Properties props = cmd.getOptionProperties(notPresent);
        assertNotNull(props);
        assertTrue(props.isEmpty(), "Properties should be empty when option is not present");
    }

    @Test
    public void testPropertiesExtractedFromValues() throws Exception {
        Option opt = new Option("opt", "desc");
        // values: proper key=value, empty value, and a token without '=' which should be ignored
        opt.getValuesList().add("k1=v1");
        opt.getValuesList().add("k2=");
        opt.getValuesList().add("noequals");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = createCommandLine(new ArrayList<>(), options, null);
        Properties props = cmd.getOptionProperties(opt);
        assertNotNull(props);
        assertEquals("v1", props.getProperty("k1"), "k1 should map to v1");
        assertEquals("", props.getProperty("k2"), "k2 should map to empty string");
        assertNull(props.getProperty("noequals"), "tokens without '=' should not become properties");
    }

    @Test
    public void testEqualDifferentInstanceMatches() throws Exception {
        // option stored in the command line
        Option stored = new Option("same", "desc");
        stored.getValuesList().add("alpha=1");
        List<Option> options = new ArrayList<>();
        options.add(stored);
        CommandLine cmd = createCommandLine(new ArrayList<>(), options, null);
        // different Option instance but with same option id -> equals() should be true
        Option query = new Option("same", "anotherDesc");
        Properties props = cmd.getOptionProperties(query);
        assertNotNull(props);
        assertEquals("1", props.getProperty("alpha"), "Matching by equals should retrieve properties from stored option");
    }

    @Test
    public void testMultipleMatchingOptionsOverrideBehavior() throws Exception {
        // two options in the list that are equal according to Option.equals (same option string)
        Option first = new Option("dup", "desc");
        first.getValuesList().add("k=value1");
        Option second = new Option("dup", "desc");
        second.getValuesList().add("k=value2");
        List<Option> options = new ArrayList<>();
        options.add(first);
        options.add(second);
        CommandLine cmd = createCommandLine(new ArrayList<>(), options, null);
        Option query = new Option("dup", "whatever");
        Properties props = cmd.getOptionProperties(query);
        // second should override first because it is processed later in the loop
        assertNotNull(props);
        assertEquals("value2", props.getProperty("k"), "Later matching options should override earlier ones");
    }
}
