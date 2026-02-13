package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
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
import java.util.Properties;
import java.util.function.Consumer;

class CommandLine_getOptionValue_18_0_Test_testDelegationUsesSupplierWithNonNullDefault {

    /**
     * Test helper subclass that captures the Supplier and OptionGroup passed to
     * the Supplier-based getOptionValue(...) overload. This method is declared
     * without @Override to avoid compile-time dependency on the exact signature
     * visibility of the overload in the super class; at runtime the superclass'
     * call should dispatch here if signatures match.
     */
    static class TestCommandLine extends CommandLine {

        Supplier<String> capturedSupplier;

        OptionGroup capturedGroup;

        final String prefix = "OVERRIDE:";

        // protected constructor in CommandLine is accessible from same package
        TestCommandLine() {
            super();
        }

        // Intentionally do not annotate with @Override (safer across different compile contexts).
        public String getOptionValue(final OptionGroup group, final Supplier<String> defaultValue) {
            this.capturedGroup = group;
            this.capturedSupplier = defaultValue;
            // guard in case the supplier itself is null (defensive)
            String v = (defaultValue == null) ? null : defaultValue.get();
            return prefix + v;
        }
    }

    @Test
    void testDelegationUsesSupplierWithNonNullDefault() {
        TestCommandLine cli = new TestCommandLine();
        String result = cli.getOptionValue((OptionGroup) null, "myDefault");
        // The subclass override should have been dispatched and returned prefix + default
        assertEquals("OVERRIDE:myDefault", result);
        // The Supplier captured should be non-null and return the original default
        assertNotNull(cli.capturedSupplier, "Supplier should have been passed to the overload");
        assertEquals("myDefault", cli.capturedSupplier.get());
    }


}
