package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValues_46_0_Test_testSelectedOptionGroupDelegatesToStringVariant {



    @Test
    void testSelectedOptionGroupDelegatesToStringVariant() throws Exception {
        // Arrange
        CommandLine cmd = new CommandLine();
        OptionGroup optionGroup = new OptionGroup();
        // set private 'selected' field on OptionGroup to simulate selection
        Field selectedField = OptionGroup.class.getDeclaredField("selected");
        selectedField.setAccessible(true);
        selectedField.set(optionGroup, "myOpt");
        Supplier<String[]> supplier = () -> new String[] { "s1" };
        // Act
        String[] fromFocal = cmd.getParsedOptionValues(optionGroup, supplier);
        // Use reflection to invoke the two-argument variant that focal method delegates to:
        Method delegated = CommandLine.class.getDeclaredMethod("getParsedOptionValues", String.class, Supplier.class);
        delegated.setAccessible(true);
        String[] fromDelegated = (String[]) delegated.invoke(cmd, "myOpt", supplier);
        // Assert: the focal method should return what the delegated method returns
        assertArrayEquals(fromDelegated, fromFocal);
    }
}
