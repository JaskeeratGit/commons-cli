package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLine_getParsedOptionValue_34_0_Test_testConstructCommandLineUsingPrivateConstructor_viaReflection {

    // A small subclass to allow interception of the String overload.
    // This relies on the overload in CommandLine being non-final/non-private so it can be overridden.
    static class TestCommandLine extends CommandLine {

        public TestCommandLine() {
            super();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getParsedOptionValue(final String opt, final Supplier<T> defaultValue) {
            // return a distinct value so we can detect delegation
            return (T) ("parsed-" + opt);
        }
    }

    @Test
    void testConstructCommandLineUsingPrivateConstructor_viaReflection() throws Exception {
        // Use reflection to invoke the private constructor CommandLine(List,String,Consumer)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        Consumer<Option> handler = null;
        CommandLine cmd = ctor.newInstance(args, options, handler);
        Supplier<Long> supplier = () -> 999L;
        Long result = cmd.getParsedOptionValue((OptionGroup) null, supplier);
        assertEquals(Long.valueOf(999L), result);
    }
}
