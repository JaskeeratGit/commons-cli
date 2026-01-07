package org.apache.commons.cli;

import java.lang.reflect.Field;
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
import java.util.List;

class Options_addOption_1_0_Test_testAddOptionAddsToShortOptsAndReturnsThis {

    @SuppressWarnings("unchecked")
    private Map<String, Object> getShortOpts(Options options) throws Exception {
        Field f = Options.class.getDeclaredField("shortOpts");
        f.setAccessible(true);
        return (Map<String, Object>) f.get(options);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getLongOpts(Options options) throws Exception {
        Field f = Options.class.getDeclaredField("longOpts");
        f.setAccessible(true);
        return (Map<String, Object>) f.get(options);
    }

    @Test
    void testAddOptionAddsToShortOptsAndReturnsThis() throws Exception {
        Options opts = new Options();
        Options returned = opts.addOption("a", true, "description-a");
        assertSame(opts, returned, "addOption should return the same Options instance for chaining");
        Map<String, Object> shortOpts = getShortOpts(opts);
        assertEquals(1, shortOpts.size(), "shortOpts should contain one entry after adding one option");
        assertTrue(shortOpts.containsKey("a"), "shortOpts should contain the key 'a'");
        assertNotNull(shortOpts.get("a"), "the stored Option object for key 'a' should not be null");
        Map<String, Object> longOpts = getLongOpts(opts);
        assertFalse(longOpts.containsKey("a"), "longOpts should not contain the short option key 'a' (longOpt was null)");
    }


}
