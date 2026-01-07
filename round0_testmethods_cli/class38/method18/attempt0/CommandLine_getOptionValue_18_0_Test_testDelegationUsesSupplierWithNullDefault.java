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

class CommandLine_getOptionValue_18_0_Test_testDelegationUsesSupplierWithNullDefault {

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
    void testDelegationUsesSupplierWithNullDefault() {
        TestCommandLine cli = new TestCommandLine();
        // pass a non-null group to ensure it's forwarded
        OptionGroup group = new OptionGroup();
        String result = cli.getOptionValue(group, (String) null);
        // Supplier.get() will return null; concatenation yields "OVERRIDE:null"
        assertEquals("OVERRIDE:null", result);
        // Verify the OptionGroup passed down is the same instance
        assertSame(group, cli.capturedGroup, "OptionGroup should be forwarded to the Supplier-based overload");
        // Supplier should be non-null (the method should create a Supplier that returns null)
        assertNotNull(cli.capturedSupplier);
        assertNull(cli.capturedSupplier.get());
    }

}
