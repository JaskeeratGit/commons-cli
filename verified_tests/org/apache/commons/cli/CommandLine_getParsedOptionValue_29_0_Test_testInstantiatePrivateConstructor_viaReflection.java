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

class CommandLine_getParsedOptionValue_29_0_Test_testInstantiatePrivateConstructor_viaReflection {

    /**
     * A small test subclass that overrides the String-variant of getParsedOptionValue to
     * capture the incoming option name and default value and return predictable results
     * depending on the type of defaultValue.
     */
    static class TestCommandLine extends CommandLine {

        String capturedOptionName;

        Object capturedDefaultValue;

        protected TestCommandLine() {
            // calls protected no-arg constructor of CommandLine
            super();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getParsedOptionValue(final String optionName, final T defaultValue) {
            this.capturedOptionName = optionName;
            this.capturedDefaultValue = defaultValue;
            // If defaultValue is null -> return null
            if (defaultValue == null) {
                return null;
            }
            // If defaultValue is an Integer, return the codepoint of the first char as Integer
            if (defaultValue instanceof Integer) {
                return (T) Integer.valueOf((int) optionName.charAt(0));
            }
            // Otherwise return a string indicating the optionName
            return (T) ("OK:" + optionName);
        }
    }





    @Test
    void testInstantiatePrivateConstructor_viaReflection() throws Exception {
        // Locate the private constructor CommandLine(List<String>, List<Option>, Consumer<Option>)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create proper arguments: args list, options list, and a no-op deprecated handler
        List<String> args = new LinkedList<>();
        args.add("arg1");
        List<Option> options = new ArrayList<>();
        Consumer<Option> handler = opt -> {
            // no-op
        };
        // Instantiate using the private constructor
        CommandLine instance = ctor.newInstance(args, options, handler);
        assertNotNull(instance, "Should be able to create a CommandLine via its private constructor reflectively");
        assertTrue(instance instanceof CommandLine);
        // For safety, use reflection to obtain the focal method but do not require a specific runtime behavior
        Method focal = CommandLine.class.getMethod("getParsedOptionValue", char.class, Object.class);
        focal.setAccessible(true);
        // Invoke on a safe TestCommandLine (to demonstrate reflection invocation)
        TestCommandLine cmd = new TestCommandLine();
        Object result = focal.invoke(cmd, 'm', "X");
        assertEquals("OK:m", result);
    }
}
