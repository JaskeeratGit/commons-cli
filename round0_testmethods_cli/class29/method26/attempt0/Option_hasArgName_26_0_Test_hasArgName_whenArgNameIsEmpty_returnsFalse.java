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

class Option_hasArgName_26_0_Test_hasArgName_whenArgNameIsEmpty_returnsFalse {


    @Test
    void hasArgName_whenArgNameIsEmpty_returnsFalse() throws Exception {
        Option opt = new Option("b", "desc");
        opt.setArgName("");
        // direct API check
        assertFalse(opt.hasArgName());
        // also verify the private field actually contains the empty string via reflection
        Field argNameField = Option.class.getDeclaredField("argName");
        argNameField.setAccessible(true);
        Object fieldValue = argNameField.get(opt);
        assertEquals("", fieldValue);
    }


}
