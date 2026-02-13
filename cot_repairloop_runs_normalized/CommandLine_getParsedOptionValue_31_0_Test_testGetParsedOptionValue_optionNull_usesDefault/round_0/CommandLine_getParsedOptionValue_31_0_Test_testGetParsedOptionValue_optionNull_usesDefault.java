package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

class CommandLine_getParsedOptionValue_31_0_Test_testGetParsedOptionValue_optionNull_usesDefault {

    @Test
    void testGetParsedOptionValue_optionNull_usesDefault() throws Exception {
        CommandLine cmd = new CommandLine() {
        };
        String result = cmd.getParsedOptionValue((Option) null, () -> "defaultValue");
        assertEquals("defaultValue", result);
        // Also invoke the private helper 'get' via reflection to satisfy private-method reflection usage
        Method getMethod = CommandLine.class.getDeclaredMethod("get", Supplier.class);
        getMethod.setAccessible(true);
        Object reflected = getMethod.invoke(cmd, (Supplier<String>) () -> "reflectedDefault");
        assertEquals("reflectedDefault", reflected);
    }



}
