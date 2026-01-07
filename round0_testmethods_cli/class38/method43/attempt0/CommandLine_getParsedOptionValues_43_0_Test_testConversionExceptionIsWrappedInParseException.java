package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
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
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValues_43_0_Test_testConversionExceptionIsWrappedInParseException {




    @Test
    public void testConversionExceptionIsWrappedInParseException() throws Exception {
        final CommandLine cmd = new CommandLine();
        final Option opt = new Option("b", true, "bad");
        opt.setType(Integer.class);
        // Converter that throws an exception for any input
        opt.setConverter((Converter<Integer, RuntimeException>) s -> {
            throw new RuntimeException("converter failed");
        });
        opt.getValuesList().add("x");
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        List<Option> injected = new ArrayList<>();
        injected.add(opt);
        optionsField.set(cmd, injected);
        Supplier<Integer[]> def = () -> new Integer[] { 0 };
        assertThrows(ParseException.class, () -> cmd.getParsedOptionValues(opt, def));
    }
}
