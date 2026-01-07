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

public class CommandLine_hasOption_53_0_Test_testHasOptionNotPresent {

    @Test
    void testHasOptionNotPresent() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        AtomicBoolean invoked = new AtomicBoolean(false);
        Consumer<Option> handler = o -> invoked.set(true);
        CommandLine cmd = ctor.newInstance(args, options, handler);
        Option opt = new Option("a", "description");
        // option not present in list -> should return false and not invoke handler
        assertFalse(cmd.hasOption(opt));
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
