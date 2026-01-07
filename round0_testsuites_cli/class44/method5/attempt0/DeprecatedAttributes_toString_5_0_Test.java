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

public class DeprecatedAttributes_toString_5_0_Test {

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
    public void testToString_defaultInstanceField() throws Exception {
        DeprecatedAttributes def = getDefaultInstanceViaReflection();
        // DEFAULT created with "", "", false -> "Deprecated"
        assertEquals("Deprecated", def.toString());
    }

    @Test
    public void testToString_forRemovalOnly() throws Exception {
        DeprecatedAttributes attr = newInstance("", "", true);
        assertEquals("Deprecated for removal", attr.toString());
    }

    @Test
    public void testToString_sinceOnly() throws Exception {
        DeprecatedAttributes attr = newInstance("", "1.0", false);
        assertEquals("Deprecated since 1.0", attr.toString());
    }

    @Test
    public void testToString_descriptionOnly() throws Exception {
        DeprecatedAttributes attr = newInstance("Some feature is deprecated", "", false);
        assertEquals("Deprecated: Some feature is deprecated", attr.toString());
    }

    @Test
    public void testToString_allFieldsPresent() throws Exception {
        DeprecatedAttributes attr = newInstance("Use X instead", "2.5", true);
        assertEquals("Deprecated for removal since 2.5: Use X instead", attr.toString());
    }

    @Test
    public void testToString_nullInputsAreTreatedAsEmpty() throws Exception {
        // Pass nulls to constructor; private toEmpty should convert to EMPTY_STRING
        DeprecatedAttributes attr = newInstance(null, null, true);
        // since and description become empty strings; only forRemoval branch applies
        assertEquals("Deprecated for removal", attr.toString());
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
