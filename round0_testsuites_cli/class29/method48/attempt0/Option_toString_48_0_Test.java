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

class Option_toString_48_0_Test {

    @Test
    void testToString_noLong_noArgs_defaults() {
        Option opt = new Option("o", "desc");
        String s = opt.toString();
        assertNotNull(s);
        assertTrue(s.startsWith("[ "), "should start with '[ '");
        assertTrue(s.contains("Option o"), "should contain option short name");
        assertTrue(s.contains(":: desc ::"), "should contain description segment");
        assertTrue(s.contains("class java.lang.String"), "default type should be String.class");
        assertTrue(s.endsWith(" ]"), "should end with ' ]'");
    }

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

    @Test
    void testToString_withLong_deprecated_and_multipleArgs_hasArgs_and_customType() throws Exception {
        Option opt = new Option("o", "desc");
        // set long option and multiple args
        opt.setLongOpt("longer");
        // >1 => hasArgs()
        opt.setArgs(3);
        // change type to Integer to verify type printing
        opt.setType(Integer.class);
        // create a DeprecatedAttributes instance via reflection (constructor is private)
        DeprecatedAttributes deprecated = createDeprecatedAttributes("old", "1.0", true);
        // set the private final 'deprecated' field on the Option instance via reflection
        Field deprecatedField = Option.class.getDeclaredField("deprecated");
        deprecatedField.setAccessible(true);
        deprecatedField.set(opt, deprecated);
        String s = opt.toString();
        assertNotNull(s);
        // long option present
        assertTrue(s.contains("longer"), "should contain the long option");
        // deprecated.toString() should be included
        assertTrue(s.contains("Deprecated for removal since 1.0: old"), "should include deprecated attributes");
        // hasArgs() true => "[ARG...]"
        assertTrue(s.contains("[ARG...]"), "should contain varargs indicator [ARG...]");
        // type changed to Integer
        assertTrue(s.contains("class java.lang.Integer"), "should reflect updated type");
        // description present
        assertTrue(s.contains(":: desc ::"), "should contain description");
    }

    // helper to instantiate DeprecatedAttributes via its private constructor
    private DeprecatedAttributes createDeprecatedAttributes(String description, String since, boolean forRemoval) throws Exception {
        Constructor<DeprecatedAttributes> ctor = DeprecatedAttributes.class.getDeclaredConstructor(String.class, String.class, boolean.class);
        ctor.setAccessible(true);
        return ctor.newInstance(description, since, forRemoval);
    }
}
