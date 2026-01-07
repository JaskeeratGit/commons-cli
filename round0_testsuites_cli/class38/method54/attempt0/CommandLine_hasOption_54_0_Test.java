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

public class CommandLine_hasOption_54_0_Test {

    @Test
    public void testHasOption_WithNullOptionGroup_ReturnsFalse_usingPrivateConstructor() throws Exception {
        // Use reflection to invoke the private constructor CommandLine(List<String>, List<Option>, Consumer<Option>)
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // Create an instance of CommandLine without relying on other constructors
        CommandLine cmd = ctor.newInstance(new LinkedList<String>(), new ArrayList<>(), (Consumer<Option>) null);
        // Calling hasOption with null should return false (branch: optionGroup == null)
        assertFalse(cmd.hasOption((OptionGroup) null), "hasOption(null) should return false");
    }

    @Test
    public void testHasOption_WithUnselectedOptionGroup_ReturnsFalse() throws Exception {
        // Instantiate a subtype of CommandLine to avoid depending on resolution of actual options.
        // The subclass uses default behavior for hasOption(OptionGroup) (inherited) but we override hasOption(String)
        // so that the call path into hasOption(String) is observable if reached.
        CommandLine cmd = new CommandLine() {

            @Override
            public boolean hasOption(final String optionName) {
                // If this is called unexpectedly for an unselected group, fail the test.
                // But it may be called in other contexts; we defensively return false.
                return false;
            }
        };
        // Unselected OptionGroup (default) should produce false (branch: !optionGroup.isSelected())
        OptionGroup group = new OptionGroup();
        assertFalse(group.isSelected(), "new OptionGroup should not be selected by default");
        assertFalse(cmd.hasOption(group), "hasOption on an unselected OptionGroup should return false");
    }

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
