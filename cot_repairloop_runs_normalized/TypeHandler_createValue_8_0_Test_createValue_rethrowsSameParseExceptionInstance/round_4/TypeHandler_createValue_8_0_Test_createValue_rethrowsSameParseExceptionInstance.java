package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    private TypeHandler originalDefault;
    private Map<Class<?>, Converter<?, ? extends Throwable>> originalConverterMapCopy;

    @BeforeEach
    void saveOriginalDefault() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        originalDefault = (TypeHandler) f.get(null);

        Field cm = TypeHandler.class.getDeclaredField("converterMap");
        cm.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cm.get(originalDefault);
        // make a defensive copy of the original map to restore later
        originalConverterMapCopy = new HashMap<>(map);
    }

    @AfterEach
    void restoreOriginalDefault() throws Exception {
        // restore the original converter map contents
        Field cm = TypeHandler.class.getDeclaredField("converterMap");
        cm.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cm.get(originalDefault);
        map.clear();
        map.putAll(originalConverterMapCopy);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void removeFinalAndSet(Field field, Object value) throws Exception {
        field.setAccessible(true);
        // remove final modifier
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some JVMs (like newer ones) may not allow changing modifiers; still try set()
        }
        field.set(null, value);
    }

    @SuppressWarnings("unchecked")
    @Test
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");

        // replace the Long converter in the existing DEFAULT instance's converterMap
        Field cm = TypeHandler.class.getDeclaredField("converterMap");
        cm.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cm.get(originalDefault);

        map.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });

        ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }
}
