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
class OptionBuilder_hasOptionalArgs_9_0_Test_testHasOptionalArgsWithZeroAndNegativeValues {

    @BeforeEach
    void resetOptionBuilder() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }


    @Test
    void testHasOptionalArgsWithZeroAndNegativeValues() throws Exception {
        // zero
        OptionBuilder.hasOptionalArgs(0);
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        argCountField.setAccessible(true);
        optionalArgField.setAccessible(true);
        assertEquals(0, argCountField.getInt(null), "argCount should be 0 when called with 0");
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should be true after call");
        // negative
        OptionBuilder.hasOptionalArgs(-1);
        assertEquals(-1, argCountField.getInt(null), "argCount should accept negative values as-is");
        assertTrue(optionalArgField.getBoolean(null), "optionalArg should remain true after call");
    }


}
