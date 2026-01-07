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
public class Options_addOption_0_0_Test_testAddOption_WithoutLongOpt_ButRequired {

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
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


}
