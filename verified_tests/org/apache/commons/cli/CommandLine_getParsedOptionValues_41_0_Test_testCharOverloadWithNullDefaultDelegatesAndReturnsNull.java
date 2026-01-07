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

public class CommandLine_getParsedOptionValues_41_0_Test_testCharOverloadWithNullDefaultDelegatesAndReturnsNull {

    /**
     * A small test subclass that overrides the String-based getParsedOptionValues
     * so we can observe delegation from the char-based overload.
     */
    static class TestCommandLine extends CommandLine {

        String capturedOptionName = null;

        public TestCommandLine() {
            // calls the protected constructor in CommandLine
            super();
        }

        @Override
        public <T> T[] getParsedOptionValues(final String optionName, final T[] defaultValue) {
            this.capturedOptionName = optionName;
            return defaultValue;
        }
    }


    @Test
    public void testCharOverloadWithNullDefaultDelegatesAndReturnsNull() throws Exception {
        TestCommandLine cl = new TestCommandLine();
        String[] result = cl.getParsedOptionValues('b', (String[]) null);
        assertEquals("b", cl.capturedOptionName);
        assertNull(result);
    }

}
