package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public class Options_addRequiredOption_6_0_Test_testDuplicateRequiredOptionMovesToEnd {



    @Test
    public void testDuplicateRequiredOptionMovesToEnd() throws Exception {
        Options opts = new Options();
        // add two required options
        opts.addRequiredOption("a", "alpha", false, "d1");
        opts.addRequiredOption("b", "beta", false, "d2");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // initial order should be [a, b]
        assertEquals(2, requiredOpts.size());
        assertEquals("a", requiredOpts.get(0));
        assertEquals("b", requiredOpts.get(1));
        // add "a" again (duplicate key) - should remove existing and add at end
        opts.addRequiredOption("a", "alpha", false, "d3");
        List<?> requiredOptsAfter = getPrivateField(opts, "requiredOpts");
        assertEquals(2, requiredOptsAfter.size());
        // now order should be [b, a]
        assertEquals("b", requiredOptsAfter.get(0));
        assertEquals("a", requiredOptsAfter.get(1));
    }

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
    }
}
