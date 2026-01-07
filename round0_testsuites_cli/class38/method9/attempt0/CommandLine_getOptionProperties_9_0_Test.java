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

class CommandLine_getOptionProperties_9_0_Test {

    // Helper to create CommandLine instance via the private constructor CommandLine(List<String>, List<Option>, Consumer<Option>)
    private CommandLine createCommandLineWithOptions(List<Option> options) throws Exception {
        Class<CommandLine> clz = CommandLine.class;
        Constructor<CommandLine> ctor = clz.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // args list can be empty
        return ctor.newInstance(new ArrayList<String>(), options, (Consumer<Option>) o -> {
        });
    }

    @Test
    void testGetOptionProperties_noMatch_returnsEmpty() throws Exception {
        List<Option> options = new ArrayList<>();
        CommandLine cmd = createCommandLineWithOptions(options);
        Properties props = cmd.getOptionProperties("nonexistent");
        assertNotNull(props);
        assertTrue(props.isEmpty(), "Properties should be empty when no matching option");
    }

    @Test
    void testGetOptionProperties_matchesOpt_parsesValues() throws Exception {
        // create option with opt "o" and longOpt "long"
        Option opt = new Option("o", "long", true, "desc");
        // add values expected to be parsed into properties by processPropertiesFromValues
        // typical commons-cli behavior: "k1=v1" -> key k1 value v1, and bare "k2" -> value "true"
        opt.getValuesList().add("k1=v1");
        opt.getValuesList().add("k2");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = createCommandLineWithOptions(options);
        Properties props = cmd.getOptionProperties("o");
        assertNotNull(props);
        // Expect both properties present
        assertEquals("v1", props.getProperty("k1"));
        // Bare key often interpreted as "true"
        assertEquals("true", props.getProperty("k2"));
        assertEquals(2, props.size());
    }

    @Test
    void testGetOptionProperties_matchesLongOpt_parsesValues() throws Exception {
        Option opt = new Option("a", "longName", true, "desc");
        opt.getValuesList().add("alpha=one");
        opt.getValuesList().add("beta");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = createCommandLineWithOptions(options);
        Properties props = cmd.getOptionProperties("longName");
        assertNotNull(props);
        assertEquals("one", props.getProperty("alpha"));
        assertEquals("true", props.getProperty("beta"));
        assertEquals(2, props.size());
    }

    @Test
    void testGetOptionProperties_multipleOptions_onlyMatchingProcessed() throws Exception {
        Option opt1 = new Option("x", "longX", true, "desc");
        opt1.getValuesList().add("k=v");
        Option opt2 = new Option("y", "longY", true, "desc");
        opt2.getValuesList().add("should=not");
        opt2.getValuesList().add("appear");
        List<Option> options = new ArrayList<>();
        options.add(opt1);
        options.add(opt2);
        CommandLine cmd = createCommandLineWithOptions(options);
        Properties propsForX = cmd.getOptionProperties("x");
        assertEquals("v", propsForX.getProperty("k"));
        assertEquals(1, propsForX.size());
        Properties propsForLongY = cmd.getOptionProperties("longY");
        assertEquals("not", propsForLongY.getProperty("should"));
        assertEquals("true", propsForLongY.getProperty("appear"));
        assertEquals(2, propsForLongY.size());
    }
}
