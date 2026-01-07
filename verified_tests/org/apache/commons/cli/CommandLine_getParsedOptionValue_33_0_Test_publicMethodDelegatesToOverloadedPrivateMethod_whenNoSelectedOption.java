package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

class CommandLine_getParsedOptionValue_33_0_Test_publicMethodDelegatesToOverloadedPrivateMethod_whenNoSelectedOption {

    // Helper to create a CommandLine instance via the private constructor
    private CommandLine newCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Consumer<Option> deprecatedHandler = (Consumer<Option>) (o -> {
            // no-op
        });
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    @Test
    void publicMethodDelegatesToOverloadedPrivateMethod_whenNoSelectedOption() throws Exception {
        CommandLine cmd = newCommandLineInstance();
        OptionGroup og = new OptionGroup();
        Method publicMethod = CommandLine.class.getMethod("getParsedOptionValue", OptionGroup.class);
        Method privateOverloaded = CommandLine.class.getDeclaredMethod("getParsedOptionValue", OptionGroup.class, Supplier.class);
        privateOverloaded.setAccessible(true);
        // Invoke public method
        Object publicResult = publicMethod.invoke(cmd, og);
        // Invoke private overloaded method with a Supplier that returns null
        Object privateResult = privateOverloaded.invoke(cmd, og, (Supplier<Object>) () -> null);
        // They should behave the same (public delegates to overloaded with () -> null)
        assertEquals(privateResult, publicResult, "Public method should delegate to overloaded private method with null supplier");
    }


}
