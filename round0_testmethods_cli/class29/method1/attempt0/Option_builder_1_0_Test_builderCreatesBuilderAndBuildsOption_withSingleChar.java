package org.apache.commons.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

class Option_builder_1_0_Test_builderCreatesBuilderAndBuildsOption_withSingleChar {

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

    @Test
    void builderCreatesBuilderAndBuildsOption_withSingleChar() {
        Object builder = Option.builder("a");
        assertNotNull(builder);
        Option option = buildOptionFromBuilder(builder);
        assertEquals("a", option.getOpt());
    }






}
