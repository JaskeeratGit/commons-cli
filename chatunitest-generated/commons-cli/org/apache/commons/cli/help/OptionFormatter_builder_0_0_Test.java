package org.apache.commons.cli.help;

import org.apache.commons.cli.Option;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.cli.DeprecatedAttributes;

public class OptionFormatter_builder_0_0_Test {

    @Test
    public void testBuilderReturnsDistinctBuilderInstances() {
        Object builder1 = OptionFormatter.builder();
        Object builder2 = OptionFormatter.builder();
        assertNotNull(builder1, "builder() should not return null");
        assertNotNull(builder2, "builder() should not return null on subsequent call");
        assertNotSame(builder1, builder2, "builder() should produce distinct Builder instances");
        assertEquals(builder1.getClass(), builder2.getClass(), "Both builders should be same runtime class");
        // Ensure the builder is the nested Builder of OptionFormatter
        assertEquals(OptionFormatter.class, builder1.getClass().getEnclosingClass(), "Builder should be enclosed by OptionFormatter");
    }
}
