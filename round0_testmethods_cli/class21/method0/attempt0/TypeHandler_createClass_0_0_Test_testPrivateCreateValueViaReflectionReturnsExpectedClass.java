package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
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

class TypeHandler_createClass_0_0_Test_testPrivateCreateValueViaReflectionReturnsExpectedClass {




    @Test
    void testPrivateCreateValueViaReflectionReturnsExpectedClass() throws Exception {
        Method createValue = TypeHandler.class.getDeclaredMethod("createValue", String.class, Class.class);
        createValue.setAccessible(true);
        Object result = createValue.invoke(null, "java.lang.Integer", Class.class);
        assertNotNull(result);
        assertTrue(result instanceof Class);
        assertEquals(Integer.class, result);
    }


}
