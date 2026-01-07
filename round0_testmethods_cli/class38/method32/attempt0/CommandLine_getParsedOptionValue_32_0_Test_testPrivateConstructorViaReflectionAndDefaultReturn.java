package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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

public class CommandLine_getParsedOptionValue_32_0_Test_testPrivateConstructorViaReflectionAndDefaultReturn {

    // A small subclass to override the Supplier-taking overload and observe supplier usage.
    static class TestCommandLine extends CommandLine {

        boolean supplierInvoked = false;

        Option receivedOption = null;

        Supplier<?> capturedSupplier = null;

        protected TestCommandLine() {
            // use protected no-arg constructor
            super();
        }

        @Override
        public <T> T getParsedOptionValue(final Option option, final Supplier<T> defaultValueSupplier) throws ParseException {
            // record inputs and then return the supplier result
            supplierInvoked = true;
            receivedOption = option;
            capturedSupplier = defaultValueSupplier;
            return defaultValueSupplier.get();
        }
    }


    @Test
    public void testPrivateConstructorViaReflectionAndDefaultReturn() throws Exception {
        // Obtain private constructor CommandLine(List<String>, List<Option>, Consumer<Option>) via reflection
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // create an instance using the private constructor
        CommandLine cl = ctor.newInstance(new LinkedList<String>(), new ArrayList<Option>(), (Consumer<Option>) o -> {
        });
        // create an Option and ensure getParsedOptionValue returns the supplied default value
        Option opt = new Option("x", "xlong", false, "desc");
        String defaultVal = "refDefault";
        String out = cl.getParsedOptionValue(opt, defaultVal);
        // the focal method delegates to the supplier overload; for an empty CommandLine instance,
        // the default should be propagated back. Assert that we received the default value.
        assertEquals(defaultVal, out);
    }
}
