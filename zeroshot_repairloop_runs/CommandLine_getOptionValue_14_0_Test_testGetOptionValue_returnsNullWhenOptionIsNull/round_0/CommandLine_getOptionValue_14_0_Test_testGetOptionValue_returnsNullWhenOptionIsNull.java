package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_14_0_Test_testGetOptionValue_returnsNullWhenOptionIsNull {

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
        // passing null should return null — cast to disambiguate overload resolution
        assertNull(cmd.getOptionValue((Option) null));
    }

}
