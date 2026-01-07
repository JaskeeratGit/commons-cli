package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;

public class PatternOptionBuilder_getValueClass_0_0_Test_testDeprecatedConstructorPresent {




    @Test
    public void testDeprecatedConstructorPresent() {
        // ensure deprecated constructor is present and can be invoked
        PatternOptionBuilder instance = new PatternOptionBuilder();
        assertNotNull(instance);
    }
}
