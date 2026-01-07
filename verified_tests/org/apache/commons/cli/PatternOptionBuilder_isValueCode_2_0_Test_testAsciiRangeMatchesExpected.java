package org.apache.commons.cli;

import java.lang.reflect.Method;
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

public class PatternOptionBuilder_isValueCode_2_0_Test_testAsciiRangeMatchesExpected {

    private boolean invokeIsValueCode(char c) throws Exception {
        Class<?> cls = Class.forName("org.apache.commons.cli.PatternOptionBuilder");
        Method m = cls.getDeclaredMethod("isValueCode", char.class);
        m.setAccessible(true);
        // static method, instance = null
        Object result = m.invoke(null, c);
        assertNotNull(result, "Method returned null for char: " + c);
        assertTrue(result instanceof Boolean, "Method did not return a Boolean for char: " + c);
        return (Boolean) result;
    }

    @Test
    public void testAsciiRangeMatchesExpected() throws Exception {
        // Characters that should return true according to the implementation:
        // '@' ':' '%' '+' '#' '<' '>' '*' '/' '!'
        boolean[] expected = new boolean[128];
        expected['@'] = true;
        expected[':'] = true;
        expected['%'] = true;
        expected['+'] = true;
        expected['#'] = true;
        expected['<'] = true;
        expected['>'] = true;
        expected['*'] = true;
        expected['/'] = true;
        expected['!'] = true;
        // Check entire ASCII range (0..127)
        for (int i = 0; i < 128; i++) {
            char c = (char) i;
            boolean actual = invokeIsValueCode(c);
            assertEquals(expected[i], actual, "Mismatch for ASCII code " + i + " (char='" + printable(c) + "')");
        }
    }


    private String printable(char c) {
        if (c >= 32 && c <= 126) {
            return Character.toString(c);
        } else {
            return String.format("\\u%04X", (int) c);
        }
    }
}
