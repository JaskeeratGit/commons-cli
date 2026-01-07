package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValues_47_0_Test_returnsNull_whenDefaultIsNull {

    // Helper to create a CommandLine instance using the private constructor:
    private CommandLine createCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(java.util.List.class, java.util.List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(new LinkedList<>(), new ArrayList<>(), null);
    }


    @Test
    public void returnsNull_whenDefaultIsNull() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        OptionGroup group = new OptionGroup();
        Method m = CommandLine.class.getMethod("getParsedOptionValues", OptionGroup.class, Object[].class);
        Object result = m.invoke(cmd, group, (Object) null);
        assertNull(result, "Expected null to be returned when defaultValue is null");
    }

}
