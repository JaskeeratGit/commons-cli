package org.apache.commons.cli;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A small test subclass that overrides the String-variant of getParsedOptionValue to
 * capture the incoming option name and default value and return predictable results
 * depending on the type of defaultValue.
 */
class CommandLine_getParsedOptionValue_29_0_Test_testNullDefaultValue_returnsNull {

    static class TestCommandLine extends CommandLine {

        String capturedOptionName;

        Object capturedDefaultValue;

        protected TestCommandLine() {
            // calls protected no-arg constructor of CommandLine
            super();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getParsedOptionValue(final String optionName, final T defaultValue) {
            this.capturedOptionName = optionName;
            this.capturedDefaultValue = defaultValue;
            // If defaultValue is null -> return null
            if (defaultValue == null) {
                return null;
            }
            // If defaultValue is an Integer, return the codepoint of the first char as Integer
            if (defaultValue instanceof Integer) {
                return (T) Integer.valueOf((int) optionName.charAt(0));
            }
            // Otherwise return a string indicating the optionName
            return (T) ("OK:" + optionName);
        }
    }


    @Test
    void testNullDefaultValue_returnsNull() throws ParseException {
        TestCommandLine cmd = new TestCommandLine();
        Object result = cmd.getParsedOptionValue('c', (Object) null);
        assertNull(result, "When defaultValue is null the override returns null");
        assertEquals("c", cmd.capturedOptionName);
        assertNull(cmd.capturedDefaultValue);
    }

}
