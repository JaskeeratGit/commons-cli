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

public class CommandLine_hasOption_54_0_Test_testHasOption_WithNullOptionGroup_ReturnsFalse_usingPrivateConstructor {

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


}
