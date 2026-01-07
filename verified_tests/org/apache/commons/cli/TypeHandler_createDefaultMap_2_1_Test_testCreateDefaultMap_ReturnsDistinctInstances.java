package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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
import java.util.Objects;

class TypeHandler_createDefaultMap_2_1_Test_testCreateDefaultMap_ReturnsDistinctInstances {


    @Test
    void testCreateDefaultMap_ReturnsDistinctInstances() {
        Map<Class<?>, ?> first = TypeHandler.createDefaultMap();
        Map<Class<?>, ?> second = TypeHandler.createDefaultMap();
        // They should not be the same instance (fresh HashMap expected)
        assertNotSame(first, second, "createDefaultMap should return a new Map instance on each call");
    }

}
