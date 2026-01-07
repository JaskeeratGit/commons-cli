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

public class CommandLine_getOptionProperties_8_0_Test_testEqualDifferentInstanceMatches {

    @SuppressWarnings("unchecked")
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
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

}
