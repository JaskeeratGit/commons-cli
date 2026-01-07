package org.apache.commons.cli;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_withDescription_14_0_Test_testWithDescriptionWithNullSetsDescriptionToNullAndReturnsSingleton {

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
    public void testWithDescriptionWithNullSetsDescriptionToNullAndReturnsSingleton() throws Exception {
        // start with a non-null description to ensure null overwrites
        descriptionField.set(null, "initial");
        OptionBuilder returned = OptionBuilder.withDescription(null);
        Object instance = instanceField.get(null);
        assertSame(instance, returned, "withDescription(null) should still return the singleton INSTANCE");
        Object stored = descriptionField.get(null);
        assertNull(stored, "description field should be set to null when called with null");
    }

}
