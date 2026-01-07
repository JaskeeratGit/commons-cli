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

/**
 * Unit tests for Options.toString() using reflection to manipulate private maps.
 */
public class Options_toString_17_0_Test_toString_withShortAndLongEntries_returnsExpected {

    @SuppressWarnings("unchecked")
    private Map<String, Object> getInternalMap(Options options, String fieldName) throws Exception {
        Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (Map<String, Object>) f.get(options);
    }


    @Test
    public void toString_withShortAndLongEntries_returnsExpected() throws Exception {
        Options opts = new Options();
        // Put entries into private shortOpts and longOpts via reflection
        Map<String, Object> shortMap = getInternalMap(opts, "shortOpts");
        Map<String, Object> longMap = getInternalMap(opts, "longOpts");
        shortMap.put("s", "SHORT_VAL");
        longMap.put("long-name", "LONG_VAL");
        String s = opts.toString();
        // LinkedHashMap toString preserves insertion and uses key=value format
        String expected = "[ Options: [ short {s=SHORT_VAL} ] [ long {long-name=LONG_VAL} ]";
        assertEquals(expected, s);
    }


}
