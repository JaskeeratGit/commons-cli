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

public class CommandLine_getParsedOptionValues_43_0_Test_testValuesNullReturnsDefault {


    @Test
    public void testValuesNullReturnsDefault() throws Exception {
        final CommandLine cmd = new CommandLine();
        // Create an option but do not put it into the command line's options list,
        // so getOptionValues(option) will return null.
        final Option opt = new Option("o", true, "opt");
        opt.setType(String.class);
        // identity
        opt.setConverter((Converter<String, RuntimeException>) s -> s);
        Supplier<String[]> def = () -> new String[] { "fallback" };
        String[] result = cmd.getParsedOptionValues(opt, def);
        assertArrayEquals(new String[] { "fallback" }, result);
    }


}
