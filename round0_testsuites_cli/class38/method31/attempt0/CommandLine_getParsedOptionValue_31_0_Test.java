package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

class CommandLine_getParsedOptionValue_31_0_Test {

    @Test
    void testGetParsedOptionValue_optionNull_usesDefault() throws Exception {
        CommandLine cmd = new CommandLine() {
        };
        String result = cmd.getParsedOptionValue(null, () -> "defaultValue");
        assertEquals("defaultValue", result);
        // Also invoke the private helper 'get' via reflection to satisfy private-method reflection usage
        Method getMethod = CommandLine.class.getDeclaredMethod("get", Supplier.class);
        getMethod.setAccessible(true);
        Object reflected = getMethod.invoke(cmd, (Supplier<String>) () -> "reflectedDefault");
        assertEquals("reflectedDefault", reflected);
    }

    @Test
    void testGetParsedOptionValue_optionNonNull_getOptionValueNull_returnsDefault() throws Exception {
        Option opt = new Option("o", true, "opt");
        // command line that returns null for any option value
        CommandLine cmd = new CommandLine() {

            @Override
            public String getOptionValue(final Option option) {
                return null;
            }
        };
        String result = cmd.getParsedOptionValue(opt, () -> "fallback");
        assertEquals("fallback", result);
    }

    @Test
    void testGetParsedOptionValue_conversionSuccess_returnsConvertedValue() throws Exception {
        Option opt = new Option("n", true, "number");
        // set a converter that converts string to Integer
        opt.setConverter((Converter<Integer, RuntimeException>) Integer::valueOf);
        CommandLine cmd = new CommandLine() {

            @Override
            public String getOptionValue(final Option option) {
                // ensure it's the expected option
                if (option == opt) {
                    return "123";
                }
                return null;
            }
        };
        Integer result = cmd.getParsedOptionValue(opt, () -> 0);
        assertEquals(123, result.intValue());
    }

    @Test
    void testGetParsedOptionValue_converterThrows_throwsParseException() throws Exception {
        Option opt = new Option("x", true, "bad");
        // converter that throws an exception
        opt.setConverter((Converter<Object, RuntimeException>) s -> {
            throw new IllegalArgumentException("bad convert");
        });
        CommandLine cmd = new CommandLine() {

            @Override
            public String getOptionValue(final Option option) {
                return "willFail";
            }
        };
        assertThrows(ParseException.class, () -> cmd.getParsedOptionValue(opt, () -> "unused"));
    }
}
