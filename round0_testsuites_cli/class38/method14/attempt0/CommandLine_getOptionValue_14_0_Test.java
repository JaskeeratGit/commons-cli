package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

public class CommandLine_getOptionValue_14_0_Test {

    // Helper to instantiate CommandLine via its private constructor using reflection
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    public void testGetOptionValue_returnsNullWhenOptionIsNull() throws Exception {
        // empty args and options
        CommandLine cmd = createCommandLine(new LinkedList<>(), new ArrayList<>(), null);
        // passing null should return null
        assertNull(cmd.getOptionValue(null));
    }

    @Test
    public void testGetOptionValue_returnsFirstValueWhenOptionHasValues() throws Exception {
        // create an Option and add multiple values
        Option opt = new Option("a", true, "desc");
        opt.getValuesList().add("first");
        opt.getValuesList().add("second");
        List<Option> options = new ArrayList<>();
        // add the same instance so equals() check in CommandLine#getOptionValues succeeds
        options.add(opt);
        CommandLine cmd = createCommandLine(new LinkedList<>(), options, null);
        // should return the first value from the option's values
        assertEquals("first", cmd.getOptionValue(opt));
    }

    @Test
    public void testGetOptionValue_returnsNullWhenOptionPresentButNoValues() throws Exception {
        // option present but with no values should result in null from getOptionValues -> getOptionValue returns null
        Option opt = new Option("b", false, "no values");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = createCommandLine(new LinkedList<>(), options, null);
        assertNull(cmd.getOptionValue(opt));
    }
}
