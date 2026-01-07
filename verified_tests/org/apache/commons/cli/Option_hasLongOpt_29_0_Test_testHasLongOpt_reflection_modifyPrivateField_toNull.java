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

class Option_hasLongOpt_29_0_Test_testHasLongOpt_reflection_modifyPrivateField_toNull {






    @Test
    void testHasLongOpt_reflection_modifyPrivateField_toNull() throws Exception {
        Option opt = new Option("x", "ref-long", false, "desc");
        assertTrue(opt.hasLongOpt());
        Field longOptField = Option.class.getDeclaredField("longOption");
        longOptField.setAccessible(true);
        longOptField.set(opt, null);
        assertFalse(opt.hasLongOpt(), "hasLongOpt should be false after reflection sets private longOption to null");
        assertNull(opt.getLongOpt());
    }
}
