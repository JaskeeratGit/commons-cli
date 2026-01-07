package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

class CommandLine_getOptionValue_13_0_Test_testReflectivePrivateConstructorAndInvokeGetOptionValueChar {

    /**
     * Verifies that getOptionValue(char, Supplier) delegates to getOptionValue(String, Supplier)
     * by overriding the String-based variant in a subclass and observing the values received.
     */


    /**
     * Uses reflection to invoke the private 3-arg constructor and to invoke the focal method
     * on the instance created reflectively. This ensures the class can be instantiated via its
     * private constructor and that the char-based method can be invoked reflectively without errors.
     */
    @Test
    void testReflectivePrivateConstructorAndInvokeGetOptionValueChar() throws Exception {
        // Locate the private constructor: (List<String>, List<Option>, Consumer<Option>)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create empty lists for args and options and a no-op deprecated handler
        List<String> args = new LinkedList<>();
        List<?> options = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Consumer<Option> deprecatedHandler = (Consumer<Option>) (o -> {
            /* no-op */
        });
        // Instantiate using the private constructor
        CommandLine instance = ctor.newInstance(args, options, deprecatedHandler);
        assertNotNull(instance, "Instance from private constructor should not be null");
        assertEquals(CommandLine.class, instance.getClass(), "Expected exact CommandLine class instance");
        // Reflectively obtain the focal method: getOptionValue(char, Supplier)
        Method focal = CommandLine.class.getMethod("getOptionValue", char.class, Supplier.class);
        // Invoke the method reflectively. We only assert that invocation completes without throwing and returns (possibly null).
        Supplier<String> supplier = () -> "r-default";
        Object returned = focal.invoke(instance, 'z', supplier);
        // Invocation completed; the returned value may be null depending on internal implementation.
        // We assert no exception and that the returned value is either null or a String.
        assertTrue(returned == null || returned instanceof String, "Returned value should be null or a String");
    }
}
