package org.apache.commons.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Options_getOptionGroup_9_0_Test_testGetOptionGroupReturnsNullWhenMissing {

    @Test
    public void testGetOptionGroupReturnsNullWhenMissing() throws Exception {
        Options options = new Options();
        Option opt = new Option("b", "desc");
        OptionGroup result = options.getOptionGroup(opt);
        assertNull(result, "Expected null when no OptionGroup is associated with the option key");
    }

}
