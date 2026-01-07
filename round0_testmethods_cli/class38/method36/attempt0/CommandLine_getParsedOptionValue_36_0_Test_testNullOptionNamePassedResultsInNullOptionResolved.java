package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Unit tests for CommandLine.getParsedOptionValue(String)
 */
public class CommandLine_getParsedOptionValue_36_0_Test_testNullOptionNamePassedResultsInNullOptionResolved {

    /**
     * A testable subclass that overrides getParsedOptionValue(Option)
     * so we can capture the resolved Option passed by the focal method.
     */
    static class TestableCommandLine extends CommandLine {

        private Option captured;

        private final Object toReturn;

        TestableCommandLine(final Object toReturn) {
            // calls protected no-arg constructor
            super();
            this.toReturn = toReturn;
        }

        @Override
        public <T> T getParsedOptionValue(final Option option) throws ParseException {
            this.captured = option;
            @SuppressWarnings("unchecked")
            final T r = (T) toReturn;
            return r;
        }

        Option getCaptured() {
            return captured;
        }
    }

    private void setOptionsList(final CommandLine cmd, final List<Option> list) throws Exception {
        final Field f = CommandLine.class.getDeclaredField("options");
        f.setAccessible(true);
        f.set(cmd, list);
    }




    @Test
    public void testNullOptionNamePassedResultsInNullOptionResolved() throws Exception {
        final String expected = "nullNameCase";
        final TestableCommandLine cmd = new TestableCommandLine(expected);
        // ensure there is at least one option but name is null => resolveOption should treat as null
        final Option opt = new Option("a", "alpha", false, "desc");
        final List<Option> opts = new ArrayList<>();
        opts.add(opt);
        setOptionsList(cmd, opts);
        final Object result = cmd.getParsedOptionValue((String) null);
        assertEquals(expected, result, "When passing null name, overridden method should be invoked with null and its return passed through");
        final Option captured = cmd.getCaptured();
        assertNull(captured, "Captured option should be null when optionName is null");
    }
}
