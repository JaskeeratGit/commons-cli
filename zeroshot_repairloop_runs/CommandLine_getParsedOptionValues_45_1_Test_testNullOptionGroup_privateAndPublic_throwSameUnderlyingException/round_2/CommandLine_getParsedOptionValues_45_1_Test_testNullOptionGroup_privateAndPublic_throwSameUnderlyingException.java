package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies behavior of getParsedOptionValues(OptionGroup).
 */
public class CommandLine_getParsedOptionValues_45_1_Test_testNullOptionGroup_privateAndPublic_throwSameUnderlyingException {

    /**
     * Verifies that calling the private method with a null OptionGroup and calling
     * the public delegating method with a null OptionGroup either both throw the
     * same underlying exception type or both return and produce the same result
     * (same instance).
     */
    @Test
    public void testNullOptionGroup_privateAndPublic_throwSameUnderlyingException() throws Exception {
        // create CommandLine via private constructor
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance(new LinkedList<>(), new ArrayList<>(), (Consumer<?>) (o -> {
            // no-op
        }));

        Method twoArgMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", OptionGroup.class, Supplier.class);
        twoArgMethod.setAccessible(true);

        // Invoke two-arg (declared) method with null OptionGroup and capture underlying cause or result
        Object privateResult = null;
        Throwable privateUnderlying = null;
        try {
            privateResult = twoArgMethod.invoke(cmd, null, (Supplier<?>) (() -> null));
        } catch (InvocationTargetException ite) {
            privateUnderlying = ite.getCause();
        } catch (Throwable t) {
            privateUnderlying = t;
        }

        // Invoke public method with null OptionGroup and capture thrown exception or result
        Object publicResult = null;
        Throwable publicThrown = null;
        try {
            publicResult = cmd.getParsedOptionValues((OptionGroup) null);
        } catch (Throwable t) {
            publicThrown = t;
        }

        // If both returned (no exception), ensure the results are the same instance
        if (privateUnderlying == null && publicThrown == null) {
            assertSame(privateResult, publicResult, "When both invocations return, they should return the same instance");
        } else {
            // Otherwise ensure both threw something and that the underlying exception types match
            assertNotNull(privateUnderlying, "Private invocation should throw an underlying exception when public threw an exception");
            assertNotNull(publicThrown, "Public invocation should throw an exception when private threw an exception");
            assertEquals(privateUnderlying.getClass(), publicThrown.getClass(),
                    "The underlying exception types should match between private and public invocations");
        }
    }
}
