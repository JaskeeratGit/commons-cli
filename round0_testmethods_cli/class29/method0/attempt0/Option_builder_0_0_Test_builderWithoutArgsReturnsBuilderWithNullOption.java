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

public class Option_builder_0_0_Test_builderWithoutArgsReturnsBuilderWithNullOption {

    @Test
    void builderWithoutArgsReturnsBuilderWithNullOption() throws Exception {
        // calls builder(null)
        Object builder = Option.builder();
        assertNotNull(builder, "builder() should not return null");
        Class<?> builderClass = builder.getClass();
        Field optionField = builderClass.getDeclaredField("option");
        optionField.setAccessible(true);
        Object optValue = optionField.get(builder);
        assertNull(optValue, "Builder.option should be null when created via Option.builder()");
    }


}
