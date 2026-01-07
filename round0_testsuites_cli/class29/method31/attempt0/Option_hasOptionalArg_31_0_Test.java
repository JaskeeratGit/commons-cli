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

public class Option_hasOptionalArg_31_0_Test {

    @Test
    void testDefaultHasOptionalArgAndPrivateHasNoValuesViaReflection() throws Exception {
        // default construction
        Option opt = new Option("a", "description");
        // by default optionalArg should be false
        assertFalse(opt.hasOptionalArg(), "New Option should not have optionalArg by default");
        // invoke private hasNoValues() via reflection
        Method hasNoValues = Option.class.getDeclaredMethod("hasNoValues");
        hasNoValues.setAccessible(true);
        boolean noValues = (Boolean) hasNoValues.invoke(opt);
        assertTrue(noValues, "New Option should have no values initially");
        // invoke private add(String) to add a value and then verify hasNoValues() becomes false
        Method add = Option.class.getDeclaredMethod("add", String.class);
        add.setAccessible(true);
        add.invoke(opt, "value1");
        boolean noValuesAfterAdd = (Boolean) hasNoValues.invoke(opt);
        assertFalse(noValuesAfterAdd, "After adding a value, hasNoValues() should be false");
    }

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
