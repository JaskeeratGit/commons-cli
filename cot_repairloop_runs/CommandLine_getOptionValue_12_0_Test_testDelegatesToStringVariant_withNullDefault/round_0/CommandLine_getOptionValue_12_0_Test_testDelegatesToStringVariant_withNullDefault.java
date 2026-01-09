package org.apache.commons.cli;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for CommandLine.getOptionValue(char, String) delegating to
 * getOptionValue(String, Supplier<String>) when default is null.
 */
class CommandLine_getOptionValue_12_0_Test_testDelegatesToStringVariant_withNullDefault {

    /**
     * Subclass of CommandLine used to capture the arguments passed to
     * getOptionValue(String, Supplier<String>) and to return a recognizable value.
     *
     * Placed in the same package so it can access protected no-arg constructor.
     */
    static class TestCommandLine extends CommandLine {

        String capturedOpt;
        Supplier<String> capturedSupplier;

        // relies on the protected no-arg constructor being available
        TestCommandLine() {
            super();
        }

        // Intentionally do not use @Override to remain resilient in case of signature differences,
        // but if the base class has this exact signature, this will override it.
        public String getOptionValue(final String opt, final Supplier<String> defaultValue) {
            this.capturedOpt = opt;
            this.capturedSupplier = defaultValue;
            // call the supplier to emulate typical behavior of using the default
            return "OVERRIDE:" + defaultValue.get();
        }
    }

    @Test
    void testDelegatesToStringVariant_withNullDefault() {
        TestCommandLine cmd = new TestCommandLine();
        // disambiguate the overloaded methods by casting null to String so the (char, String) variant is chosen
        String res = cmd.getOptionValue('Z', (String) null);
        // When supplier.get() returns null, concatenation will produce "OVERRIDE:null"
        assertEquals("OVERRIDE:null", res, "Expected overridden method to be called and to handle null default via supplier");
        assertEquals("Z", cmd.capturedOpt);
        assertNull(cmd.capturedSupplier.get(), "Supplier should return null when defaultValue was null");
    }
}
