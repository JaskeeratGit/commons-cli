package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLine_getParsedOptionValue_34_0_Test_testConstructCommandLineUsingPrivateConstructor_viaReflection {

    @Test
    void testConstructCommandLineUsingPrivateConstructor_viaReflection() throws Exception {
        // Use reflection to invoke the (non-public) constructor CommandLine(List,String,Consumer)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        Consumer<Option> handler = null;
        CommandLine cmd = ctor.newInstance(args, options, handler);

        Supplier<Long> supplier = () -> 999L;
        // Cast null to OptionGroup to disambiguate between the overloads:
        Long result = cmd.getParsedOptionValue((OptionGroup) null, supplier);
        assertEquals(Long.valueOf(999L), result);
    }
}
