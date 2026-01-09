package org.apache.commons.cli;

import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    private TypeHandler defaultHandler;
    // reference to the internal map held by the DEFAULT TypeHandler instance
    @SuppressWarnings("rawtypes")
    private Map converterMap;
    // backup of original entries so we can restore after the test
    private Map<Object, Object> originalEntries;

    @BeforeEach
    void saveOriginalDefaultAndConverters() throws Exception {
        Field f = TypeHandler.class.getDeclaredField("DEFAULT");
        f.setAccessible(true);
        defaultHandler = (TypeHandler) f.get(null);

        // Find the first Map-typed field inside TypeHandler instance and treat it as the converters map
        Field mapField = null;
        for (Field fld : TypeHandler.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(fld.getType())) {
                mapField = fld;
                break;
            }
        }
        assertNotNull(mapField, "Could not find a Map field inside TypeHandler to modify for testing");
        mapField.setAccessible(true);
        // get the map instance from the DEFAULT handler and back it up
        @SuppressWarnings("unchecked")
        Map<Object, Object> mapInstance = (Map<Object, Object>) mapField.get(defaultHandler);
        assertNotNull(mapInstance, "Converters map inside DEFAULT TypeHandler is null");
        // keep references for test and restore
        this.converterMap = mapInstance;
        this.originalEntries = new HashMap<>(mapInstance);
    }

    @AfterEach
    void restoreOriginalConverters() throws Exception {
        if (converterMap != null && originalEntries != null) {
            converterMap.clear();
            converterMap.putAll(originalEntries);
        }
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");

        // Use an anonymous Converter implementation so we can declare that it throws ParseException
        Converter<Long, ParseException> throwingConverter = new Converter<Long, ParseException>() {
            @Override
            public Long apply(String s) throws ParseException {
                throw expected;
            }
        };

        // Put our converter into the existing DEFAULT TypeHandler's converters map
        converterMap.put(Long.class, throwingConverter);

        ParseException thrown = assertThrows(ParseException.class,
                () -> TypeHandler.createValue("10", Long.class));
        // ensure it's the exact same instance thrown by the converter
        assertSame(expected, thrown);
    }
}
