package org.apache.commons.cli;

import java.lang.reflect.Constructor;
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
import java.util.function.Supplier;

public class CommandLine_getOptionValues_23_0_Test_delegatesToStringVariantPassingCorrectString {


    @Test
    public void delegatesToStringVariantPassingCorrectString() {
        // Create a subclass that overrides the String variant so we can assert delegation
        class TestCommandLine extends CommandLine {

            String receivedOptionName;

            public TestCommandLine() {
                // protected constructor is accessible from subclass
                super();
            }

            @Override
            public String[] getOptionValues(String optionName) {
                this.receivedOptionName = optionName;
                // return a distinct array so we can assert it's returned unchanged
                return new String[] { "returned", optionName };
            }
        }
        TestCommandLine tcmd = new TestCommandLine();
        String[] result = tcmd.getOptionValues('Z');
        // Ensure delegation occurred: the overridden method received the expected string
        assertNotNull(result, "Result should not be null from overridden method");
        assertEquals(2, result.length);
        assertEquals("returned", result[0]);
        assertEquals("Z", result[1]);
        // Also verify the subclass captured the passed option name correctly
        assertEquals("Z", tcmd.receivedOptionName);
    }
}
