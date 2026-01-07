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

public class CommandLine_getOptionValue_16_0_Test_returnsDefaultWhenOptionMissing {


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


}
