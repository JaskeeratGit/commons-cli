package org.apache.commons.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLine_getParsedOptionValue_29_0_Test_testDelegatesToStringVariant_andReturnsStringResult {

    /**
     * A small test subclass that overrides the String-variant of getParsedOptionValue to
     * capture the incoming option name and default value and return predictable results
     * depending on the type of defaultValue.
     */
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
    void testDelegatesToStringVariant_andReturnsStringResult() throws ParseException {
        TestCommandLine cmd = new TestCommandLine();
        String result = cmd.getParsedOptionValue('a', "default");
        assertEquals("OK:a", result, "Should return the value produced by the String-variant override");
        assertEquals("a", cmd.capturedOptionName, "The char should be converted to a String and passed");
        assertEquals("default", cmd.capturedDefaultValue, "Default value should be forwarded unchanged");
    }

}
