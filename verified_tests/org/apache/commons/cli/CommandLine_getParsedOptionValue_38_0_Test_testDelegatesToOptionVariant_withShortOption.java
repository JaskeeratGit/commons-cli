package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
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
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValue_38_0_Test_testDelegatesToOptionVariant_withShortOption {

    /**
     * A test subclass of CommandLine that overrides getParsedOptionValue(Option, T)
     * so we can observe delegation from the focal method getParsedOptionValue(String, T).
     */
    static class TestCommandLine extends CommandLine {

        Option lastOption;

        Object lastDefault;

        @Override
        public <T> T getParsedOptionValue(final Option option, final T defaultValue) throws ParseException {
            this.lastOption = option;
            this.lastDefault = defaultValue;
            if (option == null) {
                return defaultValue;
            }
            @SuppressWarnings("unchecked")
            T result = (T) ("parsed:" + option.getOpt());
            return result;
        }
    }

    @Test
    public void testDelegatesToOptionVariant_withShortOption() throws Exception {
        TestCommandLine cmd = new TestCommandLine();
        // create Option with short and long names
        Option opt = new Option("a", "alpha", false, "desc");
        // inject option into the private 'options' list
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Option> options = (List<Option>) optionsField.get(cmd);
        options.add(opt);
        // call focal method with short option (with leading hyphen)
        String result = cmd.getParsedOptionValue("-a", "DEF");
        assertEquals("parsed:a", result, "Should return parsed value from overridden method");
        // overridden method should have been invoked with the matching Option
        assertNotNull(cmd.lastOption);
        assertEquals("a", cmd.lastOption.getOpt());
        assertEquals("DEF", cmd.lastDefault);
    }



}
