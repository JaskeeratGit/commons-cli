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
public class CommandLine_getOptionValues_25_0_Test_testGetOptionValues_Selected_DelegatesAndReturns {

    /**
     * Create a CommandLine instance by invoking its private constructor via reflection.
     */
    private CommandLine createCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(new LinkedList<>(), new ArrayList<>(), null);
    }



    @Test
    public void testGetOptionValues_Selected_DelegatesAndReturns() throws Exception {
        CommandLine cmd = createCommandLineInstance();
        OptionGroup group = new OptionGroup();
        // Force the OptionGroup into selected state by setting its private 'selected' field via reflection
        Field selectedField = OptionGroup.class.getDeclaredField("selected");
        selectedField.setAccessible(true);
        selectedField.set(group, "someOptionName");
        assertTrue(group.isSelected(), "Precondition: group should be selected after setting 'selected' field");
        // Call the focal method. The method should invoke getOptionValues(String) internally.
        // We do not rely on the internals of getOptionValues(String)/resolveOption here; we assert that the call completes
        // and returns whatever the delegated method returns (likely null in absence of configured options).
        String[] result = cmd.getOptionValues(group);
        // The returned value can be null (no matching option/values). We assert invocation did not throw and result may be null.
        // This covers the branch where optionGroup.isSelected() == true.
        assertNull(result, "Expected null when delegated getOptionValues(String) finds no values (safe default assertion)");
    }
}
