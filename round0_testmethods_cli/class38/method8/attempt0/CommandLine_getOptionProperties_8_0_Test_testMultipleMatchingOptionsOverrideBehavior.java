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

public class CommandLine_getOptionProperties_8_0_Test_testMultipleMatchingOptionsOverrideBehavior {

    @SuppressWarnings("unchecked")
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
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
