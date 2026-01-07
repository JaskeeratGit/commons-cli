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
public class Options_addOption_0_0_Test_testAddOption_WithLongOpt_NotRequired {

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
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



}
