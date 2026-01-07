package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;

public class CommandLine_getOptionValue_16_0_Test {

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

    @Test
    void returnsDefaultWhenOptionMissing() throws Exception {
        // create an option but do not add it to CommandLine.options => getOptionValue should be null
        Option opt = new Option("b", false, "desc");
        // create CommandLine with empty options list
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        AtomicBoolean called = new AtomicBoolean(false);
        Supplier<String> supplier = () -> {
            called.set(true);
            return "defaultVal";
        };
        String result = cmd.getOptionValue(opt, supplier);
        assertTrue(called.get(), "Supplier should be invoked when option value is absent");
        assertEquals("defaultVal", result);
    }

    @Test
    void returnsDefaultWhenOptionPresentButNoValues() throws Exception {
        // create an Option with no values and add it to CommandLine.options
        Option opt = new Option("c", false, "desc");
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        AtomicBoolean called = new AtomicBoolean(false);
        Supplier<String> supplier = () -> {
            called.set(true);
            return "fallback";
        };
        String result = cmd.getOptionValue(opt, supplier);
        assertTrue(called.get(), "Supplier should be invoked when option has no values");
        assertEquals("fallback", result);
    }

    @Test
    void returnsNullWhenOptionMissingAndSupplierIsNull() throws Exception {
        // create an option not present and pass null as supplier
        Option opt = new Option("d", false, "desc");
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        String result = cmd.getOptionValue(opt, (Supplier<String>) null);
        assertNull(result, "Should return null when option missing and default supplier is null");
    }
}
