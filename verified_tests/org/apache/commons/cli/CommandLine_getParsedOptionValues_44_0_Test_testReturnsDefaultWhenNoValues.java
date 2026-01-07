package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
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

public class CommandLine_getParsedOptionValues_44_0_Test_testReturnsDefaultWhenNoValues {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    // Helper to set the private 'values' field inside Option
    private void setOptionValues(Option option, List<String> values) throws Exception {
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        // ensure it's a mutable list instance like ArrayList because some code may expect that
        valuesField.set(option, new ArrayList<>(values));
    }


    @Test
    public void testReturnsDefaultWhenNoValues() throws Exception {
        // prepare option with no values
        Option opt = new Option("o", true, "desc");
        setOptionValues(opt, Collections.emptyList());
        // create command line containing the option
        CommandLine cmd = createCommandLine(new LinkedList<>(), new ArrayList<>(Collections.singletonList(opt)), null);
        // default value provided
        String[] defaultVal = new String[] { "d1", "d2" };
        String[] result = cmd.getParsedOptionValues(opt, defaultVal);
        // should return the provided default when option has no values
        assertNotNull(result);
        assertArrayEquals(defaultVal, result);
        // Also test the supplier overload directly returning the default via reflection
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", Option.class, Supplier.class);
        privateMethod.setAccessible(true);
        @SuppressWarnings("unchecked")
        String[] reflectResult = (String[]) privateMethod.invoke(cmd, opt, (Supplier<String[]>) () -> defaultVal);
        assertArrayEquals(defaultVal, reflectResult);
    }

}
