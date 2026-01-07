package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class Option_builder_0_0_Test_builderCreatedWithNullCausesPrivateOptionCtorToThrowIllegalStateException {


    @Test
    void builderCreatedWithNullCausesPrivateOptionCtorToThrowIllegalStateException() throws Exception {
        // builder.option == null and builder.longOption == null
        Object builder = Option.builder();
        Constructor<?> privateCtor = Option.class.getDeclaredConstructor(builder.getClass());
        privateCtor.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
            privateCtor.newInstance(builder);
        }, "Invoking private Option(Builder) ctor with no opt and no longOpt should throw");
        assertNotNull(ite.getCause(), "InvocationTargetException should have a cause");
        assertTrue(ite.getCause() instanceof IllegalStateException, "Cause should be IllegalStateException");
        assertEquals("Either opt or longOpt must be specified", ite.getCause().getMessage());
    }

}
