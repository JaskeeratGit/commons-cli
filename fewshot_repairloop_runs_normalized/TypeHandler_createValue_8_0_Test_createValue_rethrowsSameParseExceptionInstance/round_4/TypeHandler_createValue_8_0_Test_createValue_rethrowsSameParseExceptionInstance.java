package org.apache.commons.cli;

import java.util.HashMap;
import java.util.Map;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeHandler_createValue_8_0_Test_createValue_rethrowsSameParseExceptionInstance {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    void createValue_rethrowsSameParseExceptionInstance() throws Exception {
        final ParseException expected = new ParseException("expected");
        Map<Class<?>, Converter<?, ? extends Throwable>> map = new HashMap<>();
        map.put(Long.class, (Converter<Long, ParseException>) s -> {
            throw expected;
        });
        TypeHandler custom = new TypeHandler(map);

        try (MockedStatic<TypeHandler> mocked = Mockito.mockStatic(TypeHandler.class)) {
            mocked.when(TypeHandler::getDefault).thenReturn(custom);
            ParseException thrown = assertThrows(ParseException.class, () -> TypeHandler.createValue("10", Long.class));
            // ensure it's the exact same instance thrown by the converter
            assertSame(expected, thrown);
        }
    }
}
