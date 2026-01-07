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

class Options_addOption_1_0_Test_testAddMultipleDistinctOptions {

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
