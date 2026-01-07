package org.apache.commons.cli;

import java.lang.reflect.Method;
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

public class CommandLine_getOptionValue_17_0_Test_returnsFirstElementWhenArrayHasElements_viaReflection {

    /**
     * Test helper subclass that lets us control the return value of getOptionValues(OptionGroup).
     * Uses the protected no-arg constructor of CommandLine.
     */
    static class TestableCommandLine extends CommandLine {

        private final String[] toReturn;

        protected TestableCommandLine(final String[] toReturn) {
            super();
            this.toReturn = toReturn;
        }

        @Override
        public String[] getOptionValues(final OptionGroup optionGroup) {
            return toReturn;
        }
    }


    @Test
    public void returnsFirstElementWhenArrayHasElements_viaReflection() throws Exception {
        TestableCommandLine cmd = new TestableCommandLine(new String[] { "first", "second" });
        Method m = CommandLine.class.getMethod("getOptionValue", OptionGroup.class);
        m.setAccessible(true);
        String result = (String) m.invoke(cmd, new OptionGroup());
        assertEquals("first", result);
    }


}
