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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Generated tests for TypeHandler.createValue(String, Object) (deprecated overload).
 * Tests use reflection to reach the private overload and exercise casting behavior.
 */
public class TypeHandler_createValue_9_0_Test_testPrivateCreateValue_viaReflection_stringClass {



    @Test
    public void testPrivateCreateValue_viaReflection_stringClass() throws Throwable {
        // Use reflection to invoke the private static createValue(String, Class) overload directly.
        Method privateCreate = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        privateCreate.setAccessible(true);
        Object invoked = privateCreate.invoke(null, "reflect", String.class);
        assertEquals("reflect", invoked);
    }
}
