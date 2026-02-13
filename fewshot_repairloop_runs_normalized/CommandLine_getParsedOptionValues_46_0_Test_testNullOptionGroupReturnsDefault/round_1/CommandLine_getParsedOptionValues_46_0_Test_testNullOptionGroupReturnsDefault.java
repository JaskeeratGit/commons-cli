package org.apache.commons.cli;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CommandLine_getParsedOptionValues_46_0_Test_testNullOptionGroupReturnsDefault {

    @Test
    void testNullOptionGroupReturnsDefault() throws Exception {
        // Arrange
        CommandLine cmd = new CommandLine();
        Supplier<String[]> supplier = () -> new String[] { "default1", "default2" };
        // Act
        String[] result = cmd.getParsedOptionValues((OptionGroup) null, supplier);
        // Assert
        assertArrayEquals(supplier.get(), result);
    }

}
