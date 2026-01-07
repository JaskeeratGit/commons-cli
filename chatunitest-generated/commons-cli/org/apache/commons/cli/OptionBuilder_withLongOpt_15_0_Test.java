package org.apache.commons.cli;

import org.apache.commons.cli.OptionBuilder;
import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("deprecation")
public class OptionBuilder_withLongOpt_15_0_Test {

    private Field longOptionField;

    private Field instanceField;

    private String originalLongOption;

    private Object originalInstance;

    @BeforeEach
    public void setUp() throws Exception {
        longOptionField = OptionBuilder.class.getDeclaredField("longOption");
        longOptionField.setAccessible(true);
        originalLongOption = (String) longOptionField.get(null);
        instanceField = OptionBuilder.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        originalInstance = instanceField.get(null);
    }

    @Test
    public void testWithLongOptSetsValueAndReturnsSingleton() throws Exception {
        // ensure a different initial value to observe change
        longOptionField.set(null, "initialValue");
        OptionBuilder result = OptionBuilder.withLongOpt("myLongOpt");
        // verify returned instance is the same as the private INSTANCE
        Object instance = instanceField.get(null);
        assertSame(instance, result, "withLongOpt should return the singleton INSTANCE");
        // verify the static longOption was set
        String current = (String) longOptionField.get(null);
        assertEquals("myLongOpt", current, "longOption should be set to the provided value");
    }

    @Test
    public void testWithLongOptOverwritesPreviousValueAndHandlesNull() throws Exception {
        // set a prior value
        longOptionField.set(null, "previousValue");
        OptionBuilder.withLongOpt("firstValue");
        assertEquals("firstValue", (String) longOptionField.get(null), "longOption should be firstValue after first call");
        // call again with a new value to ensure overwrite
        OptionBuilder.withLongOpt("secondValue");
        assertEquals("secondValue", (String) longOptionField.get(null), "longOption should be secondValue after second call");
        // call with null to ensure it can accept null and overwrite
        OptionBuilder.withLongOpt(null);
        assertNull(longOptionField.get(null), "longOption should be null after calling withLongOpt(null)");
    }
}
