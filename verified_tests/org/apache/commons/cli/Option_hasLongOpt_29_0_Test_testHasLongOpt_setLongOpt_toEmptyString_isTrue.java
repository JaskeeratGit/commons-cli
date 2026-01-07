package org.apache.commons.cli;

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

class Option_hasLongOpt_29_0_Test_testHasLongOpt_setLongOpt_toEmptyString_isTrue {




    @Test
    void testHasLongOpt_setLongOpt_toEmptyString_isTrue() throws Exception {
        Option opt = new Option("o", "option description");
        assertFalse(opt.hasLongOpt());
        // empty string is still non-null -> should be considered as having a long option
        opt.setLongOpt("");
        assertTrue(opt.hasLongOpt(), "hasLongOpt should be true when long option is an empty string");
        assertEquals("", opt.getLongOpt(), "getLongOpt should return the empty string set");
    }


}
