package org.apache.commons.cli;

import java.io.File;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

public class TypeHandler_createFile_3_0_Test_testCreateFile_withEmptyString_returnsFileWithEmptyPath {



    @Test
    public void testCreateFile_withEmptyString_returnsFileWithEmptyPath() {
        String path = "";
        File file = TypeHandler.createFile(path);
        assertNotNull(file);
        assertEquals(new File(path).getPath(), file.getPath());
    }
}
