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

public class OptionBuilder_withType_16_0_Test_testWithTypeNullSetsTypeToNullAndReturnsSingleton {

    private static Field typeField;

    private static Method resetMethod;

    @BeforeEach
    public void setUp() throws Exception {
        // obtain references to private members via reflection
        typeField = OptionBuilder.class.getDeclaredField("type");
        typeField.setAccessible(true);
        resetMethod = OptionBuilder.class.getDeclaredMethod("reset");
        resetMethod.setAccessible(true);
        // ensure a clean state before each test
        resetMethod.invoke(null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // restore initial state after each test
        if (resetMethod != null) {
            resetMethod.invoke(null);
        }
    }


    @Test
    public void testWithTypeNullSetsTypeToNullAndReturnsSingleton() throws Exception {
        // set to a non-null type first
        OptionBuilder first = OptionBuilder.withType(Integer.class);
        Object before = typeField.get(null);
        assertEquals(Integer.class, before, "Precondition: 'type' should be Integer.class after first call");
        // now set type to null and verify
        OptionBuilder afterCall = OptionBuilder.withType(null);
        assertSame(first, afterCall, "withType(null) should still return the same singleton instance");
        Object currentType = typeField.get(null);
        assertNull(currentType, "The private 'type' field should be set to null when withType(null) is called");
    }
}
