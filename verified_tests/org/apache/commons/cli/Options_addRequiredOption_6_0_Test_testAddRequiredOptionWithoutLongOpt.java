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

public class Options_addRequiredOption_6_0_Test_testAddRequiredOptionWithoutLongOpt {

    @Test
    public void testAddRequiredOptionWithoutLongOpt() throws Exception {
        Options opts = new Options();
        // call focal method
        Options ret = opts.addRequiredOption("a", null, false, "description-a");
        // should return same instance
        assertSame(opts, ret);
        // access private fields via reflection
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        Map<String, Option> longOpts = getPrivateField(opts, "longOpts");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // shortOpts should contain the option keyed by "a"
        assertTrue(shortOpts.containsKey("a"), "shortOpts should contain key 'a'");
        Option added = shortOpts.get("a");
        // check the option's opt value via public getOpt()
        Method getOpt = Option.class.getMethod("getOpt");
        Object optName = getOpt.invoke(added);
        assertEquals("a", optName);
        // since longOpt was null, longOpts should not contain any mapping
        assertFalse(longOpts.containsKey("a"));
        assertTrue(longOpts.isEmpty());
        // requiredOpts should contain the key for the required option
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }



    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
    }
}
