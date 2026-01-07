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
public class Options_toString_17_0_Test_toString_afterMutatingMaps_reflectsChanges {

    @SuppressWarnings("unchecked")
    private Map<String, Object> getInternalMap(Options options, String fieldName) throws Exception {
        Field f = Options.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (Map<String, Object>) f.get(options);
    }




    @Test
    public void toString_afterMutatingMaps_reflectsChanges() throws Exception {
        Options opts = new Options();
        Map<String, Object> shortMap = getInternalMap(opts, "shortOpts");
        Map<String, Object> longMap = getInternalMap(opts, "longOpts");
        shortMap.put("x", "X");
        longMap.put("y", "Y");
        String first = opts.toString();
        String expectedFirst = "[ Options: [ short {x=X} ] [ long {y=Y} ]";
        assertEquals(expectedFirst, first);
        // Mutate maps further and verify toString reflects changes
        shortMap.put("z", "Z");
        longMap.clear();
        String second = opts.toString();
        String expectedSecond = "[ Options: [ short {x=X, z=Z} ] [ long {} ]";
        assertEquals(expectedSecond, second);
    }
}
