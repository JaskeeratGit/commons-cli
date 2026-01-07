package org.apache.commons.cli;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for OptionBuilder.withArgName(String)
 */
class OptionBuilder_withArgName_13_0_Test_testWithArgNameNullClearsNameAndReturnsSingleton {

    @BeforeEach
    void resetArgName() throws Exception {
        // Ensure argName starts from a known state before each test
        setPrivateStaticField(OptionBuilder.class, "argName", null);
    }


    @Test
    void testWithArgNameNullClearsNameAndReturnsSingleton() throws Exception {
        // Set an initial non-null value to ensure null clears it
        setPrivateStaticField(OptionBuilder.class, "argName", "initial");
        OptionBuilder returned = OptionBuilder.withArgName(null);
        // Verify argName is now null
        Object argNameVal = getPrivateStaticField(OptionBuilder.class, "argName");
        assertNull(argNameVal);
        // Verify returned instance is the private singleton INSTANCE
        OptionBuilder instanceFieldVal = (OptionBuilder) getPrivateStaticField(OptionBuilder.class, "INSTANCE");
        assertSame(instanceFieldVal, returned);
    }


    // Helper to read private static fields
    private static Object getPrivateStaticField(Class<?> clazz, String fieldName) throws Exception {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(null);
    }

    // Helper to write private static fields
    private static void setPrivateStaticField(Class<?> clazz, String fieldName, Object value) throws Exception {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, value);
    }
}
