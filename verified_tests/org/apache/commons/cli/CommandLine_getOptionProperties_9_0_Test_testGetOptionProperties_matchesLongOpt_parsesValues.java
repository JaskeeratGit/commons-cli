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

class CommandLine_getOptionProperties_9_0_Test_testGetOptionProperties_matchesLongOpt_parsesValues {

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

}
