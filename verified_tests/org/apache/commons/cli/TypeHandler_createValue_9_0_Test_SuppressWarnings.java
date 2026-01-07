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
public class TypeHandler_createValue_9_0_Test_SuppressWarnings {


    @Test
    @SuppressWarnings("deprecation")
    public void testCreateValue_withNonClass_throwsClassCastException() {
        // Passing a non-Class object should cause a ClassCastException when the method casts.
        assertThrows(ClassCastException.class, () -> TypeHandler.createValue("value", new Object()));
    }

}
