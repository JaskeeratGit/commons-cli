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

class TypeHandler_createValue_8_0_Test_createValue_propagatesUnsupportedOperationException {

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
    void createValue_propagatesUnsupportedOperationException() throws Exception {
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Double.class, (Converter<Double, RuntimeException>) s -> {
            throw new UnsupportedOperationException("not supported");
        });
        TypeHandler custom = new TypeHandler(map);
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        removeFinalAndSet(f, custom);
        assertThrows(UnsupportedOperationException.class, () -> TypeHandler.createValue("1.2", Double.class));
    }

}
