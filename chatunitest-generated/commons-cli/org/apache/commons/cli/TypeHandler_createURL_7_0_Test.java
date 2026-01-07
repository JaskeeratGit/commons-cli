package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.net.URL;
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
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TypeHandler_createURL_7_0_Test {

    @Test
    public void testCreateURL_withHttp() throws Exception {
        String input = "http://example.com/path?query=1";
        URL url = TypeHandler.createURL(input);
        assertNotNull(url, "Expected non-null URL for a valid http string");
        assertEquals("http", url.getProtocol());
        // URL#toString may normalize; compare important components
        assertEquals("example.com", url.getHost());
        assertEquals("/path", url.getPath());
        assertEquals("query=1", url.getQuery());
    }

    @Test
    public void testCreateURL_withFileScheme() throws Exception {
        String input = "file:/tmp/test-file.txt";
        URL url = TypeHandler.createURL(input);
        assertNotNull(url, "Expected non-null URL for a valid file string");
        assertEquals("file", url.getProtocol());
        // path for file URL may be absolute; ensure it ends with the filename
        assertTrue(url.getPath().endsWith("test-file.txt"));
    }

    @Test
    public void testCreateURL_invalidString_throwsParseException() {
        String invalid = "ht!tp:////:/not a url";
        assertThrows(Exception.class, () -> TypeHandler.createURL(invalid));
    }

    @Test
    public void testPrivateCreateValue_reflection_returnsURL() throws Exception {
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        String input = "http://example.org/resource";
        Object result = createValue.invoke(null, input, URL.class);
        assertNotNull(result, "Expected non-null result from private createValue for valid URL");
        assertTrue(result instanceof URL, "Expected result to be an instance of URL");
        URL url = (URL) result;
        assertEquals("example.org", url.getHost());
        assertEquals("/resource", url.getPath());
    }
}
