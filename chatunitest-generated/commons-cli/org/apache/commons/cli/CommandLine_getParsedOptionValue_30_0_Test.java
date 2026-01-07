package org.apache.commons.cli;

import java.lang.reflect.Method;
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
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValue_30_0_Test {

    /**
     * Ensure the no-arg public getParsedOptionValue(Option) delegates to the two-arg
     * getParsedOptionValue(Option, Supplier) with a Supplier that returns null.
     * We compare the return value of the public method with a reflective invocation
     * of the private/protected two-arg method when the supplier returns null.
     */
    @Test
    public void testPublicMethodDelegatesToTwoArgWithNullSupplier() throws Exception {
        // instantiate CommandLine using the protected no-arg constructor (same package)
        CommandLine cmd = new CommandLine() {
        };
        // create an Option that has an argument and a value
        Option opt = new Option("o", true, "description");
        opt.getValuesList().add("value1");
        // call the public method
        Object publicResult = cmd.getParsedOptionValue(opt);
        // locate the two-arg method getParsedOptionValue(Option, Supplier)
        Method twoArg = CommandLine.class.getDeclaredMethod("getParsedOptionValue", Option.class, Supplier.class);
        twoArg.setAccessible(true);
        // invoke the two-arg method reflectively with a Supplier that returns null
        Object reflectResult = twoArg.invoke(cmd, opt, (Supplier<?>) () -> null);
        // The public method should delegate and thus produce the same result as calling
        // the two-arg method with a supplier returning null.
        assertEquals(reflectResult, publicResult);
    }

    /**
     * Verify behavior difference between public one-arg method (which uses a supplier returning null)
     * and the two-arg method when provided a non-null supplier.
     *
     * - When the option has no values, the public method should return null (supplier used is () -> null).
     * - The two-arg method should return the supplier-provided value when a non-null supplier is passed.
     */
    @Test
    public void testTwoArgSupplierUsedWhenNoValuesPresent() throws Exception {
        CommandLine cmd = new CommandLine() {
        };
        // create an Option without values
        Option opt = new Option("b", false, "no values");
        // public method uses supplier that returns null -> expect null
        Object publicResult = cmd.getParsedOptionValue(opt);
        assertNull(publicResult, "Public getParsedOptionValue should return null when no values and supplier returns null");
        // call the private two-arg method with a supplier that returns a sentinel object
        Method twoArg = CommandLine.class.getDeclaredMethod("getParsedOptionValue", Option.class, Supplier.class);
        twoArg.setAccessible(true);
        final String sentinel = "SENTINEL";
        Object twoArgResult = twoArg.invoke(cmd, opt, (Supplier<?>) () -> sentinel);
        assertEquals(sentinel, twoArgResult, "Two-arg getParsedOptionValue should return the supplier-provided value when no option values");
    }
}
