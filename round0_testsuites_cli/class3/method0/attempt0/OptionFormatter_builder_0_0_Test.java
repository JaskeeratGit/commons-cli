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

    @Test
    public void testPrivateConstructorAndPublicAccessorsDoNotThrow() throws Exception {
        // Obtain a Builder instance via the public builder() factory
        Object builder = OptionFormatter.builder();
        assertNotNull(builder);
        // Find the private constructor OptionFormatter(Option, Builder)
        Class<?> builderClass = builder.getClass();
        Constructor<?> constructor = OptionFormatter.class.getDeclaredConstructor(Option.class, builderClass);
        constructor.setAccessible(true);
        // Instantiate OptionFormatter using reflection (pass null Option to exercise constructor path)
        Object optionFormatterInstance = constructor.newInstance((Option) null, builder);
        assertNotNull(optionFormatterInstance, "Instance created via private constructor should not be null");
        // Invoke public accessor methods to ensure they execute without throwing
        Method getArgName = OptionFormatter.class.getMethod("getArgName");
        Method getBothOpt = OptionFormatter.class.getMethod("getBothOpt");
        Method getDescription = OptionFormatter.class.getMethod("getDescription");
        Method getLongOpt = OptionFormatter.class.getMethod("getLongOpt");
        Method getOpt = OptionFormatter.class.getMethod("getOpt");
        Method getSince = OptionFormatter.class.getMethod("getSince");
        Method isRequired = OptionFormatter.class.getMethod("isRequired");
        Method toOptional = OptionFormatter.class.getMethod("toOptional", String.class);
        Method toSyntaxOptionNoArg = OptionFormatter.class.getMethod("toSyntaxOption");
        Method toSyntaxOptionBool = OptionFormatter.class.getMethod("toSyntaxOption", boolean.class);
        Object resArgName = getArgName.invoke(optionFormatterInstance);
        Object resBothOpt = getBothOpt.invoke(optionFormatterInstance);
        Object resDescription = getDescription.invoke(optionFormatterInstance);
        Object resLongOpt = getLongOpt.invoke(optionFormatterInstance);
        Object resOpt = getOpt.invoke(optionFormatterInstance);
        Object resSince = getSince.invoke(optionFormatterInstance);
        Object resIsRequired = isRequired.invoke(optionFormatterInstance);
        Object resToOptional = toOptional.invoke(optionFormatterInstance, "text");
        Object resToSyntaxNoArg = toSyntaxOptionNoArg.invoke(optionFormatterInstance);
        Object resToSyntaxBool = toSyntaxOptionBool.invoke(optionFormatterInstance, Boolean.TRUE);
        // Validate returned types where applicable and that no method returned an unexpected type
        assertTrue(resIsRequired instanceof Boolean, "isRequired should return a boolean (boxed)");
        // Other getters may validly return null or String; ensure they don't return unexpected object types
        assertTrue(resArgName == null || resArgName instanceof String, "getArgName should return String or null");
        assertTrue(resBothOpt == null || resBothOpt instanceof String, "getBothOpt should return String or null");
        assertTrue(resDescription == null || resDescription instanceof String, "getDescription should return String or null");
        assertTrue(resLongOpt == null || resLongOpt instanceof String, "getLongOpt should return String or null");
        assertTrue(resOpt == null || resOpt instanceof String, "getOpt should return String or null");
        assertTrue(resSince == null || resSince instanceof String, "getSince should return String or null");
        assertTrue(resToOptional == null || resToOptional instanceof String, "toOptional should return String or null");
        assertTrue(resToSyntaxNoArg == null || resToSyntaxNoArg instanceof String, "toSyntaxOption() should return String or null");
        assertTrue(resToSyntaxBool == null || resToSyntaxBool instanceof String, "toSyntaxOption(boolean) should return String or null");
    }
}
