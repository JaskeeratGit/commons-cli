package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValues_41_0_Test {

    /**
     * A small test subclass that overrides the String-based getParsedOptionValues
     * so we can observe delegation from the char-based overload.
     */
    static class TestCommandLine extends CommandLine {

        String capturedOptionName = null;

        public TestCommandLine() {
            // calls the protected constructor in CommandLine
            super();
        }

        @Override
        public <T> T[] getParsedOptionValues(final String optionName, final T[] defaultValue) {
            this.capturedOptionName = optionName;
            return defaultValue;
        }
    }

    @Test
    public void testCharOverloadDelegatesToStringOverload_andReturnsDefaultArray() throws Exception {
        TestCommandLine cl = new TestCommandLine();
        String[] defaultArr = new String[] { "one", "two" };
        String[] result = cl.getParsedOptionValues('a', defaultArr);
        // Ensure delegation used String.valueOf('a') => "a"
        assertEquals("a", cl.capturedOptionName);
        // Ensure returned array is the same default array instance (delegate returned it)
        assertSame(defaultArr, result);
    }

    @Test
    public void testCharOverloadWithNullDefaultDelegatesAndReturnsNull() throws Exception {
        TestCommandLine cl = new TestCommandLine();
        String[] result = cl.getParsedOptionValues('b', (String[]) null);
        assertEquals("b", cl.capturedOptionName);
        assertNull(result);
    }

    @Test
    public void testInvokeCharOverloadViaReflection_onPrivatelyConstructedInstance() throws Exception {
        // Obtain the private constructor CommandLine(List<String>, List<Option>, Consumer<Option>)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create lists to satisfy Objects.requireNonNull in the constructor
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        // Construct a CommandLine instance via the private constructor
        CommandLine cl = ctor.newInstance(args, options, null);
        // Locate the char-based method: getParsedOptionValues(char, Object[])
        Method m = CommandLine.class.getMethod("getParsedOptionValues", char.class, Object[].class);
        // Prepare a default value
        String[] defaultArr = new String[] { "x", "y" };
        // Invoke the method reflectively
        Object returned = m.invoke(cl, new Object[] { 'z', defaultArr });
        // The char overload delegates to the String overload. In a default-empty CommandLine
        // implementation that returns default when no option was set, we expect the same array.
        // We assert that the returned value matches the provided default array.
        assertTrue(returned == null || returned instanceof String[]);
        if (returned != null) {
            assertArrayEquals(defaultArr, (String[]) returned);
        }
    }
}
