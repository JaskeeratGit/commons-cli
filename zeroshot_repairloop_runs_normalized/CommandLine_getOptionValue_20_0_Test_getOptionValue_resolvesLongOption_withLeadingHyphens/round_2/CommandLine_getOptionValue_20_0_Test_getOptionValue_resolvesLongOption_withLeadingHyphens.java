package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_resolvesLongOption_withLeadingHyphens {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }

    @Test
    public void getOptionValue_resolvesLongOption_withLeadingHyphens() throws Exception {
        Option opt = new Option("c", true, "long opt") {
            @Override
            public String getValue() {
                return "longValue";
            }

            @Override
            public String[] getValues() {
                return new String[] { "longValue" };
            }
        };
        opt.setLongOpt("charlie");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // with single or double hyphens, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
