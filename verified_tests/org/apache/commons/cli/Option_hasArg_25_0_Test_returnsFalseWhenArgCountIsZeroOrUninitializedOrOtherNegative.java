package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

class Option_hasArg_25_0_Test_returnsFalseWhenArgCountIsZeroOrUninitializedOrOtherNegative {

    private static void setPrivateIntField(final Object target, final String fieldName, final int value) throws Exception {
        Field f = Option.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.setInt(target, value);
    }

    private static boolean invokeHasArgReflectively(final Option option) throws Exception {
        Method m = Option.class.getDeclaredMethod("hasArg");
        m.setAccessible(true);
        return (Boolean) m.invoke(option);
    }



    @Test
    void returnsFalseWhenArgCountIsZeroOrUninitializedOrOtherNegative() throws Exception {
        Option opt = new Option("z", "desc");
        setPrivateIntField(opt, "argCount", 0);
        assertFalse(invokeHasArgReflectively(opt), "argCount == 0 should return false");
        setPrivateIntField(opt, "argCount", Option.UNINITIALIZED);
        assertFalse(invokeHasArgReflectively(opt), "argCount == UNINITIALIZED should return false");
        // other negative (not UNLIMITED_VALUES) should be false
        setPrivateIntField(opt, "argCount", -3);
        assertFalse(invokeHasArgReflectively(opt), "other negative argCount should return false");
    }
}
