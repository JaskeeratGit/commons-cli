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

public class CommandLine_getOptionProperties_8_0_Test_testEmptyWhenOptionNotPresent {

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



}
