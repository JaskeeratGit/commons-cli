package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

public class CommandLine_hasOption_55_0_Test_testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated {

    // Helper to create CommandLine using the private constructor via reflection
    private CommandLine createCommandLine(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // first argument is args list (List<String>), we pass an empty list
        return ctor.newInstance(new ArrayList<String>(), options, deprecatedHandler);
    }



    @Test
    public void testHasOptionInvokesDeprecatedHandlerWhenOptionIsDeprecated() throws Exception {
        Option dep = new Option("d", "deprecated", true);
        List<Option> options = new ArrayList<>();
        options.add(dep);
        AtomicInteger invoked = new AtomicInteger(0);
        Consumer<Option> handler = o -> invoked.incrementAndGet();
        CommandLine cmd = createCommandLine(options, handler);
        // The option exists and is deprecated, so hasOption should return true and invoke the handler once
        assertTrue(cmd.hasOption("d"));
        assertEquals(1, invoked.get());
        // Calling again should also return true and invoke handler again
        assertTrue(cmd.hasOption("deprecated"));
        assertEquals(2, invoked.get());
    }

}

/*
 Minimal Option class to support testing. Placed in the same package as CommandLine
 so it matches the expected type used by the CommandLine class under test.
*/
class Option {

    private final String opt;

    private final String longOpt;

    private final boolean deprecated;

    public Option(String opt, String longOpt, boolean deprecated) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.deprecated = deprecated;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}
