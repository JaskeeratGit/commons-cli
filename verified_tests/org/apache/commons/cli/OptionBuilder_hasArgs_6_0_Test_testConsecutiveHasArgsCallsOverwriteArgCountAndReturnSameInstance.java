package org.apache.commons.cli;

import org.apache.commons.cli.OptionBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_hasArgs_6_0_Test_testConsecutiveHasArgsCallsOverwriteArgCountAndReturnSameInstance {

    @BeforeEach
    public void setUp() throws Exception {
        // reset static state before each test by invoking the private reset() method
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // reset static state after each test as well
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    private int getArgCount() throws Exception {
        Field argCount = OptionBuilder.class.getDeclaredField("argCount");
        argCount.setAccessible(true);
        return argCount.getInt(null);
    }

    private OptionBuilder getInstanceField() throws Exception {
        Field instance = OptionBuilder.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        return (OptionBuilder) instance.get(null);
    }





    @Test
    public void testConsecutiveHasArgsCallsOverwriteArgCountAndReturnSameInstance() throws Exception {
        OptionBuilder first = OptionBuilder.hasArgs(5);
        assertEquals(5, getArgCount(), "argCount should be 5 after first call");
        OptionBuilder second = OptionBuilder.hasArgs(2);
        assertEquals(2, getArgCount(), "argCount should be overwritten to 2 after second call");
        // both returned references must be the singleton instance
        OptionBuilder instance = getInstanceField();
        assertSame(instance, first, "first call should return the singleton INSTANCE");
        assertSame(instance, second, "second call should return the singleton INSTANCE");
        assertSame(first, second, "both calls should return the identical INSTANCE object");
    }
}
