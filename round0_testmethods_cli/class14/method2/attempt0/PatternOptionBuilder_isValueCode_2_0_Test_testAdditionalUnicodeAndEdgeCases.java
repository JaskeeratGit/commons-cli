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

public class PatternOptionBuilder_isValueCode_2_0_Test_testAdditionalUnicodeAndEdgeCases {

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
    public void testAdditionalUnicodeAndEdgeCases() throws Exception {
        // Some non-ASCII and edge cases that should be false
        assertFalse(invokeIsValueCode('\u0000'), "\\u0000 should be false");
        assertFalse(invokeIsValueCode('\uFFFF'), "\\uFFFF should be false");
        assertFalse(invokeIsValueCode('A'), "Uppercase letter should be false");
        assertFalse(invokeIsValueCode('z'), "Lowercase letter should be false");
        assertFalse(invokeIsValueCode('0'), "Digit should be false");
        // Verify positives explicitly
        assertTrue(invokeIsValueCode('@'), "Expected '@' to be a value code");
        assertTrue(invokeIsValueCode(':'), "Expected ':' to be a value code");
        assertTrue(invokeIsValueCode('%'), "Expected '%' to be a value code");
        assertTrue(invokeIsValueCode('+'), "Expected '+' to be a value code");
        assertTrue(invokeIsValueCode('#'), "Expected '#' to be a value code");
        assertTrue(invokeIsValueCode('<'), "Expected '<' to be a value code");
        assertTrue(invokeIsValueCode('>'), "Expected '>' to be a value code");
        assertTrue(invokeIsValueCode('*'), "Expected '*' to be a value code");
        assertTrue(invokeIsValueCode('/'), "Expected '/' to be a value code");
        assertTrue(invokeIsValueCode('!'), "Expected '!' to be a value code");
    }

    private String printable(char c) {
        if (c >= 32 && c <= 126) {
            return Character.toString(c);
        } else {
            return String.format("\\u%04X", (int) c);
        }
    }
}
