package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class DeprecatedAttributes_toString_5_0_Test_testPrivateToEmptyMethodBehavior {

    private static Constructor<DeprecatedAttributes> getPrivateConstructor() throws NoSuchMethodException {
        Constructor<DeprecatedAttributes> ctor = DeprecatedAttributes.class.getDeclaredConstructor(String.class, String.class, boolean.class);
        ctor.setAccessible(true);
        return ctor;
    }

    private static DeprecatedAttributes newInstance(String description, String since, boolean forRemoval) throws Exception {
        Constructor<DeprecatedAttributes> ctor = getPrivateConstructor();
        return ctor.newInstance(description, since, forRemoval);
    }

    private static DeprecatedAttributes getDefaultInstanceViaReflection() throws Exception {
        Field defaultField = DeprecatedAttributes.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        return (DeprecatedAttributes) defaultField.get(null);
    }







    @Test
    public void testPrivateToEmptyMethodBehavior() throws Exception {
        // Create an instance to invoke the private instance method toEmpty
        DeprecatedAttributes attr = newInstance("d", "s", false);
        Method toEmpty = DeprecatedAttributes.class.getDeclaredMethod("toEmpty", String.class);
        toEmpty.setAccessible(true);
        // non-null input should return same string
        String nonNullResult = (String) toEmpty.invoke(attr, "abc");
        assertEquals("abc", nonNullResult);
        // null input should return the EMPTY_STRING (which is "")
        String nullResult = (String) toEmpty.invoke(attr, (Object) null);
        assertEquals("", nullResult);
    }
}
