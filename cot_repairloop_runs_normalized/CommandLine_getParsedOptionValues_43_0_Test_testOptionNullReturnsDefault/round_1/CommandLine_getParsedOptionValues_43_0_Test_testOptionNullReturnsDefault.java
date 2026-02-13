package org.apache.commons.cli;

import org.junit.jupiter.api.Test;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getParsedOptionValues_43_0_Test_testOptionNullReturnsDefault {

    @Test
    public void testOptionNullReturnsDefault() throws Exception {
        final CommandLine cmd = new CommandLine();
        Supplier<String[]> def = () -> new String[] { "default" };
        String[] result = cmd.getParsedOptionValues((Option) null, def);
        assertArrayEquals(new String[] { "default" }, result);
    }

}
