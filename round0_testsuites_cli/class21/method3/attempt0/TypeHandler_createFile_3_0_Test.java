package org.apache.commons.cli;

import java.io.File;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

public class TypeHandler_createFile_3_0_Test {

    @Test
    public void testCreateFile_withCustomConverterOverride() throws Exception {
        Class<?> clazz = TypeHandler.class;
        // Save original DEFAULT to restore later
        Field defaultField = clazz.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        Object originalDefault = defaultField.get(null);
        // Make it possible to replace the final static field
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(defaultField, defaultField.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some JVMs disallow access to modifiers field; proceed without clearing final flag
        }
        try {
            // Prepare a proxy instance for the Converter interface that always returns a specific File
            Class<?> converterInterface = Class.forName("org.apache.commons.cli.Converter");
            InvocationHandler handler = (proxy, method, args) -> {
                if ("convert".equals(method.getName())) {
                    // Return a distinguishable File instance
                    return new File("/tmp/custom-converter-file");
                }
                // For toString/hashCode/equals, delegate to defaults
                if ("toString".equals(method.getName()))
                    return proxy.getClass().getName();
                if ("hashCode".equals(method.getName()))
                    return System.identityHashCode(proxy);
                if ("equals".equals(method.getName()))
                    return proxy == args[0];
                return null;
            };
            Object converterProxy = Proxy.newProxyInstance(converterInterface.getClassLoader(), new Class<?>[] { converterInterface }, handler);
            // Build a map mapping File.class to our proxy converter
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(File.class, converterProxy);
            // Create a new TypeHandler instance using the Map constructor (via reflection)
            Constructor<?> ctor = clazz.getConstructor(Map.class);
            Object customHandler = ctor.newInstance(map);
            // Replace DEFAULT with customHandler
            defaultField.set(null, customHandler);
            // Invoke the static createFile method and verify it returns the File from our converter
            File result = TypeHandler.createFile("ignored-input");
            assertNotNull(result, "createFile should not return null when a converter is present");
            assertEquals("/tmp/custom-converter-file", result.getPath(), "Expected the File returned by custom converter");
        } finally {
            // Restore original DEFAULT to avoid side effects on other tests
            try {
                defaultField.set(null, originalDefault);
            } catch (IllegalAccessException e) {
                // If restore fails, rethrow as test failure
                throw new RuntimeException("Failed to restore original DEFAULT field", e);
            }
        }
    }

    @Test
    public void testCreateFile_withDefaultBehavior_returnsFileForString() {
        // Basic behavior: createFile should produce a File pointing to the same path string
        String path = "some/relative/path.txt";
        File file = TypeHandler.createFile(path);
        assertNotNull(file);
        assertEquals(new File(path).getPath(), file.getPath());
    }

    @Test
    public void testCreateFile_withEmptyString_returnsFileWithEmptyPath() {
        String path = "";
        File file = TypeHandler.createFile(path);
        assertNotNull(file);
        assertEquals(new File(path).getPath(), file.getPath());
    }
}
