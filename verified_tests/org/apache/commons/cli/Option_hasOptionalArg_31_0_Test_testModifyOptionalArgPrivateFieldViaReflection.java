package org.apache.commons.cli;

import org.apache.commons.cli.Option;
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

public class Option_hasOptionalArg_31_0_Test_testModifyOptionalArgPrivateFieldViaReflection {



    @Test
    void testModifyOptionalArgPrivateFieldViaReflection() throws Exception {
        Option opt = new Option("c", "desc");
        // access private field optionalArg via reflection
        Field optionalArgField = Option.class.getDeclaredField("optionalArg");
        optionalArgField.setAccessible(true);
        // set to true directly
        optionalArgField.setBoolean(opt, true);
        assertTrue(opt.hasOptionalArg(), "Directly setting private field optionalArg to true should reflect in hasOptionalArg()");
        // set to false directly
        optionalArgField.setBoolean(opt, false);
        assertFalse(opt.hasOptionalArg(), "Directly setting private field optionalArg to false should reflect in hasOptionalArg()");
    }
}
