package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class TypeHandler_createDate_1_0_Test_testCreateValueUncheckedMethodSignature_isPrivateAndStatic {

    private static Method createValueUncheckedMethod;

    @BeforeAll
    static void setUpReflection() throws Exception {
        // locate the private static method createValueUnchecked(String, Class)
        createValueUncheckedMethod = TypeHandler.class.getDeclaredMethod("createValueUnchecked", String.class, Class.class);
        createValueUncheckedMethod.setAccessible(true);
    }

    @Test
    void testCreateValueUncheckedMethodSignature_isPrivateAndStatic() {
        int mods = createValueUncheckedMethod.getModifiers();
        assertTrue(Modifier.isPrivate(mods), "createValueUnchecked should be private");
        assertTrue(Modifier.isStatic(mods), "createValueUnchecked should be static");
        Class<?>[] params = createValueUncheckedMethod.getParameterTypes();
        assertEquals(2, params.length);
        assertEquals(String.class, params[0]);
        assertEquals(Class.class, params[1]);
    }


}
