package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class OptionBuilder_withType_17_0_Test_testWithType_acceptsClassAndSetsTypeFieldAndReturnsInstance {

    private static final String OPTION_BUILDER_CLASS = "org.apache.commons.cli.OptionBuilder";

    private Class<?> optionBuilderClass;

    private Field typeField;

    private Field instanceField;

    private Object originalTypeValue;

    private Object instanceValue;

    private Method withTypeObjectMethod;

    @BeforeEach
    public void setUp() throws Exception {
        optionBuilderClass = Class.forName(OPTION_BUILDER_CLASS);
        // access private static field 'type'
        typeField = optionBuilderClass.getDeclaredField("type");
        typeField.setAccessible(true);
        originalTypeValue = typeField.get(null);
        // access private static field 'INSTANCE'
        instanceField = optionBuilderClass.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceValue = instanceField.get(null);
        // get the focal method: public static OptionBuilder withType(Object)
        withTypeObjectMethod = optionBuilderClass.getDeclaredMethod("withType", Object.class);
        withTypeObjectMethod.setAccessible(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // restore original value of 'type' to avoid side effects between tests
        typeField.set(null, originalTypeValue);
    }

    @Test
    public void testWithType_acceptsClassAndSetsTypeFieldAndReturnsInstance() throws Exception {
        // invoke withType(Object) with a Class (String.class)
        Object returned = withTypeObjectMethod.invoke(null, String.class);
        // should return the singleton INSTANCE
        assertSame(instanceValue, returned, "withType(Class) should return the singleton INSTANCE");
        // private static field 'type' should be set to String.class
        Object currentType = typeField.get(null);
        assertEquals(String.class, currentType, "private field 'type' should be set to the provided Class");
    }



}
