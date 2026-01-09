package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit test for CommandLine.getOptionValue(String).
 *
 * The original test called Option.addValueForProcessing(String) which is not present
 * in the Option API used here. Instead, we use a Mockito mock for Option and
 * stub the relevant getters (getLongOpt/getOpt/getValue) so CommandLine.resolveOption
 * and getOptionValue(Option) behave as expected for the test scenarios.
 */
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
        // Use a Mockito mock for Option and stub the necessary methods
        Option opt = mock(Option.class);
        when(opt.getOpt()).thenReturn("c");
        when(opt.getLongOpt()).thenReturn("charlie");
        when(opt.getValue()).thenReturn("longValue");

        List<Option> opts = new ArrayList<>();
        opts.add(opt);

        CommandLine cmd = createCommandLineWithOptions(opts);

        // with single or double hyphens or bare name, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("charlie"));
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
