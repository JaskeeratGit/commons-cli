package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
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
import java.util.Objects;
import java.util.Properties;

public class CommandLine_getOptionValue_16_0_Test_returnsOptionValueWhenPresent {

    @Test
    void returnsOptionValueWhenPresent() throws Exception {
        // create an Option and populate its private 'values' list via reflection
        Option opt = new Option("a", true, "desc");
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> vals = (List<String>) valuesField.get(opt);
        vals.add("value1");
        // construct CommandLine via private constructor to supply our options list
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        // supplier that should not be invoked
        Supplier<String> badSupplier = () -> {
            throw new AssertionError("Supplier should not be called when option value exists");
        };
        String result = cmd.getOptionValue(opt, badSupplier);
        assertEquals("value1", result);
    }



}
