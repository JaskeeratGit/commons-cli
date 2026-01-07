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

class CommandLine_getParsedOptionValue_31_0_Test_testGetParsedOptionValue_conversionSuccess_returnsConvertedValue {



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

}
