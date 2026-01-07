package org.apache.commons.cli;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class DeprecatedAttributes_builder_0_0_Test_testBuilderIsDeclaredInnerClassInstance {



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

}
