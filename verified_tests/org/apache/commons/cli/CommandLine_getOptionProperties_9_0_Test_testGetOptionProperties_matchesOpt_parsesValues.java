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

class CommandLine_getOptionProperties_9_0_Test_testGetOptionProperties_matchesOpt_parsesValues {

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


}
