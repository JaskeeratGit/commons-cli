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

class Option_hasLongOpt_29_0_Test_testHasLongOpt_setLongOpt_toNull_becomesFalse {



    @Test
    void testHasLongOpt_setLongOpt_toNull_becomesFalse() throws Exception {
        Option opt = new Option("o", "long-opt", false, "option description");
        assertTrue(opt.hasLongOpt());
        // set long option to null via setter
        opt.setLongOpt(null);
        assertFalse(opt.hasLongOpt(), "hasLongOpt should be false after setting long option to null");
        assertNull(opt.getLongOpt(), "getLongOpt should return null after setting to null");
    }



}
