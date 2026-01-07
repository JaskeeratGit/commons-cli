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

class CommandLine_getOptionProperties_9_0_Test_testGetOptionProperties_multipleOptions_onlyMatchingProcessed {

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
