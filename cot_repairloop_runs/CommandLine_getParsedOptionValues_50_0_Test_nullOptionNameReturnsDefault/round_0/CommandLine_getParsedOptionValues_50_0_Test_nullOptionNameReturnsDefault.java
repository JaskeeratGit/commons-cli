package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Focused test for CommandLine#getParsedOptionValues(String, T[]).
 * This test ensures that when a null option name is supplied the method
 * returns the provided default array.
 *
 * The test uses the real CommandLine from org.apache.commons.cli and avoids
 * introducing any top-level stub classes that would conflict with other tests.
 */
public class CommandLine_getParsedOptionValues_50_0_Test_nullOptionNameReturnsDefault {

    @Test
    public void nullOptionNameReturnsDefault() throws Exception {
        // Use the real CommandLine (protected no-arg constructor is package-visible)
        CommandLine cl = new CommandLine();
        String[] defaultValues = new String[] { "x" };

        // disambiguate the call so the compiler selects the String variant (not Option)
        String[] result = cl.getParsedOptionValues((String) null, defaultValues);

        assertArrayEquals(defaultValues, result);
    }
}
