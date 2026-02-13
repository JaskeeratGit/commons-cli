package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_getParsedOptionValues_45_1_Test_testNullOptionGroup_privateAndPublic_throwSameUnderlyingException {

    /**
     * Verifies that calling the private method with a null OptionGroup and calling
     * the public delegating method with a null OptionGroup produce the same type of
     * underlying exception (i.e. the public method truly delegates behavior).
     */
    @Test
    public void testNullOptionGroup_privateAndPublic_throwSameUnderlyingException() throws Exception {
        // create CommandLine via private constructor
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance(new LinkedList<>(), new ArrayList<>(), (Consumer<?>) (o -> {
            // no-op
        }));
        Method privateMethod = CommandLine.class.getDeclaredMethod("getParsedOptionValues", OptionGroup.class, Supplier.class);
        privateMethod.setAccessible(true);
        // Invoke private method with null OptionGroup and capture underlying cause
        Throwable privateUnderlying = null;
        try {
            privateMethod.invoke(cmd, null, (Supplier<?>) (() -> null));
        } catch (InvocationTargetException ite) {
            privateUnderlying = ite.getCause();
        } catch (Throwable t) {
            privateUnderlying = t;
        }
        // Invoke public method with null OptionGroup and capture thrown exception
        Throwable publicThrown = null;
        try {
            cmd.getParsedOptionValues((OptionGroup) null);
        } catch (Throwable t) {
            publicThrown = t;
        }
        // Ensure both threw something or both did not, and if they threw the underlying exception types match
        assertTrue(
            (privateUnderlying == null && publicThrown == null) ||
            (privateUnderlying != null && publicThrown != null && privateUnderlying.getClass().equals(publicThrown.getClass())),
            "Both private and public invocations should either both not throw, or both throw the same underlying exception type"
        );
    }
}
