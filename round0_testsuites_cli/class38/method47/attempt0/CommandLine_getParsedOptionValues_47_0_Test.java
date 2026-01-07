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

public class CommandLine_getParsedOptionValues_47_0_Test {

    // Helper to create a CommandLine instance using the private constructor:
    private CommandLine createCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(java.util.List.class, java.util.List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(new LinkedList<>(), new ArrayList<>(), null);
    }

    @Test
    public void returnsSameDefaultArray_whenNoSelectedOption_andNonNullDefault() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        // empty group, no selected option
        OptionGroup group = new OptionGroup();
        String[] defaultValue = new String[] { "a", "b" };
        Method m = CommandLine.class.getMethod("getParsedOptionValues", OptionGroup.class, Object[].class);
        @SuppressWarnings("unchecked")
        String[] result = (String[]) m.invoke(cmd, group, defaultValue);
        // The method should delegate to the supplier that returns the provided defaultValue,
        // so the returned array instance should be the same object.
        assertSame(defaultValue, result, "Expected the returned array to be the same instance as the provided default");
    }

    @Test
    public void returnsNull_whenDefaultIsNull() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        OptionGroup group = new OptionGroup();
        Method m = CommandLine.class.getMethod("getParsedOptionValues", OptionGroup.class, Object[].class);
        Object result = m.invoke(cmd, group, (Object) null);
        assertNull(result, "Expected null to be returned when defaultValue is null");
    }

    @Test
    public void preservesTypedArrayInstance_forDifferentTypeParameter() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        OptionGroup group = new OptionGroup();
        Integer[] defaultInts = new Integer[] { 1, 2, 3 };
        Method m = CommandLine.class.getMethod("getParsedOptionValues", OptionGroup.class, Object[].class);
        @SuppressWarnings("unchecked")
        Integer[] result = (Integer[]) m.invoke(cmd, group, (Object) defaultInts);
        assertSame(defaultInts, result, "Expected same Integer[] instance to be returned");
        assertArrayEquals(new Integer[] { 1, 2, 3 }, result);
    }
}
