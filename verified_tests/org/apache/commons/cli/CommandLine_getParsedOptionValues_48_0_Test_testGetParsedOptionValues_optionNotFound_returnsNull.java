package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
 * Unit tests for CommandLine#getParsedOptionValues(String)
 */
public class CommandLine_getParsedOptionValues_48_0_Test_testGetParsedOptionValues_optionNotFound_returnsNull {


    @Test
    public void testGetParsedOptionValues_optionNotFound_returnsNull() throws Exception {
        CommandLine cmd = new CommandLine();
        // option name that cannot be resolved
        Object[] values = cmd.getParsedOptionValues("nonexistent");
        assertNull(values, "Expected null when option name does not match any option");
    }

}
