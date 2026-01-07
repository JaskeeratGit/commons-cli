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

public class CommandLine_getParsedOptionValues_42_0_Test_testGetParsedOptionValues_withValues_returnsValues {


    @Test
    public void testGetParsedOptionValues_withValues_returnsValues() throws Exception {
        CommandLine cmd = new CommandLine();
        // create an option that accepts arguments and add values to it
        Option opt = new Option("a", true, "option a");
        opt.getValuesList().add("one");
        opt.getValuesList().add("two");
        // ensure the CommandLine knows about this option (some implementations check membership)
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Option> optionsList = (List<Option>) optionsField.get(cmd);
        optionsList.add(opt);
        // call the public method (which delegates to the two-arg private method with a null supplier)
        String[] parsed = cmd.getParsedOptionValues(opt);
        // Accept either null or a populated array (some implementations may return null if no parsing done).
        assertNotNull(parsed, "Expected parsed values to be non-null for an option with values");
        assertArrayEquals(new String[] { "one", "two" }, parsed);
    }

}
