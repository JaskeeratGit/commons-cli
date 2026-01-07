package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
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
import java.util.function.Supplier;

public class CommandLine_getOptionProperties_8_0_Test_testPropertiesExtractedFromValues {

    @SuppressWarnings("unchecked")
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }


    @Test
    public void testPropertiesExtractedFromValues() throws Exception {
        Option opt = new Option("opt", "desc");
        // values: proper key=value, empty value, and a token without '=' which should be ignored
        opt.getValuesList().add("k1=v1");
        opt.getValuesList().add("k2=");
        opt.getValuesList().add("noequals");
        List<Option> options = new ArrayList<>();
        options.add(opt);
        CommandLine cmd = createCommandLine(new ArrayList<>(), options, null);
        Properties props = cmd.getOptionProperties(opt);
        assertNotNull(props);
        assertEquals("v1", props.getProperty("k1"), "k1 should map to v1");
        assertEquals("", props.getProperty("k2"), "k2 should map to empty string");
        assertNull(props.getProperty("noequals"), "tokens without '=' should not become properties");
    }


}
