package org.apache.commons.cli;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

public class PatternOptionBuilder_getValueType_1_0_Test_testAllBranchesVisited {

    private static Method getValueTypeMethod() throws Exception {
        Method m = PatternOptionBuilder.class.getDeclaredMethod("getValueType", char.class);
        m.setAccessible(true);
        return m;
    }

    static Stream<Arguments> valueTypeProvider() {
        return Stream.of(Arguments.of('@', PatternOptionBuilder.OBJECT_VALUE), Arguments.of(':', PatternOptionBuilder.STRING_VALUE), Arguments.of('%', PatternOptionBuilder.NUMBER_VALUE), Arguments.of('+', PatternOptionBuilder.CLASS_VALUE), Arguments.of('#', PatternOptionBuilder.DATE_VALUE), Arguments.of('<', PatternOptionBuilder.EXISTING_FILE_VALUE), Arguments.of('>', PatternOptionBuilder.FILE_VALUE), Arguments.of('*', PatternOptionBuilder.FILES_VALUE), Arguments.of('/', PatternOptionBuilder.URL_VALUE));
    }

    @ParameterizedTest
    @MethodSource("valueTypeProvider")
    void testKnownValueCodes(char input, Class<?> expected) throws Exception {
        Method m = getValueTypeMethod();
        Object result = m.invoke(null, input);
        assertSame(expected, result);
    }


    @Test
    void testAllBranchesVisited() throws Exception {
        Method m = getValueTypeMethod();
        char[] codes = new char[] { '@', ':', '%', '+', '#', '<', '>', '*', '/' };
        for (char c : codes) {
            Object r = m.invoke(null, c);
            assertNotNull(r, "Expected non-null for code: " + c);
        }
    }
}
