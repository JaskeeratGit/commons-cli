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

class CommandLine_getParsedOptionValue_33_0_Test_privateOverloadedUsesProvidedSupplier_whenNoSelectedOption {

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
    void privateOverloadedUsesProvidedSupplier_whenNoSelectedOption() throws Exception {
        CommandLine cmd = newCommandLineInstance();
        OptionGroup og = new OptionGroup();
        Method privateOverloaded = CommandLine.class.getDeclaredMethod("getParsedOptionValue", OptionGroup.class, Supplier.class);
        privateOverloaded.setAccessible(true);
        // Supply a non-null default value via Supplier
        String defaultValue = "DEFAULT_VALUE";
        Object result = privateOverloaded.invoke(cmd, og, (Supplier<String>) () -> defaultValue);
        assertEquals(defaultValue, result, "Private overloaded method should return the supplier value when no option is selected");
    }

}
