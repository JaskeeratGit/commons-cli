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

public class CommandLine_hasOption_54_0_Test_testHasOption_WithUnselectedOptionGroup_ReturnsFalse {


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

}
