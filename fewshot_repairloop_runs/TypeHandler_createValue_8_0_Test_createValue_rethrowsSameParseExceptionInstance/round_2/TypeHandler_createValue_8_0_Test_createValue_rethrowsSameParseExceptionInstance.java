package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    private TypeHandler originalDefault;
    private Map<Class<?>, Converter<?, ? extends Throwable>> originalMap;

    @BeforeEach
    void saveOriginalDefault() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        originalDefault = (TypeHandler) f.get(null);

        Field cmf = TypeHandler.class.getDeclaredField("converterMap");
        cmf.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cmf.get(originalDefault);
        originalMap = new HashMap<>(map);
    }

    @AfterEach
    void restoreOriginalDefault() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        TypeHandler defaultInst = (TypeHandler) f.get(null);

        Field cmf = TypeHandler.class.getDeclaredField("converterMap");
        cmf.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> map =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cmf.get(defaultInst);

        map.clear();
        map.putAll(originalMap);
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

    @Test
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });
        TypeHandler custom = new TypeHandler(map);

        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        TypeHandler defaultInst = (TypeHandler) f.get(null);

        Field cmf = TypeHandler.class.getDeclaredField("converterMap");
        cmf.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Converter<?, ? extends Throwable>> defaultMap =
                (Map<Class<?>, Converter<?, ? extends Throwable>>) cmf.get(defaultInst);

        // install the failing converter into the existing default map
        defaultMap.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });

        ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }
}
