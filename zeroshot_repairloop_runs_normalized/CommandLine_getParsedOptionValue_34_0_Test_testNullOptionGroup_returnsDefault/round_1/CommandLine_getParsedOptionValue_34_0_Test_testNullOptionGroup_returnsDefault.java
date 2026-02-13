package org.apache.commons.cli;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLine_getParsedOptionValue_34_0_Test_testNullOptionGroup_returnsDefault {

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
    void testNullOptionGroup_returnsDefault() throws Exception {
        CommandLine cmd = new TestCommandLine();
        Supplier<String> supplier = () -> "defaultValue";
        String result = cmd.getParsedOptionValue((OptionGroup) null, supplier);
        assertEquals("defaultValue", result);
    }

}
