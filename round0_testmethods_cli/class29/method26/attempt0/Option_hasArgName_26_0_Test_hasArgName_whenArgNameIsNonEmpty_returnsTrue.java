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

class Option_hasArgName_26_0_Test_hasArgName_whenArgNameIsNonEmpty_returnsTrue {



    @Test
    void hasArgName_whenArgNameIsNonEmpty_returnsTrue() throws Exception {
        Option opt = new Option("c", "desc");
        opt.setArgName("ARG");
        // direct API check
        assertTrue(opt.hasArgName());
        // reflective invocation of the method
        Method hasArgName = Option.class.getDeclaredMethod("hasArgName");
        hasArgName.setAccessible(true);
        Object result = hasArgName.invoke(opt);
        assertTrue((Boolean) result);
    }

}
