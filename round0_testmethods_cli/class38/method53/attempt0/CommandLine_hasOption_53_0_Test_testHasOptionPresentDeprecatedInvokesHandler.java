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

public class CommandLine_hasOption_53_0_Test_testHasOptionPresentDeprecatedInvokesHandler {



    @Test
    void testHasOptionPresentDeprecatedInvokesHandler() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        DeprecatedOption depr = new DeprecatedOption("c");
        options.add(depr);
        AtomicBoolean invoked = new AtomicBoolean(false);
        Consumer<Option> handler = o -> {
            // ensure the handler receives the same option instance
            invoked.set(o == depr);
        };
        CommandLine cmd = ctor.newInstance(args, options, handler);
        // option present and overridden to be deprecated -> should return true and invoke handler
        assertTrue(cmd.hasOption(depr));
        assertTrue(invoked.get());
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
