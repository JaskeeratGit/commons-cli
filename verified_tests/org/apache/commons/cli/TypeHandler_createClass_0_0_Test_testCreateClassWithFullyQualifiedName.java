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

class TypeHandler_createClass_0_0_Test_testCreateClassWithFullyQualifiedName {

    @Test
    void testCreateClassWithFullyQualifiedName() throws Exception {
        // direct public API
        Class<?> cls = TypeHandler.createClass("java.lang.String");
        assertEquals(String.class, cls);
    }





}
