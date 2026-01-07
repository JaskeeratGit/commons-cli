package org.apache.commons.cli;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class DeprecatedAttributes_builder_0_0_Test {

    @Test
    void testBuilderNotNullAndClassName() throws Exception {
        Method builderMethod = DeprecatedAttributes.class.getMethod("builder");
        Object builder = builderMethod.invoke(null);
        assertNotNull(builder, "builder() should not return null");
        assertEquals("Builder", builder.getClass().getSimpleName(), "Returned object's simple class name should be 'Builder'");
    }

    @Test
    void testBuilderReturnsDistinctInstances() throws Exception {
        Method builderMethod = DeprecatedAttributes.class.getMethod("builder");
        Object first = builderMethod.invoke(null);
        Object second = builderMethod.invoke(null);
        assertNotNull(first);
        assertNotNull(second);
        assertNotSame(first, second, "builder() should return a new Builder instance on each invocation");
    }

    @Test
    void testBuilderIsDeclaredInnerClassInstance() throws Exception {
        Method builderMethod = DeprecatedAttributes.class.getMethod("builder");
        Object builder = builderMethod.invoke(null);
        Class<?>[] declared = DeprecatedAttributes.class.getDeclaredClasses();
        Class<?> found = null;
        for (Class<?> c : declared) {
            if ("Builder".equals(c.getSimpleName())) {
                found = c;
                break;
            }
        }
        assertNotNull(found, "DeprecatedAttributes should declare an inner class named Builder");
        assertEquals(found, builder.getClass(), "builder() should return an instance of the declared Builder inner class");
    }

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
