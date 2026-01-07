package org.apache.commons.cli;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class DeprecatedAttributes_builder_0_0_Test_testPrivateToEmptyBehavior {




    @Test
    void testPrivateToEmptyBehavior() throws Exception {
        // Access the package-private DEFAULT instance
        DeprecatedAttributes defaultInstance = DeprecatedAttributes.DEFAULT;
        assertNotNull(defaultInstance, "DEFAULT instance should be available");
        Method toEmpty = DeprecatedAttributes.class.getDeclaredMethod("toEmpty", String.class);
        toEmpty.setAccessible(true);
        // null -> expected empty string
        Object resNull = toEmpty.invoke(defaultInstance, new Object[] { null });
        assertTrue(resNull instanceof String);
        assertEquals("", resNull, "toEmpty(null) should return empty string");
        // empty string -> empty string
        Object resEmpty = toEmpty.invoke(defaultInstance, "");
        assertEquals("", resEmpty, "toEmpty(\"\") should return empty string");
        // non-empty -> same non-empty
        Object resValue = toEmpty.invoke(defaultInstance, "1.2.3");
        assertEquals("1.2.3", resValue, "toEmpty(nonEmpty) should return the same non-empty string");
    }
}
