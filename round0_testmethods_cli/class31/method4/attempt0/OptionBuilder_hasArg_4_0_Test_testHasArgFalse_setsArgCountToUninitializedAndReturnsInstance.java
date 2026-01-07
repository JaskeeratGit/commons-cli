package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_hasArg_4_0_Test_testHasArgFalse_setsArgCountToUninitializedAndReturnsInstance {

    @BeforeEach
    void setUp() throws Exception {
        invokeReset();
    }


    @Test
    void testHasArgFalse_setsArgCountToUninitializedAndReturnsInstance() throws Exception {
        OptionBuilder returned = OptionBuilder.hasArg(false);
        assertSame(getInstance(), returned, "hasArg should return the singleton INSTANCE");
        assertEquals(Option.UNINITIALIZED, getArgCount(), "argCount should be set to Option.UNINITIALIZED when hasArg(false) is called");
    }



    // --- Reflection helpers ---
    private int getArgCount() throws Exception {
        Field f = OptionBuilder.class.getDeclaredField("argCount");
        f.setAccessible(true);
        return f.getInt(null);
    }

    private Object getInstance() throws Exception {
        Field f = OptionBuilder.class.getDeclaredField("INSTANCE");
        f.setAccessible(true);
        return f.get(null);
    }

    private void invokeReset() throws Exception {
        Method m = OptionBuilder.class.getDeclaredMethod("reset");
        m.setAccessible(true);
        m.invoke(null);
    }
}
