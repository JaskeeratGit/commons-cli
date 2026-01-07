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

public class Options_addRequiredOption_6_0_Test_testAddRequiredOptionWithLongOpt {


    @Test
    public void testAddRequiredOptionWithLongOpt() throws Exception {
        Options opts = new Options();
        // call focal method with a long option
        opts.addRequiredOption("x", "x-long", true, "description-x");
        // reflectively inspect internal structures
        Map<String, Option> shortOpts = getPrivateField(opts, "shortOpts");
        Map<String, Option> longOpts = getPrivateField(opts, "longOpts");
        List<?> requiredOpts = getPrivateField(opts, "requiredOpts");
        // shortOpts should contain key "x"
        assertTrue(shortOpts.containsKey("x"));
        Option added = shortOpts.get("x");
        Method getOpt = Option.class.getMethod("getOpt");
        Method getLongOpt = Option.class.getMethod("getLongOpt");
        assertEquals("x", getOpt.invoke(added));
        assertEquals("x-long", getLongOpt.invoke(added));
        // longOpts should contain mapping for the long option name
        assertTrue(longOpts.containsKey("x-long"));
        Option longMapped = longOpts.get("x-long");
        assertEquals("x-long", getLongOpt.invoke(longMapped));
        // requiredOpts should contain the key for the required option
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("x"));
    }


    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(target);
    }
}
