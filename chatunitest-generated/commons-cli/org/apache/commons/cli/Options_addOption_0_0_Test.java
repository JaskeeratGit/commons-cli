package org.apache.commons.cli;

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

/**
 * Unit tests for Options.addOption(Option)
 */
public class Options_addOption_0_0_Test {

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Class<?> clazz = target.getClass();
        Field f = null;
        while (clazz != null) {
            try {
                f = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        if (f == null) {
            throw new NoSuchFieldException(fieldName);
        }
        f.setAccessible(true);
        return (T) f.get(target);
    }

    @Test
    public void testAddOption_WithLongOpt_NotRequired() throws Exception {
        Options opts = new Options();
        Option opt = new Option("a", "alpha", false, "alpha option");
        // sanity: option should report hasLongOpt and key
        assertTrue(opt.hasLongOpt());
        assertEquals("a", opt.getKey());
        assertEquals("alpha", opt.getLongOpt());
        Options returned = opts.addOption(opt);
        // fluent API returns same instance
        assertSame(opts, returned);
        // inspect private maps/lists
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        Map<String, Option> longOpts = getPrivateField(opts, "longOpts");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // short option should be present under key "a"
        assertTrue(shortOpts.containsKey("a"));
        assertSame(opt, shortOpts.get("a"));
        // long option should be present under "alpha"
        assertTrue(longOpts.containsKey("alpha"));
        assertSame(opt, longOpts.get("alpha"));
        // not required, so required list empty
        assertTrue(requiredOpts.isEmpty());
    }

    @Test
    public void testAddOption_WithoutLongOpt_ButRequired() throws Exception {
        Options opts = new Options();
        Option opt = new Option("b", false, "b option");
        // mark as required
        opt.setRequired(true);
        Options returned = opts.addOption(opt);
        assertSame(opts, returned);
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        Map<String, Option> longOpts = getPrivateField(opts, "longOpts");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // short option present
        assertTrue(shortOpts.containsKey("b"));
        assertSame(opt, shortOpts.get("b"));
        // no long option for this instance
        assertTrue(longOpts.isEmpty());
        // required list should contain the key "b"
        assertEquals(1, requiredOpts.size());
        assertEquals("b", requiredOpts.get(0));
    }

    @Test
    public void testAddOption_Required_DuplicateKey_ReplacesInShortOpts_AndKeepsSingleRequiredEntry() throws Exception {
        Options opts = new Options();
        Option first = new Option("c", false, "first c");
        first.setRequired(true);
        opts.addOption(first);
        Option second = new Option("c", false, "second c");
        second.setRequired(true);
        opts.addOption(second);
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        Map<String, Option> longOpts = getPrivateField(opts, "longOpts");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // shortOpts should have the latest option mapped for key "c"
        assertSame(second, shortOpts.get("c"));
        // no long options involved
        assertTrue(longOpts.isEmpty());
        // required list should contain a single entry "c"
        assertEquals(1, requiredOpts.size());
        assertEquals("c", requiredOpts.get(0));
    }

    @Test
    public void testAddOption_MultipleRequired_OrderPreservedAndMovedIfReadded() throws Exception {
        Options opts = new Options();
        Option a = new Option("x", false, "x option");
        a.setRequired(true);
        opts.addOption(a);
        Option b = new Option("y", false, "y option");
        b.setRequired(true);
        opts.addOption(b);
        // Now required order should be [x, y]
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        assertEquals(2, requiredOpts.size());
        assertEquals("x", requiredOpts.get(0));
        assertEquals("y", requiredOpts.get(1));
        // Re-add x as required (new Option instance) — should move "x" to the end
        Option a2 = new Option("x", false, "x option v2");
        a2.setRequired(true);
        opts.addOption(a2);
        // Inspect again
        requiredOpts = getPrivateField(opts, "requiredOpts");
        assertEquals(2, requiredOpts.size());
        // "y" should now be first, "x" last
        assertEquals("y", requiredOpts.get(0));
        assertEquals("x", requiredOpts.get(1));
        // shortOpts should map "x" to the most recently added a2
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        assertSame(a2, shortOpts.get("x"));
    }
}
