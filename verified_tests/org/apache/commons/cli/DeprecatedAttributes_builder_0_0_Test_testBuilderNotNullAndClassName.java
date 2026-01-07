package org.apache.commons.cli;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class DeprecatedAttributes_builder_0_0_Test_testBuilderNotNullAndClassName {

    @Test
    void testBuilderNotNullAndClassName() throws Exception {
        Method builderMethod = DeprecatedAttributes.class.getMethod("builder");
        Object builder = builderMethod.invoke(null);
        assertNotNull(builder, "builder() should not return null");
        assertEquals("Builder", builder.getClass().getSimpleName(), "Returned object's simple class name should be 'Builder'");
    }



}
