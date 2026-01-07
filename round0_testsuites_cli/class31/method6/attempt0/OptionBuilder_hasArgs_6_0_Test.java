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

public class OptionBuilder_hasArgs_6_0_Test {

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
    public void testHasArgsSetsPositiveValue() throws Exception {
        int value = 3;
        OptionBuilder returned = OptionBuilder.hasArgs(value);
        // returned should be the singleton instance
        OptionBuilder instance = getInstanceField();
        assertSame(instance, returned, "hasArgs should return the singleton INSTANCE");
        // argCount should be updated to the provided value
        assertEquals(value, getArgCount(), "argCount should be set to the provided positive value");
    }

    @Test
    public void testHasArgsSetsZero() throws Exception {
        int value = 0;
        OptionBuilder returned = OptionBuilder.hasArgs(value);
        OptionBuilder instance = getInstanceField();
        assertSame(instance, returned, "hasArgs should return the singleton INSTANCE for zero");
        assertEquals(value, getArgCount(), "argCount should be set to zero when zero is provided");
    }

    @Test
    public void testHasArgsSetsNegativeValue() throws Exception {
        int value = -1;
        OptionBuilder returned = OptionBuilder.hasArgs(value);
        OptionBuilder instance = getInstanceField();
        assertSame(instance, returned, "hasArgs should return the singleton INSTANCE for negative value");
        assertEquals(value, getArgCount(), "argCount should be set to the provided negative value");
    }

    @Test
    public void testHasArgsSetsMaxIntValue() throws Exception {
        int value = Integer.MAX_VALUE;
        OptionBuilder returned = OptionBuilder.hasArgs(value);
        OptionBuilder instance = getInstanceField();
        assertSame(instance, returned, "hasArgs should return the singleton INSTANCE for Integer.MAX_VALUE");
        assertEquals(value, getArgCount(), "argCount should be set to Integer.MAX_VALUE");
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
