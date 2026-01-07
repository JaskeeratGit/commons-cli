package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }




    @Test
    public void getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt() throws Exception {
        Option opt = new Option("b", true, "has values");
        // add values for processing (commons-cli Option exposes addValueForProcessing)
        opt.addValueForProcessing("first");
        opt.addValueForProcessing("second");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        assertEquals("first", cmd.getOptionValue("b"));
        assertEquals("first", cmd.getOptionValue("-b"));
    }

}
