package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * JUnit 5 tests for CommandLine#getParsedOptionValues(char).
 *
 * These tests verify that the char overload delegates to the String overload.
 * A SpyCommandLine subclass overrides the String overload to capture the argument
 * and return predictable values. Both direct invocation and reflective invocation
 * of the char overload are tested.
 */
public class CommandLine_getParsedOptionValues_39_0_Test_testDelegationToStringOverload_directCall {

    /**
     * A test double that overrides getParsedOptionValues(String) so we can observe delegation.
     */
    public static class SpyCommandLine extends CommandLine {

        String capturedOptionName;

        public SpyCommandLine() {
            // uses protected no-arg constructor
            super();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] getParsedOptionValues(final String optionName) {
            this.capturedOptionName = optionName;
            // return a predictable String[] (unchecked cast to T[])
            return (T[]) new String[] { optionName + "-v1", optionName + "-v2" };
        }
    }

    @Test
    public void testDelegationToStringOverload_directCall() {
        SpyCommandLine cmd = new SpyCommandLine();
        // explicit type witness to help inference (T = String yields T[] == String[])
        String[] values = cmd.<String>getParsedOptionValues('a');
        assertEquals("a", cmd.capturedOptionName, "Should delegate with single-character string");
        assertArrayEquals(new String[] { "a-v1", "a-v2" }, values);
    }


}
