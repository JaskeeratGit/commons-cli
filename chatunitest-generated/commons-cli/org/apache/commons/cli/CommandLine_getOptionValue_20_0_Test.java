package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

@ExtendWith(MockitoExtension.class)
public class CommandLine_getOptionValue_20_0_Test {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }

    @Test
    public void getOptionValue_returnsNull_whenOptionNameIsNull() throws Exception {
        CommandLine cmd = createCommandLineWithOptions(new ArrayList<>());
        assertNull(cmd.getOptionValue((String) null));
    }

    @Test
    public void getOptionValue_returnsNull_whenOptionNotFound() throws Exception {
        CommandLine cmd = createCommandLineWithOptions(new ArrayList<>());
        assertNull(cmd.getOptionValue("nonexistent"));
        // with leading hyphens too
        assertNull(cmd.getOptionValue("--nonexistent"));
    }

    @Test
    public void getOptionValue_returnsNull_whenOptionHasNoValues() throws Exception {
        Option opt = Mockito.mock(Option.class);
        when(opt.getOpt()).thenReturn("a");
        when(opt.getLongOpt()).thenReturn(null);
        // option exists but has no associated values -> should return null
        when(opt.getValuesList()).thenReturn(new ArrayList<>());
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        assertNull(cmd.getOptionValue("a"));
        assertNull(cmd.getOptionValue("-a"));
        assertNull(cmd.getOptionValue("--a"));
    }

    @Test
    public void getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt() throws Exception {
        Option opt = Mockito.mock(Option.class);
        when(opt.getOpt()).thenReturn("b");
        when(opt.getLongOpt()).thenReturn(null);
        when(opt.getValuesList()).thenReturn(Arrays.asList("first", "second"));
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        assertEquals("first", cmd.getOptionValue("b"));
        assertEquals("first", cmd.getOptionValue("-b"));
    }

    @Test
    public void getOptionValue_resolvesLongOption_withLeadingHyphens() throws Exception {
        Option opt = Mockito.mock(Option.class);
        when(opt.getOpt()).thenReturn("c");
        when(opt.getLongOpt()).thenReturn("charlie");
        when(opt.getValuesList()).thenReturn(Arrays.asList("longValue"));
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // with single or double hyphens or bare name, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("charlie"));
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
