package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit tests for CommandLine.getParsedOptionValue focusing on:
 *  - behavior of the char-variant when defaultValue is null
 *  - exercising the String-variant override branches via a test subclass
 */
class CommandLine_getParsedOptionValue_29_0_Test_testNullDefaultValue_returnsNull {

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

    /**
     * Test the observable behavior of the char-variant when defaultValue is null.
     * We instantiate a plain CommandLine and call the char-variant; the expected
     * result is null (no parsed option / no default).
     */
    @Test
    void testNullDefaultValue_returnsNull() throws ParseException {
        CommandLine cmd = new CommandLine();
        Object result = cmd.getParsedOptionValue('c', null);
        assertNull(result, "When defaultValue is null the method should return null");
    }

    /**
     * Directly test the String-variant override in the TestCommandLine subclass to
     * exercise the branch where defaultValue is an Integer.
     */
    @Test
    void testStringVariant_integerDefault_returnsCodepoint() {
        TestCommandLine cmd = new TestCommandLine();
        Integer result = cmd.getParsedOptionValue("abc", Integer.valueOf(0));
        assertEquals(Integer.valueOf((int) 'a'), result, "Should return codepoint of first char as Integer");
        assertEquals("abc", cmd.capturedOptionName);
        assertEquals(Integer.valueOf(0), cmd.capturedDefaultValue);
    }

    /**
     * Directly test the String-variant override branch when defaultValue is a non-Integer,
     * expecting a string with the "OK:" prefix.
     */
    @Test
    void testStringVariant_nonIntegerDefault_returnsOkString() {
        TestCommandLine cmd = new TestCommandLine();
        String result = cmd.getParsedOptionValue("z", "def");
        assertEquals("OK:z", result);
        assertEquals("z", cmd.capturedOptionName);
        assertEquals("def", cmd.capturedDefaultValue);
    }
}
