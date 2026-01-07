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
public class Options_addOption_0_0_Test_testAddOption_MultipleRequired_OrderPreservedAndMovedIfReadded {

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
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
