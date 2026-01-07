package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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
import java.util.function.Supplier;

public class CommandLine_getOptionValues_23_0_Test_returnsNullWhenNoOptionsPresent {

    @Test
    public void returnsNullWhenNoOptionsPresent() throws Exception {
        // Use reflection to invoke the protected no-arg constructor
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance();
        // With no options configured, getOptionValues(char) should return null
        String[] values = cmd.getOptionValues('a');
        assertNull(values, "Expected null when no options are present");
    }

}
