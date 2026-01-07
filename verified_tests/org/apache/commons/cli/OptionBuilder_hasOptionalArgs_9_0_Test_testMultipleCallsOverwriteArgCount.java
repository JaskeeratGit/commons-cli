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
class OptionBuilder_hasOptionalArgs_9_0_Test_testMultipleCallsOverwriteArgCount {

    @BeforeEach
    void resetOptionBuilder() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }



    @Test
    void testMultipleCallsOverwriteArgCount() throws Exception {
        Field argCountField = OptionBuilder.class.getDeclaredField("argCount");
        Field optionalArgField = OptionBuilder.class.getDeclaredField("optionalArg");
        argCountField.setAccessible(true);
        optionalArgField.setAccessible(true);
        OptionBuilder.hasOptionalArgs(2);
        assertEquals(2, argCountField.getInt(null));
        assertTrue(optionalArgField.getBoolean(null));
        OptionBuilder.hasOptionalArgs(5);
        assertEquals(5, argCountField.getInt(null), "later calls should overwrite argCount");
        assertTrue(optionalArgField.getBoolean(null));
    }

}
