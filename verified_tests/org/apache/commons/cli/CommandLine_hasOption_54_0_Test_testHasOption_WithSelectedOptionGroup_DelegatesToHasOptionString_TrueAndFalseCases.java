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

public class CommandLine_hasOption_54_0_Test_testHasOption_WithSelectedOptionGroup_DelegatesToHasOptionString_TrueAndFalseCases {



    @Test
    public void testHasOption_WithSelectedOptionGroup_DelegatesToHasOptionString_TrueAndFalseCases() throws Exception {
        // Prepare an OptionGroup and set its private 'selected' field reflectively to simulate selection
        OptionGroup group = new OptionGroup();
        Field selectedField = OptionGroup.class.getDeclaredField("selected");
        selectedField.setAccessible(true);
        // simulate selection
        selectedField.set(group, "my-opt");
        assertTrue(group.isSelected(), "OptionGroup should be selected after setting 'selected' field");
        // Case A: subclass returns true for the given selected name
        CommandLine cmdTrue = new CommandLine() {

            @Override
            public boolean hasOption(final String optionName) {
                return "my-opt".equals(optionName);
            }
        };
        assertTrue(cmdTrue.hasOption(group), "When hasOption(String) returns true, hasOption(OptionGroup) should return true");
        // Case B: subclass returns false for the given selected name
        CommandLine cmdFalse = new CommandLine() {

            @Override
            public boolean hasOption(final String optionName) {
                return false;
            }
        };
        assertFalse(cmdFalse.hasOption(group), "When hasOption(String) returns false, hasOption(OptionGroup) should return false");
    }
}
