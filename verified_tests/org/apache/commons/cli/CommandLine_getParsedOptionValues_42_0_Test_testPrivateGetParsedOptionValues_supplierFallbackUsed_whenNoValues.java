package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValues_42_0_Test_testPrivateGetParsedOptionValues_supplierFallbackUsed_whenNoValues {



    @Test
    public void testPrivateGetParsedOptionValues_supplierFallbackUsed_whenNoValues() throws Exception {
        CommandLine cmd = new CommandLine();
        // create an option without values
        Option opt = new Option("b", true, "option b");
        // ensure CommandLine options contains it (safety)
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Option> optionsList = (List<Option>) optionsField.get(cmd);
        optionsList.add(opt);
        // Find the private two-arg method: getParsedOptionValues(Option, Supplier)
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", Option.class, Supplier.class);
        privateMethod.setAccessible(true);
        // supplier that supplies a fallback array when option has no values
        @SuppressWarnings("unchecked")
        Supplier<String[]> supplier = () -> new String[] { "fallback" };
        Object result = null;
        try {
            result = privateMethod.invoke(cmd, opt, supplier);
        } catch (InvocationTargetException ite) {
            // unwrap and rethrow if it's a runtime exception not expected by the test
            throw (Exception) ite.getTargetException();
        }
        assertNotNull(result, "Expected supplier fallback to be returned by private method");
        assertTrue(result instanceof String[], "Expected result to be a String[]");
        String[] arr = (String[]) result;
        assertArrayEquals(new String[] { "fallback" }, arr);
    }
}
