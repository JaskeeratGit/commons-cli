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

public class Option_builder_0_0_Test_builderWithStringCreatesBuilderWithOptionAndPrivateCtorCreatesOptionInstance {



    @Test
    void builderWithStringCreatesBuilderWithOptionAndPrivateCtorCreatesOptionInstance() throws Exception {
        Object builder = Option.builder("myOpt");
        assertNotNull(builder);
        Class<?> builderClass = builder.getClass();
        Field builderOptionField = builderClass.getDeclaredField("option");
        builderOptionField.setAccessible(true);
        Object builderOptValue = builderOptionField.get(builder);
        assertEquals("myOpt", builderOptValue, "Builder.option should hold the provided option string");
        // Invoke private Option(Builder) constructor to create an Option instance
        Constructor<?> privateCtor = Option.class.getDeclaredConstructor(builderClass);
        privateCtor.setAccessible(true);
        Object optionInstance = privateCtor.newInstance(builder);
        assertNotNull(optionInstance, "Option instance should be created when builder.option is set");
        // Verify the private 'option' field inside Option instance equals "myOpt"
        Field optionField = Option.class.getDeclaredField("option");
        optionField.setAccessible(true);
        Object optionFieldValue = optionField.get(optionInstance);
        assertEquals("myOpt", optionFieldValue, "Created Option should contain the provided option string");
    }
}
