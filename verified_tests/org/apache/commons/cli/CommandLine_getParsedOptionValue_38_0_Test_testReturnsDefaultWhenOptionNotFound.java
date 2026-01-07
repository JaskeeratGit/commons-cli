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

public class CommandLine_getParsedOptionValue_38_0_Test_testReturnsDefaultWhenOptionNotFound {

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
    public void testReturnsDefaultWhenOptionNotFound() throws Exception {
        TestCommandLine cmd = new TestCommandLine();
        // no options added; resolution should return null and overridden method should get null
        String defaultValue = "theDefault";
        String result = cmd.getParsedOptionValue("nonexistent", defaultValue);
        assertEquals(defaultValue, result, "When option not found, should return default");
        assertNull(cmd.lastOption, "lastOption should be null when resolution fails");
        assertEquals(defaultValue, cmd.lastDefault);
    }

}
