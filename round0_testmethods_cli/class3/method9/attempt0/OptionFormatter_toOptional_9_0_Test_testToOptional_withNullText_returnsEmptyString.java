package org.apache.commons.cli.help;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;

@SuppressWarnings("restriction")
public class OptionFormatter_toOptional_9_0_Test_testToOptional_withNullText_returnsEmptyString {

    // Helper: allocate an OptionFormatter instance without calling its private constructor
    private static Object allocateOptionFormatterInstance(String[] optionalDelimiters) throws Exception {
        // Use Unsafe to allocate instance without invoking constructor
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
        theUnsafeField.setAccessible(true);
        Object unsafe = theUnsafeField.get(null);
        Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
        Object instance = allocateInstance.invoke(unsafe, OptionFormatter.class);
        // Set the private final field optionalDelimiters
        Field optDelimsField = OptionFormatter.class.getDeclaredField("optionalDelimiters");
        optDelimsField.setAccessible(true);
        optDelimsField.set(instance, optionalDelimiters);
        // It's safe for these tests to leave other fields null because toOptional only uses optionalDelimiters
        return instance;
    }

    // Helper: invoke toOptional(String) reflectively
    private static String invokeToOptional(Object optionFormatterInstance, String input) throws Exception {
        Method toOptional = OptionFormatter.class.getMethod("toOptional", String.class);
        Object result = toOptional.invoke(optionFormatterInstance, input);
        return (String) result;
    }

    @Test
    public void testToOptional_withNullText_returnsEmptyString() throws Exception {
        // optionalDelimiters doesn't matter for null/empty text; set to defaults to be safe
        Object of = allocateOptionFormatterInstance(new String[] { "[", "]" });
        String result = invokeToOptional(of, null);
        assertEquals("", result);
    }




}
