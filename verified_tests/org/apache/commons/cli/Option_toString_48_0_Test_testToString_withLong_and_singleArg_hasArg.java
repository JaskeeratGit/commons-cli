package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

class Option_toString_48_0_Test_testToString_withLong_and_singleArg_hasArg {


    @Test
    void testToString_withLong_and_singleArg_hasArg() {
        // use constructor that sets long option and hasArg=true
        Option opt = new Option("o", "longOpt", true, "d");
        String s = opt.toString();
        assertNotNull(s);
        // long option should be present
        assertTrue(s.contains("longOpt"), "should contain the long option");
        // since hasArg=true and argCount==1, expect " [ARG]"
        assertTrue(s.contains(" [ARG]"), "should contain single [ARG] indicator");
        assertTrue(s.contains(":: d ::"), "should contain description");
    }


    // helper to instantiate DeprecatedAttributes via its private constructor
    private DeprecatedAttributes createDeprecatedAttributes(String description, String since, boolean forRemoval) throws Exception {
        Constructor<DeprecatedAttributes> ctor = DeprecatedAttributes.class.getDeclaredConstructor(String.class, String.class, boolean.class);
        ctor.setAccessible(true);
        return ctor.newInstance(description, since, forRemoval);
    }
}
