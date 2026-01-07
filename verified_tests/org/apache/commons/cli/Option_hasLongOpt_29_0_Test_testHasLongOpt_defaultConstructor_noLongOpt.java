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

class Option_hasLongOpt_29_0_Test_testHasLongOpt_defaultConstructor_noLongOpt {

    @Test
    void testHasLongOpt_defaultConstructor_noLongOpt() throws Exception {
        // create option with only short option and description
        Option opt = new Option("o", "option description");
        assertFalse(opt.hasLongOpt(), "Expected no long option by default");
        assertNull(opt.getLongOpt(), "getLongOpt should return null when none set");
    }





}
