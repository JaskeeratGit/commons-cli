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

public class OptionBuilder_withType_16_0_Test_testWithTypeSetsTypeAndReturnsSingleton {

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
    public void testWithTypeSetsTypeAndReturnsSingleton() throws Exception {
        // call withType and verify the private static 'type' field is set
        OptionBuilder instance1 = OptionBuilder.withType(String.class);
        // calling again should return the same INSTANCE (singleton)
        OptionBuilder instance2 = OptionBuilder.withType(String.class);
        assertSame(instance1, instance2, "withType should always return the same singleton instance");
        // static field: pass null
        Object currentType = typeField.get(null);
        assertEquals(String.class, currentType, "The private 'type' field should be set to String.class");
    }

}
