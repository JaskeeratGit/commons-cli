package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

class TypeHandler_createValue_8_0_Test {

    private TypeHandler defaultInstance;

    private Map<Class<?>, Converter<?, ? extends Throwable>> originalConverterMapContents;

    @BeforeEach
    void saveOriginalDefault() throws Exception {
        // get the default singleton instance via the public API
        defaultInstance = TypeHandler.getDefault();
        // grab its internal converterMap and save a copy of the contents to restore later
        Field convField = TypeHandler.class.getDeclaredField("converterMap");
        convField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> currentMap = (Map<Class<?>, Converter<?, ? extends Throwable>>) convField.get(defaultInstance);
        originalConverterMapContents = new HashMap<>(currentMap);
    }

    @AfterEach
    void restoreOriginalDefault() throws Exception {
        // restore the original converter map contents into the same default instance
        Field convField = TypeHandler.class.getDeclaredField("converterMap");
        convField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> currentMap = (Map<Class<?>, Converter<?, ? extends Throwable>>) convField.get(defaultInstance);
        currentMap.clear();
        currentMap.putAll(originalConverterMapContents);
    }

    @Test
    void createValue_returnsConvertedValue() throws Exception {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        // converter that appends "-ok" to incoming string
        map.put(String.class, (Converter<String, RuntimeException>) s -> s + "-ok");
        TypeHandler custom = new TypeHandler(map);
        setDefault(custom);
        String result = TypeHandler.createValue("input", String.class);
        assertEquals("input-ok", result);
    }

    @Test
    void createValue_returnsNullWhenConverterReturnsNull() throws Exception {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(String.class, (Converter<String, RuntimeException>) s -> null);
        TypeHandler custom = new TypeHandler(map);
        setDefault(custom);
        assertNull(TypeHandler.createValue("anything", String.class));
    }

    @Test
    void createValue_throwsParseException_whenConverterThrowsRuntimeException() throws Exception {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Integer.class, (Converter<Integer, RuntimeException>) s -> {
            throw new RuntimeException("boom");
        });
        TypeHandler custom = new TypeHandler(map);
        setDefault(custom);
        assertThrows(ParseException.class, () -> TypeHandler.createValue("1", Integer.class));
    }

    @Test
    void createValue_propagatesUnsupportedOperationException() throws Exception {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Double.class, (Converter<Double, RuntimeException>) s -> {
            throw new UnsupportedOperationException("not supported");
        });
        TypeHandler custom = new TypeHandler(map);
        setDefault(custom);
        assertThrows(UnsupportedOperationException.class, () -> TypeHandler.createValue("1.2", Double.class));
    }

    @Test
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });
        TypeHandler custom = new TypeHandler(map);
        setDefault(custom);
        ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }

    // helper that injects the replacement TypeHandler's converter map into the default singleton
    private void setDefault(TypeHandler replacement) throws Exception {
        Field convField = TypeHandler.class.getDeclaredField("converterMap");
        convField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> replacementMap = (Map<Class<?>, Converter<?, ? extends Throwable>>) convField.get(replacement);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> currentMap = (Map<Class<?>, Converter<?, ? extends Throwable>>) convField.get(defaultInstance);
        currentMap.clear();
        currentMap.putAll(replacementMap);
    }
}
