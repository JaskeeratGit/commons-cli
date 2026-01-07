package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unit tests for TypeHandler.createFiles(String)
 */
public class TypeHandler_createFiles_4_0_Test_reflectionInvoke_shouldThrowInvocationTargetException_withCauseUnsupportedOperationException {



    @Test
    public void reflectionInvoke_shouldThrowInvocationTargetException_withCauseUnsupportedOperationException() throws Exception {
        Method m = TypeHandler.class.getDeclaredMethod("createFiles", String.class);
        m.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> m.invoke(null, "any"));
        Throwable cause = ite.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Not yet implemented", cause.getMessage());
    }

}
