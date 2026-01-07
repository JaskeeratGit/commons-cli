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
public class OptionBuilder_withLongOpt_15_0_Test_testWithLongOptOverwritesPreviousValueAndHandlesNull {

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

    @AfterEach
    public void tearDown() throws Exception {
        // restore original longOption
        longOptionField.set(null, originalLongOption);
        // restore INSTANCE (should be final but resetting for cleanliness)
        instanceField.set(null, originalInstance);
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
