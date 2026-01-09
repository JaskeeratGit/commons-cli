package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getOptionValue_16_0_Test_returnsNullWhenOptionMissingAndSupplierIsNull {

    @Test
    void returnsNullWhenOptionMissingAndSupplierIsNull() throws Exception {
        // create an option not present and pass null as supplier (cast to resolve overload ambiguity)
        Option opt = new Option("d", false, "desc");
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        CommandLine cmd = ctor.newInstance(args, options, (Consumer<Option>) null);
        // cast null to Supplier<String> to select the Supplier overload (avoids ambiguity with String overload)
        String result = cmd.getOptionValue(opt, (Supplier<String>) null);
        assertNull(result, "Should return null when option missing and default supplier is null");
    }
}
