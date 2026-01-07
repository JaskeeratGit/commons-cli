package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import java.util.HashSet;

class Options_getMatchingOptions_7_0_Test {

    private void setLongOpts(Options options, String... keys) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (String k : keys) {
            // values are irrelevant for getMatchingOptions; generics are erased at runtime
            map.put(k, new Object());
        }
        Field longOptsField = Options.class.getDeclaredField("longOpts");
        longOptsField.setAccessible(true);
        longOptsField.set(options, map);
    }

    @Test
    void testExactMatchReturnsSingleton_whenInputHasLeadingHyphens() throws Exception {
        Options opts = new Options();
        // Insert keys in insertion order
        setLongOpts(opts, "alpha", "al", "beta");
        List<String> res = opts.getMatchingOptions("--alpha");
        // exact match should return single-element list containing the cleaned option name
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("alpha", res.get(0));
        // ensure that the returned list is exactly a singleton list (Collections.singletonList used)
        // modifying it should throw UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> res.add("x"));
    }

    @Test
    void testPrefixMatchesReturnAllStartingWithClean_inInsertionOrder() throws Exception {
        Options opts = new Options();
        // insertion order matters; LinkedHashMap preserves it
        setLongOpts(opts, "foo", "foobar", "fizz", "bar");
        // "-fo" should be stripped to "fo" and match "foo" and "foobar" only
        List<String> res = opts.getMatchingOptions("-fo");
        assertNotNull(res);
        assertEquals(Arrays.asList("foo", "foobar"), res);
    }

    @Test
    void testExactMatchTakesPrecedenceOverPrefixMatches() throws Exception {
        Options opts = new Options();
        // "a" is exact, "ab" would also be a prefix match
        setLongOpts(opts, "a", "ab");
        // input "-a" should return only ["a"], not ["a","ab"]
        List<String> res = opts.getMatchingOptions("-a");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("a", res.get(0));
    }

    @Test
    void testNoMatchesReturnsEmptyList() throws Exception {
        Options opts = new Options();
        setLongOpts(opts, "x", "y", "z");
        List<String> res = opts.getMatchingOptions("nope");
        assertNotNull(res);
        assertTrue(res.isEmpty());
        // ensure the same empty list instance is not required; just that it's empty
    }

    @Test
    void testLeadingSingleHyphenAndNoHyphenBehaveSame_afterStripping() throws Exception {
        Options opts = new Options();
        setLongOpts(opts, "one", "two");
        List<String> res1 = opts.getMatchingOptions("-one");
        List<String> res2 = opts.getMatchingOptions("one");
        assertEquals(Collections.singletonList("one"), res1);
        assertEquals(Collections.singletonList("one"), res2);
    }
}
