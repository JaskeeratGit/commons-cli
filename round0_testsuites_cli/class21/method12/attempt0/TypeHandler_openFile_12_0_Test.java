package org.apache.commons.cli;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.TypeHandler;
import org.junit.jupiter.api.io.TempDir;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TypeHandler_openFile_12_0_Test {

    @Test
    void testOpenFile_withExistingFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.write(file, "hello".getBytes());
        try (FileInputStream fis = TypeHandler.openFile(file.toString())) {
            assertNotNull(fis, "FileInputStream should not be null for an existing file");
            int first = fis.read();
            assertEquals((int) 'h', first, "First byte should match the file content");
        }
    }

    @Test
    void testOpenFile_withNonExistentFile_throwsParseException() {
        String path = "nonexistent-" + System.nanoTime() + ".txt";
        assertThrows(ParseException.class, () -> TypeHandler.openFile(path));
    }

    @Test
    void testCreateValue_privateMethod_viaReflection_withExistingFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("reflect.txt");
        Files.write(file, "reflect".getBytes());
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        Object result = createValue.invoke(null, file.toString(), FileInputStream.class);
        assertNotNull(result);
        assertTrue(result instanceof FileInputStream);
        ((FileInputStream) result).close();
    }

    @Test
    void testCreateValue_privateMethod_viaReflection_withNull_throwsParseExceptionWrapped() throws Exception {
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> createValue.invoke(null, (String) null, FileInputStream.class));
        assertNotNull(ite.getCause());
        assertTrue(ite.getCause() instanceof ParseException, "Cause should be a ParseException when input is null");
    }
}
