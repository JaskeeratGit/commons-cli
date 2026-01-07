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

class Option_hasLongOpt_29_0_Test_testHasLongOpt_constructorWithLongOpt_present {


    @Test
    void testHasLongOpt_constructorWithLongOpt_present() throws Exception {
        // create option with a long option via constructor
        Option opt = new Option("o", "long-opt", false, "option description");
        assertTrue(opt.hasLongOpt(), "Expected hasLongOpt to be true when constructed with a long option");
        assertEquals("long-opt", opt.getLongOpt(), "getLongOpt should return the long option set in constructor");
    }




}
