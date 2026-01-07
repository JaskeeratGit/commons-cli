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

public class Options_addOption_3_0_Test_testAddOptionWithLongOptPopulatesLongOptsAndShortOpts {


    @Test
    public void testAddOptionWithLongOptPopulatesLongOptsAndShortOpts() throws Exception {
        Options opts = new Options();
        opts.addOption("b", "beta", true, "option with longOpt");
        // reflectively check longOpts
        Field longOptsField = Options.class.getDeclaredField("longOpts");
        longOptsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Option> longOpts = (Map<String, Option>) longOptsField.get(opts);
        assertTrue(longOpts.containsKey("beta"), "longOpts should contain key 'beta'");
        Option longStored = longOpts.get("beta");
        assertNotNull(longStored);
        assertEquals("option with longOpt", longStored.getDescription());
        // also check shortOpts contains the option under its key
        Field shortOptsField = Options.class.getDeclaredField("shortOpts");
        shortOptsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Option> shortOpts = (Map<String, Option>) shortOptsField.get(opts);
        // determine key used in shortOpts via Option.getKey()
        String key = longStored.getKey();
        assertTrue(shortOpts.containsKey(key), "shortOpts should contain the option's key");
        assertSame(longStored, shortOpts.get(key), "shortOpts entry and longOpts entry should reference same Option instance");
    }

}
