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

public class CommandLine_getParsedOptionValues_43_0_Test_testSuccessfulConversion {



    @Test
    public void testSuccessfulConversion() throws Exception {
        final CommandLine cmd = new CommandLine();
        final Option opt = new Option("n", true, "number");
        // Set type to Integer and converter to Integer::valueOf
        opt.setType(Integer.class);
        @SuppressWarnings("unchecked")
        final Converter<Integer, RuntimeException> conv = s -> Integer.valueOf(s);
        opt.setConverter(conv);
        // add a value to the option's values list so getOptionValues will return it
        opt.getValuesList().add("123");
        // Inject the option into the CommandLine.options private field via reflection
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        List<Option> injected = new ArrayList<>();
        injected.add(opt);
        optionsField.set(cmd, injected);
        Supplier<Integer[]> def = () -> new Integer[] { 0 };
        Integer[] parsed = cmd.getParsedOptionValues(opt, def);
        assertNotNull(parsed);
        assertEquals(1, parsed.length);
        assertEquals(Integer.valueOf(123), parsed[0]);
    }

}
