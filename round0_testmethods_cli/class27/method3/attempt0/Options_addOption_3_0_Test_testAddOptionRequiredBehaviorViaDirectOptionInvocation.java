package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Options_addOption_3_0_Test_testAddOptionRequiredBehaviorViaDirectOptionInvocation {



    @Test
    public void testAddOptionRequiredBehaviorViaDirectOptionInvocation() throws Exception {
        Options opts = new Options();
        // Create an Option and mark it required, then add using addOption(Option)
        Option req = new Option("r", "req", false, "required option");
        // setRequired is available on commons-cli Option
        req.setRequired(true);
        // call the public addOption(Option) to hit isRequired branch
        opts.addOption(req);
        // inspect requiredOpts private field
        Field requiredField = Options.class.getDeclaredField("requiredOpts");
        requiredField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Object> required = (List<Object>) requiredField.get(opts);
        // The key for the required option should be present (getKey() usually returns the short opt 'r')
        String expectedKey = req.getKey();
        assertTrue(required.contains(expectedKey), "requiredOpts should contain the expected key");
    }
}
