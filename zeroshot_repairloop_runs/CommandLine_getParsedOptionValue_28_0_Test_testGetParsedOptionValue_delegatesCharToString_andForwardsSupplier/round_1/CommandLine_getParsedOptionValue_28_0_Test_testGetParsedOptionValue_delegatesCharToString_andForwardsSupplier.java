package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import org.apache.commons.cli.ParseException;

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
