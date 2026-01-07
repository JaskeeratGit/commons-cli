package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

class DefaultParser_builder_0_0_Test_testBuilderReturnsNonNullBuilderInstance_viaReflection {

    @Test
    void testBuilderReturnsNonNullBuilderInstance_viaReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Invoke the static builder() method reflectively
        Method builderMethod = DefaultParser.class.getDeclaredMethod("builder");
        Object builderInstance = builderMethod.invoke(null);
        assertNotNull(builderInstance, "builder() should not return null");
        // The builder should be a nested Builder class declared in DefaultParser
        Class<?> builderClass = builderInstance.getClass();
        assertEquals("Builder", builderClass.getSimpleName(), "Returned object's class simple name should be 'Builder'");
        assertEquals(DefaultParser.class, builderClass.getDeclaringClass(), "Builder should be a nested class of DefaultParser");
        // Builder should be a member (nested) class and typically static
        assertTrue(builderClass.isMemberClass(), "Builder should be a member (nested) class");
        assertTrue(Modifier.isStatic(builderClass.getModifiers()), "Builder nested class should be static");
    }

}
