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
public class Options_addOption_0_0_Test_testAddOption_Required_DuplicateKey_ReplacesInShortOpts_AndKeepsSingleRequiredEntry {

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
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

}
