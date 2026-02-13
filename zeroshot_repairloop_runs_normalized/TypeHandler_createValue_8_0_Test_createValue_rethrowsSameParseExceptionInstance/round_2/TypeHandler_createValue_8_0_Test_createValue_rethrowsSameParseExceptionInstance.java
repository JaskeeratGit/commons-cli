package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import sun.misc.Unsafe;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    private TypeHandler originalDefault;

    @BeforeEach
    void saveOriginalDefault() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        originalDefault = (TypeHandler) f.get(null);
    }

    @AfterEach
    void restoreOriginalDefault() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        removeFinalAndSet(f, originalDefault);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void removeFinalAndSet(Field field, Object value) throws Exception {
        field.setAccessible(true);
        // remove final modifier if possible
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some JVMs may not allow changing modifiers; ignore and try alternate approach
        }

        // First try the normal reflective set
        try {
            field.set(null, value);
            return;
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // Fall through to Unsafe approach
        }

        // Use Unsafe to set static final field
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            Object staticBase = unsafe.staticFieldBase(field);
            long offset = unsafe.staticFieldOffset(field);
            unsafe.putObject(staticBase, offset, value);
        } catch (Exception ex) {
            // If Unsafe approach fails, rethrow original problem as IllegalAccessException to match previous behavior
            throw new IllegalAccessException("Failed to set static final field " + field.getName() + ": " + ex);
        }
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
        removeFinalAndSet(f, custom);
        ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }
}
