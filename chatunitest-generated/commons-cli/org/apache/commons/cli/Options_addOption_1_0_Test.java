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

class Options_addOption_1_0_Test {

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

    @Test
    void testAddOptionOverwriteSameOptCreatesNewOptionEntry() throws Exception {
        Options opts = new Options();
        opts.addOption("x", false, "first");
        Map<String, Object> shortOptsBefore = getShortOpts(opts);
        Object firstObj = shortOptsBefore.get("x");
        assertNotNull(firstObj, "first added Option object should not be null");
        int sizeBefore = shortOptsBefore.size();
        assertEquals(1, sizeBefore, "should have exactly one short option after first add");
        opts.addOption("x", true, "second");
        Map<String, Object> shortOptsAfter = getShortOpts(opts);
        Object secondObj = shortOptsAfter.get("x");
        assertNotNull(secondObj, "second added Option object should not be null");
        // Expect that the same key is present but overwritten by the new Option instance
        assertEquals(1, shortOptsAfter.size(), "size should remain 1 after adding an option with the same key");
        assertNotSame(firstObj, secondObj, "a second add with the same option key should replace the stored Option object");
    }

    @Test
    void testAddMultipleDistinctOptions() throws Exception {
        Options opts = new Options();
        opts.addOption("a", false, "desc-a");
        opts.addOption("b", true, "desc-b");
        Map<String, Object> shortOpts = getShortOpts(opts);
        assertEquals(2, shortOpts.size(), "shortOpts should contain two entries after adding two distinct options");
        assertTrue(shortOpts.containsKey("a"), "shortOpts should contain key 'a'");
        assertTrue(shortOpts.containsKey("b"), "shortOpts should contain key 'b'");
        assertNotNull(shortOpts.get("a"), "Option for 'a' should not be null");
        assertNotNull(shortOpts.get("b"), "Option for 'b' should not be null");
    }
}
