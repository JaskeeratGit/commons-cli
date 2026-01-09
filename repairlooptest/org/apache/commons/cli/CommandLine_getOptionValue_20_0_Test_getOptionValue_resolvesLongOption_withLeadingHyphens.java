package org.apache.commons.cli;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_resolvesLongOption_withLeadingHyphens {

    // Helper to instantiate CommandLine via its protected no-arg constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        // CommandLine has a protected no-arg constructor and a protected addOption method.
        // Since this test is in the same package, we can use them directly.
        CommandLine cmd = new CommandLine();
        for (Option opt : options) {
            cmd.addOption(opt);
        }
        return cmd;
    }

    @Test
    public void getOptionValue_resolvesLongOption_withLeadingHyphens() throws Exception {
        Option opt = new Option("c", true, "long opt");
        opt.setLongOpt("charlie");
        opt.addValueForProcessing("longValue");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // with single or double hyphens or bare name, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("charlie"));
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
