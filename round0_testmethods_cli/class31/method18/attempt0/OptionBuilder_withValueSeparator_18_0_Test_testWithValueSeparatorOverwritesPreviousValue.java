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

public class OptionBuilder_withValueSeparator_18_0_Test_testWithValueSeparatorOverwritesPreviousValue {

    // Helper to access the private static char field 'valueSeparator'
    private char getValueSeparator() throws Exception {
        Field field = OptionBuilder.class.getDeclaredField("valueSeparator");
        field.setAccessible(true);
        // static field -> null
        return field.getChar(null);
    }

    private void setValueSeparator(char c) throws Exception {
        Field field = OptionBuilder.class.getDeclaredField("valueSeparator");
        field.setAccessible(true);
        field.setChar(null, c);
    }

    // Invoke the private static reset() to restore OptionBuilder static state
    private void invokeReset() throws Exception {
        Method reset = OptionBuilder.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    @BeforeEach
    public void setup() throws Exception {
        // Ensure a clean state before each test
        invokeReset();
    }

    @AfterEach
    public void teardown() throws Exception {
        // Restore original state after each test to avoid test interference
        invokeReset();
    }


    @Test
    public void testWithValueSeparatorOverwritesPreviousValue() throws Exception {
        // Set valueSeparator to a non '=' value
        setValueSeparator(';');
        char before = getValueSeparator();
        assertEquals(';', before, "Precondition: valueSeparator should be set to ';'");
        // Invoke method which should overwrite it to '='
        OptionBuilder.withValueSeparator();
        char after = getValueSeparator();
        assertEquals('=', after, "withValueSeparator should overwrite any previous valueSeparator to '='");
    }
}
