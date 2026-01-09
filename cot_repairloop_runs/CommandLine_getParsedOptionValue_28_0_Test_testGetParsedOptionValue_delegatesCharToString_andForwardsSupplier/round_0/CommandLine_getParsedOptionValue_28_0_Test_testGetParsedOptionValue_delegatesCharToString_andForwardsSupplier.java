package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLine_getParsedOptionValue_28_0_Test_testGetParsedOptionValue_delegatesCharToString_andForwardsSupplier {

    // A small subclass to observe delegation from the char-based API to the String-based API.
    static class TestableCommandLine extends CommandLine {

        String lastReceivedOption;

        Object lastReceivedDefaultResult;

        TestableCommandLine() {
            // protected no-arg constructor of CommandLine
            super();
        }

        @Override
        public <T> T getParsedOptionValue(final String option, final Supplier<T> defaultValue) {
            // capture the passed option and supplier result, then return the supplier result
            lastReceivedOption = option;
            T result = null;
            if (defaultValue != null) {
                result = defaultValue.get();
                lastReceivedDefaultResult = result;
            } else {
                lastReceivedDefaultResult = null;
            }
            return result;
        }
    }

    private TestableCommandLine subject;

    @BeforeEach
    void setUp() {
        subject = new TestableCommandLine();
    }

    @Test
    void testGetParsedOptionValue_delegatesCharToString_andForwardsSupplier() throws ParseException {
        Supplier<String> supplier = () -> "myDefault";
        String returned = subject.getParsedOptionValue('z', supplier);
        // Ensure delegation happened: the String-version should have received "z"
        assertEquals("z", subject.lastReceivedOption, "Expected the char to be converted to its String form and forwarded");
        // Ensure the returned value came from the supplier forwarded to the String-version method
        assertEquals("myDefault", returned);
        assertEquals("myDefault", subject.lastReceivedDefaultResult);
    }

}
