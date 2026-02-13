package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }

    @Test
    public void getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt() throws Exception {
        Option opt = new Option("b", true, "has values");
        // add values for processing by setting the internal values field reflectively
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        Object internal = valuesField.get(opt);
        if (internal instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) internal;
            list.add("first");
            list.add("second");
        } else if (internal instanceof String[]) {
            valuesField.set(opt, new String[] { "first", "second" });
        } else {
            // fallback: try to find a "value" field or fail
            try {
                Field single = Option.class.getDeclaredField("value");
                single.setAccessible(true);
                single.set(opt, "first");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Unable to set option values reflectively", e);
            }
        }

        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        assertEquals("first", cmd.getOptionValue("b"));
        assertEquals("first", cmd.getOptionValue("-b"));
    }

}
