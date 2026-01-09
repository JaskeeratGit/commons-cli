package org.apache.commons.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getParsedOptionValues_42_0_Test_testGetParsedOptionValues_nullOption_throwsNPE {

    @Test
    public void testGetParsedOptionValues_nullOption_throwsNPE() {
        CommandLine cmd = new CommandLine();
        // Cast the null to Option to disambiguate overloaded methods (OptionGroup/String overloads exist)
        assertThrows(NullPointerException.class, () -> {
            cmd.getParsedOptionValues((Option) null);
        });
    }

}
