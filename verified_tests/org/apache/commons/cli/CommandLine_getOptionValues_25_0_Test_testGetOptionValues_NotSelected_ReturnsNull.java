package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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

/**
 * JUnit 5 tests for CommandLine.getOptionValues(OptionGroup)
 */
public class CommandLine_getOptionValues_25_0_Test_testGetOptionValues_NotSelected_ReturnsNull {

    /**
     * Create a CommandLine instance by invoking its private constructor via reflection.
     */
    private CommandLine createCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(new LinkedList<>(), new ArrayList<>(), null);
    }


    @Test
    public void testGetOptionValues_NotSelected_ReturnsNull() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        // by default selected == null
        OptionGroup group = new OptionGroup();
        assertFalse(group.isSelected(), "Precondition: group should not be selected");
        String[] result = cmd.getOptionValues(group);
        assertNull(result, "Expected null when OptionGroup is not selected");
    }

}
