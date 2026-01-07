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

class TypeHandler_createClass_0_0_Test_testCreateClassWithOwnClassName {


    @Test
    void testCreateClassWithOwnClassName() throws Exception {
        // ensure it can resolve the focal class itself
        Class<?> cls = TypeHandler.createClass("org.apache.commons.cli.TypeHandler");
        assertEquals(TypeHandler.class, cls);
    }




}
