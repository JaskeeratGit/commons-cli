package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class Option_builder_1_0_Test {

    private Option buildOptionFromBuilder(Object builder) {
        try {
            Method buildMethod = builder.getClass().getMethod("build");
            Object optionObj = buildMethod.invoke(builder);
            assertNotNull(optionObj);
            assertTrue(optionObj instanceof Option);
            return (Option) optionObj;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            fail("Builder does not expose a public build() method: " + e.getMessage());
            // unreachable
            return null;
        } catch (InvocationTargetException e) {
            // Unwrap and rethrow the underlying exception for assertions in tests that expect exceptions
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    private Object invokeBuilderFluent(Object builder, String methodName, Class<?> paramType, Object arg) {
        try {
            Method m = builder.getClass().getMethod(methodName, paramType);
            return m.invoke(builder, arg);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Failed to invoke builder method '" + methodName + "': " + e.getMessage());
            return null;
        }
    }

    @Test
    void builderCreatesOptionFromOpt() {
        Object builder = Option.builder("o");
        Option opt = buildOptionFromBuilder(builder);
        assertNotNull(opt);
        assertEquals("o", opt.getOpt());
    }

    @Test
    void builderAcceptsNullOption() {
        Object builder = Option.builder(null);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> buildOptionFromBuilder(builder));
        assertTrue(ex.getMessage().contains("Either opt or longOpt must be specified"));
    }

    @Test
    void builderAcceptsLongOptOnly() {
        Object builder = Option.builder(null);
        Object ret = invokeBuilderFluent(builder, "longOpt", String.class, "long");
        assertNotNull(ret);
        // fluent API should return the builder itself (or same runtime type)
        assertEquals(builder.getClass(), ret.getClass());
        Option opt = buildOptionFromBuilder(builder);
        assertEquals("long", opt.getLongOpt());
    }

    @Test
    void buildWithoutOptOrLongOptThrows() {
        Object builder = Option.builder((String) null);
        // building without setting opt or longOpt should throw IllegalStateException
        assertThrows(IllegalStateException.class, () -> buildOptionFromBuilder(builder));
    }

    @Test
    void buildWithOptReturnsOptionWithOptSet() {
        Object builder = Option.builder("o");
        Option opt = buildOptionFromBuilder(builder);
        assertNotNull(opt);
        assertEquals("o", opt.getOpt());
        // longOpt should be null when not set
        assertNull(opt.getLongOpt());
    }

    @Test
    void buildWithLongOptOnlyReturnsOptionWithLongOptSet() {
        Object builder = Option.builder((String) null);
        // set longOpt via fluent API
        Object returned = invokeBuilderFluent(builder, "longOpt", String.class, "long-name");
        // fluent API should return the builder itself (allow chaining)
        assertSame(builder, returned);
        Option opt = buildOptionFromBuilder(builder);
        assertNotNull(opt);
        assertEquals("long-name", opt.getLongOpt());
        // opt (short name) should be null when only longOpt is set
        assertNull(opt.getOpt());
    }

    @Test
    void builderFluentChainingKeepsValues() {
        Object builder = Option.builder("x");
        // chain longOpt
        Object b2 = invokeBuilderFluent(builder, "longOpt", String.class, "longX");
        assertSame(builder, b2);
        Option opt = buildOptionFromBuilder(builder);
        assertNotNull(opt);
        assertEquals("x", opt.getOpt());
        assertEquals("longX", opt.getLongOpt());
    }

    @Test
    void builderAcceptsNullOptionWhenLongOptSet() {
        Object builder = Option.builder(null);
        // set a long option so the builder is valid
        invokeBuilderFluent(builder, "longOpt", String.class, "theLong");
        Option option = buildOptionFromBuilder(builder);
        assertNotNull(option);
        assertNull(option.getOpt(), "short option should be null when builder was created with null");
        assertEquals("theLong", option.getLongOpt());
    }

    @Test
    void builderRejectsNullOptionUnlessLongOptSet() {
        Object builder = Option.builder(null);
        // building without setting longOpt should throw IllegalStateException
        assertThrows(IllegalStateException.class, () -> buildOptionFromBuilder(builder), "Expected IllegalStateException when neither opt nor longOpt is specified");
    }

    @Test
    void builderAcceptsNonNullOptionAndLongOpt() {
        Object builder = Option.builder("o");
        invokeBuilderFluent(builder, "longOpt", String.class, "longOption");
        Option option = buildOptionFromBuilder(builder);
        assertNotNull(option);
        assertEquals("o", option.getOpt());
        assertEquals("longOption", option.getLongOpt());
    }

    @Test
    void builderCreatesOptionWithOpt() {
        // create builder with a short option name
        Object builder = Option.builder("a");
        Option opt = buildOptionFromBuilder(builder);
        assertNotNull(opt);
        assertEquals("a", opt.getOpt());
        // longOpt should be null by default
        assertNull(opt.getLongOpt());
    }

    @Test
    void builderAcceptsNullOptionWhenLongOptIsSet() {
        // builder created with null short opt but set longOpt before build
        Object builder = Option.builder(null);
        // call longOpt("long-name") fluently
        Object updatedBuilder = invokeBuilderFluent(builder, "longOpt", String.class, "long-name");
        // ensure fluency returns the builder (or equivalent) and build works
        Option opt = buildOptionFromBuilder(updatedBuilder);
        assertNotNull(opt);
        assertNull(opt.getOpt());
        assertEquals("long-name", opt.getLongOpt());
    }

    @Test
    void builderRejectsNullOptionAndNullLongOpt() {
        // builder created with null short opt and no longOpt set must fail on build
        Object builder = Option.builder(null);
        assertThrows(IllegalStateException.class, () -> {
            buildOptionFromBuilder(builder);
        });
    }

    @Test
    void builderCreatesBuilderAndBuildsOption_withSingleChar() {
        Object builder = Option.builder("a");
        assertNotNull(builder);
        Option option = buildOptionFromBuilder(builder);
        assertEquals("a", option.getOpt());
    }

    @Test
    void builderCreatesBuilderAndBuildsOption_withMultiChar() {
        Object builder = Option.builder("ab");
        assertNotNull(builder);
        Option option = buildOptionFromBuilder(builder);
        assertEquals("ab", option.getOpt());
    }

    @Test
    void builderAllowsAdditionalOptionChar_questionMark() {
        Object builder = Option.builder("?");
        assertNotNull(builder);
        Option option = buildOptionFromBuilder(builder);
        assertEquals("?", option.getOpt());
    }

    @Test
    void builderThrowsOnEmptyOption() {
        assertThrows(IllegalArgumentException.class, () -> Option.builder(""));
    }

    @Test
    void builderThrowsOnIllegalStartingChar() {
        assertThrows(IllegalArgumentException.class, () -> Option.builder("!"));
    }

    @Test
    void builderThrowsOnIllegalInnerChar() {
        // first char valid ('a'), inner char '!' is invalid and should trigger validation failure
        assertThrows(IllegalArgumentException.class, () -> Option.builder("a!"));
    }
}
