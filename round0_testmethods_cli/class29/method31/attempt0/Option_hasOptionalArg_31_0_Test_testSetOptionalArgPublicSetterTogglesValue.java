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

public class Option_hasOptionalArg_31_0_Test_testSetOptionalArgPublicSetterTogglesValue {


    @Test
    void testSetOptionalArgPublicSetterTogglesValue() throws Exception {
        Option opt = new Option("b", "desc");
        // set via public setter
        opt.setOptionalArg(true);
        assertTrue(opt.hasOptionalArg(), "setOptionalArg(true) should cause hasOptionalArg() to return true");
        // toggle back to false
        opt.setOptionalArg(false);
        assertFalse(opt.hasOptionalArg(), "setOptionalArg(false) should cause hasOptionalArg() to return false");
    }

}
