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

class Options_hasShortOption_15_0_Test_testHasShortOption_whenAbsent_and_edgeInputs {

    // Helper to insert entries into the private shortOpts map via reflection
    private void putShortOpt(final Options options, final String key) throws Exception {
        Field f = Options.class.getDeclaredField("shortOpts");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Object, Object> shortOpts = (Map<Object, Object>) f.get(options);
        shortOpts.put(key, new Object());
    }


    @Test
    void testHasShortOption_whenAbsent_and_edgeInputs() throws Exception {
        Options opts = new Options();
        // no entries added => all queries should be false
        assertFalse(opts.hasShortOption("b"), "non-existing key should not be found");
        assertFalse(opts.hasShortOption("-"), "single hyphen yields empty string -> not found");
        assertFalse(opts.hasShortOption(""), "empty string not found when not present");
        // null input when map doesn't contain null key
        assertFalse(opts.hasShortOption(null), "null should not be found when no null key present");
    }

}
