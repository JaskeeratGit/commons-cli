package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

class CommandLine_getParsedOptionValue_31_0_Test_testGetParsedOptionValue_optionNonNull_getOptionValueNull_returnsDefault {


    @Test
    void testGetParsedOptionValue_optionNonNull_getOptionValueNull_returnsDefault() throws Exception {
        Option opt = new Option("o", true, "opt");
        // command line that returns null for any option value
        CommandLine cmd = new CommandLine() {

            @Override
            public String getOptionValue(final Option option) {
                return null;
            }
        };
        String result = cmd.getParsedOptionValue(opt, () -> "fallback");
        assertEquals("fallback", result);
    }


}
