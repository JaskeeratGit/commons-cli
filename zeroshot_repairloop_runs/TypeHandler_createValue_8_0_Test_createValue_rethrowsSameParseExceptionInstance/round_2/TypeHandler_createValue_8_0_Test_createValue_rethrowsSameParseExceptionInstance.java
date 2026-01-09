package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    private Map<Class<?>, Converter<?, ? extends Throwable>> defaultMap;
    private Object originalLongConverter;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @BeforeEach
    void backupDefaultMapEntry() throws Exception {
        // obtain the DEFAULT instance
        TypeHandler defaultInstance = TypeHandler.getDefault();
        // access its private converterMap field
        Field mapField = TypeHandler.class.getDeclaredField("converterMap");
        mapField.setAccessible(true);
        defaultMap = (Map) mapField.get(defaultInstance);
        // save original Long converter (if any)
        originalLongConverter = defaultMap.get(Long.class);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @AfterEach
    void restoreDefaultMapEntry() throws Exception {
        if (defaultMap != null) {
            if (originalLongConverter != null) {
                defaultMap.put(Long.class, (Converter) originalLongConverter);
            } else {
                defaultMap.remove(Long.class);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");
        // replace the Long converter in the default map to throw the specific ParseException instance
        defaultMap.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });

        ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }
}
