package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }

    // Test-only Option subclass that provides addValueForProcessing and overrides value accessors
    static class TestOption extends Option {
        private final List<String> vals = new ArrayList<>();

        public TestOption(final String opt, final boolean hasArg, final String description) {
            super(opt, hasArg, description);
        }

        // provide the method expected by older tests
        public void addValueForProcessing(final String value) {
            vals.add(value);
        }

        @Override
        public String getValue() {
            return vals.isEmpty() ? null : vals.get(0);
        }

        @Override
        public String[] getValues() {
            return vals.toArray(new String[0]);
        }
    }

    @Test
    public void getOptionValue_returnsFirstValue_whenOptionHasValues_shortOpt() throws Exception {
        TestOption opt = new TestOption("b", true, "has values");
        // add values for processing (commons-cli Option exposes addValueForProcessing in some versions)
        opt.addValueForProcessing("first");
        opt.addValueForProcessing("second");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // current CommandLine resolves short options when prefixed with '-' for the String API,
        // so assert using "-b". Also assert the Option overload returns the expected value.
        assertEquals("first", cmd.getOptionValue("-b"));
        assertEquals("first", cmd.getOptionValue(opt));
    }

}
