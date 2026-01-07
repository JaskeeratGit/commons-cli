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

public class CommandLine_getParsedOptionValues_46_0_Test_testNotSelectedOptionGroupReturnsDefault {


    @Test
    void testNotSelectedOptionGroupReturnsDefault() throws Exception {
        // Arrange
        CommandLine cmd = new CommandLine();
        // not selected by default
        OptionGroup optionGroup = new OptionGroup();
        Supplier<Integer[]> supplier = () -> new Integer[] { 10, 20, 30 };
        // Act
        Integer[] result = cmd.getParsedOptionValues(optionGroup, supplier);
        // Assert
        assertArrayEquals(supplier.get(), result);
    }

}
