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

class Options_hasShortOption_15_0_Test_testHasShortOption_whenPresent_variationsWithHyphens {

    // Helper to insert entries into the private shortOpts map via reflection
    private void putShortOpt(final Options options, final String key) throws Exception {
        Field f = Options.class.getDeclaredField("shortOpts");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Object, Object> shortOpts = (Map<Object, Object>) f.get(options);
        shortOpts.put(key, new Object());
    }

    @Test
    void testHasShortOption_whenPresent_variationsWithHyphens() throws Exception {
        Options opts = new Options();
        // add short option with key "a"
        putShortOpt(opts, "a");
        // direct, single hyphen and double hyphen should all map to "a"
        assertTrue(opts.hasShortOption("a"), "plain name should be found");
        assertTrue(opts.hasShortOption("-a"), "single hyphen should be normalized and found");
        assertTrue(opts.hasShortOption("--a"), "double hyphen should be normalized and found");
    }


}
