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

class Options_hasShortOption_15_0_Test_testHasShortOption_withNullAndEmptyKeysPresent {

    // Helper to insert entries into the private shortOpts map via reflection
    private void putShortOpt(final Options options, final String key) throws Exception {
        Field f = Options.class.getDeclaredField("shortOpts");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Object, Object> shortOpts = (Map<Object, Object>) f.get(options);
        shortOpts.put(key, new Object());
    }



    @Test
    void testHasShortOption_withNullAndEmptyKeysPresent() throws Exception {
        Options opts = new Options();
        // insert null and empty string keys into the internal shortOpts map
        putShortOpt(opts, null);
        putShortOpt(opts, "");
        // When null key present
        assertTrue(opts.hasShortOption(null), "null key present should be found");
        // Empty string key present: queries that normalize to empty string should be true
        assertTrue(opts.hasShortOption(""), "empty string key present should be found");
        assertTrue(opts.hasShortOption("-"), "single hyphen normalizes to empty string and should be found");
        assertTrue(opts.hasShortOption("--"), "double hyphen normalizes to empty string and should be found");
    }
}
