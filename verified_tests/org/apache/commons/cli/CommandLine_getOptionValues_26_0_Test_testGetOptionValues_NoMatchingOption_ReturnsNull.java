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
public class CommandLine_getOptionValues_26_0_Test_testGetOptionValues_NoMatchingOption_ReturnsNull {

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
    public void testGetOptionValues_NoMatchingOption_ReturnsNull() throws Exception {
        // create a command line with one option that doesn't match the query
        Option opt = new Option("a", "alpha", false, "desc");
        addValuesToOption(opt, "v1");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts, null);
        // no option 'b'
        String[] result = cmd.getOptionValues("b");
        assertNull(result, "Expected null when no matching option present");
    }



}
