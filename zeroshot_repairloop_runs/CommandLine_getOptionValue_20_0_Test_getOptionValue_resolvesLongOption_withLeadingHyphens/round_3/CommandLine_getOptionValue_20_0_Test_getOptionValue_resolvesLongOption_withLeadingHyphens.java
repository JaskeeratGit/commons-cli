package org.apache.commons.cli;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_resolvesLongOption_withLeadingHyphens {

    // Helper to instantiate CommandLine and populate its options
    private CommandLine createCommandLineWithOptions(final List<Option> options) {
        // CommandLine has a protected no-arg constructor and a protected addOption method.
        // This test class is in the same package, so we can use them directly.
        CommandLine cmd = new CommandLine();
        for (Option o : options) {
            cmd.addOption(o);
        }
        return cmd;
    }

    @Test
    public void getOptionValue_resolvesLongOption_withLeadingHyphens() throws Exception {
        // Use a real Option instance and override getValue to avoid mocking issues
        Option opt = new Option("c", "charlie", true, "desc") {
            @Override
            public String getValue() {
                return "longValue";
            }
        };

        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // with single or double hyphens or bare name, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("charlie"));
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
