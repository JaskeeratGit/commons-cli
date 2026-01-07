package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.Supplier;

/**
 * Unit tests for CommandLine#getOptionValues(String)
 */
public class CommandLine_getOptionValues_26_0_Test_testGetOptionValues_DeprecatedHandlerInvoked {

    // Helper: create a CommandLine using the private constructor (List<String>, List<Option>, Consumer<Option>)
    private static CommandLine createCommandLineWithOptions(List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // args can be empty list
        return ctor.newInstance(new LinkedList<String>(), options, deprecatedHandler);
    }

    // Helper: add values to an Option instance using reflection to call addValueForProcessing(String)
    private static void addValuesToOption(Option option, String... values) throws Exception {
        // Try the known method name used in commons-cli implementations
        Method addMethod = null;
        try {
            addMethod = option.getClass().getDeclaredMethod("addValueForProcessing", String.class);
        } catch (NoSuchMethodException e) {
            // fallback: try "addValue" (some versions might differ)
            try {
                addMethod = option.getClass().getDeclaredMethod("addValue", String.class);
            } catch (NoSuchMethodException ex) {
                // give up and throw original
                throw e;
            }
        }
        addMethod.setAccessible(true);
        for (String v : values) {
            addMethod.invoke(option, v);
        }
    }




    @Test
    public void testGetOptionValues_DeprecatedHandlerInvoked() throws Exception {
        Option opt = new Option("d", "deprecated", false, "deprecated option");
        addValuesToOption(opt, "x");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        AtomicBoolean handlerInvoked = new AtomicBoolean(false);
        Consumer<Option> handler = (o) -> handlerInvoked.set(true);
        // create command line with custom deprecated handler via private constructor
        CommandLine cmd = createCommandLineWithOptions(opts, handler);
        // mark option as deprecated via reflection (Option.isDeprecated() should reflect that)
        // We try to set the deprecated flag if available, otherwise attempt to call setDeprecated method.
        try {
            Method setDeprecated = opt.getClass().getDeclaredMethod("setDeprecated", boolean.class);
            setDeprecated.setAccessible(true);
            setDeprecated.invoke(opt, true);
        } catch (NoSuchMethodException ignored) {
            // try to access a field named "deprecated"
            try {
                java.lang.reflect.Field f = opt.getClass().getDeclaredField("deprecated");
                f.setAccessible(true);
                f.set(opt, Boolean.TRUE);
            } catch (NoSuchFieldException | IllegalAccessException ignored2) {
                // If neither is available, attempt to construct Option as deprecated via constructor
                // (some Option constructors accept a deprecated flag; but already used above).
            }
        }
        String[] result = cmd.getOptionValues("d");
        assertNotNull(result);
        assertArrayEquals(new String[] { "x" }, result);
        assertTrue(handlerInvoked.get(), "Deprecated handler should have been invoked for deprecated option");
    }

}
