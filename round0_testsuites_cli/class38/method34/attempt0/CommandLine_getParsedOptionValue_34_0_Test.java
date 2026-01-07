package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
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

class CommandLine_getParsedOptionValue_34_0_Test {

    // A small subclass to allow interception of the String overload.
    // This relies on the overload in CommandLine being non-final/non-private so it can be overridden.
    static class TestCommandLine extends CommandLine {

        public TestCommandLine() {
            super();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getParsedOptionValue(final String opt, final Supplier<T> defaultValue) {
            // return a distinct value so we can detect delegation
            return (T) ("parsed-" + opt);
        }
    }

    @Test
    void testNullOptionGroup_returnsDefault() throws Exception {
        CommandLine cmd = new TestCommandLine();
        Supplier<String> supplier = () -> "defaultValue";
        String result = cmd.getParsedOptionValue(null, supplier);
        assertEquals("defaultValue", result);
    }

    @Test
    void testNotSelectedOptionGroup_returnsDefault() throws Exception {
        CommandLine cmd = new TestCommandLine();
        // by default not selected
        OptionGroup og = new OptionGroup();
        assertFalse(og.isSelected());
        Supplier<Integer> supplier = () -> 123;
        Integer result = cmd.getParsedOptionValue(og, supplier);
        assertEquals(Integer.valueOf(123), result);
    }

    @Test
    void testSelectedOptionGroup_delegatesToStringOverload() throws Exception {
        TestCommandLine cmd = new TestCommandLine();
        OptionGroup og = new OptionGroup();
        // set the private 'selected' field on OptionGroup to simulate selection
        Field selectedField = OptionGroup.class.getDeclaredField("selected");
        selectedField.setAccessible(true);
        selectedField.set(og, "optX");
        assertTrue(og.isSelected());
        Supplier<String> supplier = () -> "fallback";
        String result = cmd.getParsedOptionValue(og, supplier);
        // Our TestCommandLine overrides getParsedOptionValue(String,Supplier) to return "parsed-"+opt
        assertEquals("parsed-optX", result);
    }

    @Test
    void testConstructCommandLineUsingPrivateConstructor_viaReflection() throws Exception {
        // Use reflection to invoke the private constructor CommandLine(List,String,Consumer)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        Consumer<Option> handler = null;
        CommandLine cmd = ctor.newInstance(args, options, handler);
        Supplier<Long> supplier = () -> 999L;
        Long result = cmd.getParsedOptionValue(null, supplier);
        assertEquals(Long.valueOf(999L), result);
    }
}
