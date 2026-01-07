package org.apache.commons.cli;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_withDescription_14_0_Test_testMultipleCallsReturnSameInstanceAndLatestDescriptionWins {

    private Field descriptionField;

    private Field instanceField;

    @BeforeEach
    public void setUp() throws Exception {
        descriptionField = OptionBuilder.class.getDeclaredField("description");
        descriptionField.setAccessible(true);
        // ensure a clean start: null description
        descriptionField.set(null, null);
        instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // reset description to null after each test to avoid cross-test pollution
        if (descriptionField != null) {
            descriptionField.set(null, null);
        }
    }



    @Test
    public void testMultipleCallsReturnSameInstanceAndLatestDescriptionWins() throws Exception {
        OptionBuilder first = OptionBuilder.withDescription("first");
        OptionBuilder second = OptionBuilder.withDescription("second");
        // both calls should return same singleton instance
        assertSame(first, second, "multiple calls should return the same singleton instance");
        Object instance = instanceField.get(null);
        assertSame(instance, first, "returned instance should be the private INSTANCE");
        // description should reflect the latest call
        Object stored = descriptionField.get(null);
        assertEquals("second", stored, "description should reflect the latest value passed to withDescription");
    }
}
