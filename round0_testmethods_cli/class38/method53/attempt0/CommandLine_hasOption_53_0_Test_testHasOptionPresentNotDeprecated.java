package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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

public class CommandLine_hasOption_53_0_Test_testHasOptionPresentNotDeprecated {


    @Test
    void testHasOptionPresentNotDeprecated() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Option opt = new Option("b", "description");
        options.add(opt);
        AtomicBoolean invoked = new AtomicBoolean(false);
        Consumer<Option> handler = o -> invoked.set(true);
        CommandLine cmd = ctor.newInstance(args, options, handler);
        // option present but not deprecated -> should return true and not invoke handler
        assertTrue(cmd.hasOption(opt));
        assertFalse(invoked.get());
    }


    // helper subclass overriding isDeprecated to simulate a deprecated Option
    static class DeprecatedOption extends Option {

        DeprecatedOption(String opt) {
            super(opt, "deprecated");
        }

        @Override
        public boolean isDeprecated() {
            return true;
        }
    }
}
