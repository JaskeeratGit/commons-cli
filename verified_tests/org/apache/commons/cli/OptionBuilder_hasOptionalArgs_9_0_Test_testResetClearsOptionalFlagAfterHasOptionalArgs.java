package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("deprecation")
class OptionBuilder_hasOptionalArgs_9_0_Test_testResetClearsOptionalFlagAfterHasOptionalArgs {

    @BeforeEach
    void resetOptionBuilder() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }




    @Test
    void testResetClearsOptionalFlagAfterHasOptionalArgs() throws Exception {
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        optionalArgField.setAccessible(true);
        // set state to optional
        OptionBuilder.hasOptionalArgs(4);
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should be true after hasOptionalArgs");
        // invoke private reset()
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
        // after reset optionalArg should be back to default (false)
        assertFalse(optionalArgField.getBoolean(null), "optionalArg should be false after reset");
    }
}
