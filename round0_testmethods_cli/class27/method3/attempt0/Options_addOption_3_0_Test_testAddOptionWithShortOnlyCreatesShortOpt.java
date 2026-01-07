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

public class Options_addOption_3_0_Test_testAddOptionWithShortOnlyCreatesShortOpt {

    @Test
    public void testAddOptionWithShortOnlyCreatesShortOpt() throws Exception {
        Options opts = new Options();
        Options returned = opts.addOption("a", null, false, "short option a");
        // method is chainable
        assertSame(opts, returned);
        // inspect private shortOpts map
        Field shortOptsField = Options.class.getDeclaredField("shortOpts");
        shortOptsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Option> shortOpts = (Map<String, Option>) shortOptsField.get(opts);
        assertTrue(shortOpts.containsKey("a"), "shortOpts should contain key 'a'");
        Option stored = shortOpts.get("a");
        assertNotNull(stored, "Stored Option for key 'a' should not be null");
        assertEquals("short option a", stored.getDescription(), "Option description should match");
    }


}
