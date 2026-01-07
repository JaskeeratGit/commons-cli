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

public class OptionBuilder_hasArgs_6_0_Test_testHasArgsSetsNegativeValue {

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
    public void testHasArgsSetsNegativeValue() throws Exception {
        int value = -1;
        OptionBuilder returned = OptionBuilder.hasArgs(value);
        OptionBuilder instance = getInstanceField();
        assertSame(instance, returned, "hasArgs should return the singleton INSTANCE for negative value");
        assertEquals(value, getArgCount(), "argCount should be set to the provided negative value");
    }


}
