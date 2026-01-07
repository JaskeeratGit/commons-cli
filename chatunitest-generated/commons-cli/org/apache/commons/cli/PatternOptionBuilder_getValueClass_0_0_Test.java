package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;

public class PatternOptionBuilder_getValueClass_0_0_Test {

    @Test
    public void testGetValueClassDirectMappings() {
        Map<Character, Class<?>> expectations = new LinkedHashMap<>();
        expectations.put('@', PatternOptionBuilder.OBJECT_VALUE);
        expectations.put(':', PatternOptionBuilder.STRING_VALUE);
        expectations.put('%', PatternOptionBuilder.NUMBER_VALUE);
        expectations.put('+', PatternOptionBuilder.CLASS_VALUE);
        expectations.put('#', PatternOptionBuilder.DATE_VALUE);
        expectations.put('<', PatternOptionBuilder.EXISTING_FILE_VALUE);
        expectations.put('>', PatternOptionBuilder.FILE_VALUE);
        expectations.put('*', PatternOptionBuilder.FILES_VALUE);
        expectations.put('/', PatternOptionBuilder.URL_VALUE);
        for (Map.Entry<Character, Class<?>> e : expectations.entrySet()) {
            char ch = e.getKey();
            Class<?> expected = e.getValue();
            Object result = PatternOptionBuilder.getValueClass(ch);
            assertNotNull(result, "Expected non-null for char: " + ch);
            assertSame(expected, result, "Unexpected class for char: " + ch);
        }
    }

    @Test
    public void testGetValueClassReflectionMappings() throws Exception {
        Method m = PatternOptionBuilder.class.getMethod("getValueClass", char.class);
        Map<Character, Class<?>> expectations = new LinkedHashMap<>();
        expectations.put('@', PatternOptionBuilder.OBJECT_VALUE);
        expectations.put(':', PatternOptionBuilder.STRING_VALUE);
        expectations.put('%', PatternOptionBuilder.NUMBER_VALUE);
        expectations.put('+', PatternOptionBuilder.CLASS_VALUE);
        expectations.put('#', PatternOptionBuilder.DATE_VALUE);
        expectations.put('<', PatternOptionBuilder.EXISTING_FILE_VALUE);
        expectations.put('>', PatternOptionBuilder.FILE_VALUE);
        expectations.put('*', PatternOptionBuilder.FILES_VALUE);
        expectations.put('/', PatternOptionBuilder.URL_VALUE);
        for (Map.Entry<Character, Class<?>> e : expectations.entrySet()) {
            char ch = e.getKey();
            Class<?> expected = e.getValue();
            Object result = m.invoke(null, ch);
            assertNotNull(result, "Expected non-null for char: " + ch);
            assertSame(expected, result, "Unexpected class (reflection) for char: " + ch);
        }
    }

    @Test
    public void testGetValueClassDefaultReturnsNull() throws Exception {
        // direct call
        Object resultDirect = PatternOptionBuilder.getValueClass('x');
        assertNull(resultDirect, "Expected null for unrecognized char via direct call");
        // reflective call
        Method m = PatternOptionBuilder.class.getMethod("getValueClass", char.class);
        Object resultReflect = m.invoke(null, 'z');
        assertNull(resultReflect, "Expected null for unrecognized char via reflection");
    }

    @Test
    public void testDeprecatedConstructorPresent() {
        // ensure deprecated constructor is present and can be invoked
        PatternOptionBuilder instance = new PatternOptionBuilder();
        assertNotNull(instance);
    }
}
