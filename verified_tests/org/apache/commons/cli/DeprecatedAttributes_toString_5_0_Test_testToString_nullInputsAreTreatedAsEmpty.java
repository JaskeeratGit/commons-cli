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

public class DeprecatedAttributes_toString_5_0_Test_testToString_nullInputsAreTreatedAsEmpty {

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
    public void testToString_nullInputsAreTreatedAsEmpty() throws Exception {
        // Pass nulls to constructor; private toEmpty should convert to EMPTY_STRING
        DeprecatedAttributes attr = newInstance(null, null, true);
        // since and description become empty strings; only forRemoval branch applies
        assertEquals("Deprecated for removal", attr.toString());
    }

}
