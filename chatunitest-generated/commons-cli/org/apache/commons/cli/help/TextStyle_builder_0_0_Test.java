package org.apache.commons.cli.help;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public class TextStyle_builder_0_0_Test {

    @Test
    void builderReturnsNewBuilderInstances() {
        Object b1 = TextStyle.builder();
        Object b2 = TextStyle.builder();
        assertNotNull(b1, "builder() should not return null");
        assertNotNull(b2, "builder() should not return null on subsequent call");
        assertNotSame(b1, b2, "builder() should return a new Builder instance on each call");
        assertEquals(b1.getClass(), b2.getClass(), "Both builder instances should be of the same Builder class");
        String simpleName = b1.getClass().getSimpleName().toLowerCase();
        assertTrue(simpleName.contains("builder"), "Returned object's class name should indicate it's a Builder");
        // If Builder is a nested class it is typically static; check the modifier if available
        assertTrue(Modifier.isStatic(b1.getClass().getModifiers()) || b1.getClass().getEnclosingClass() == TextStyle.class, "Builder should be a nested static class of TextStyle or at least nested within TextStyle");
    }

    @Test
    void builderGetProducesTextStyleAndAccessorsAreCallable() throws Exception {
        Object builder = TextStyle.builder();
        assertNotNull(builder, "builder() should not return null");
        // Invoke public get() on Builder to obtain a TextStyle instance
        Method getMethod = builder.getClass().getMethod("get");
        Object textStyle = getMethod.invoke(builder);
        assertNotNull(textStyle, "Builder.get() should produce a TextStyle instance");
        assertTrue(textStyle instanceof TextStyle, "Builder.get() should return an instance of TextStyle");
        // toString should not throw and should return a non-null string
        String s = textStyle.toString();
        assertNotNull(s, "toString() should return a non-null string");
        // Call several public accessors reflectively to ensure they exist and are callable
        Method isScalable = textStyle.getClass().getMethod("isScalable");
        Object scalable = isScalable.invoke(textStyle);
        assertNotNull(scalable, "isScalable() should be callable and return a Boolean");
        assertTrue(scalable instanceof Boolean, "isScalable() should return a boolean value");
        Method getMinWidth = textStyle.getClass().getMethod("getMinWidth");
        Object minW = getMinWidth.invoke(textStyle);
        assertNotNull(minW, "getMinWidth() should be callable and return an int (boxed)");
        assertTrue(minW instanceof Integer, "getMinWidth() should return an int value");
        Method getMaxWidth = textStyle.getClass().getMethod("getMaxWidth");
        Object maxW = getMaxWidth.invoke(textStyle);
        assertNotNull(maxW, "getMaxWidth() should be callable and return an int (boxed)");
        assertTrue(maxW instanceof Integer, "getMaxWidth() should return an int value");
        Method getIndent = textStyle.getClass().getMethod("getIndent");
        Object indent = getIndent.invoke(textStyle);
        assertNotNull(indent, "getIndent() should be callable and return an int (boxed)");
        assertTrue(indent instanceof Integer, "getIndent() should return an int value");
        Method getLeftPad = textStyle.getClass().getMethod("getLeftPad");
        Object leftPad = getLeftPad.invoke(textStyle);
        assertNotNull(leftPad, "getLeftPad() should be callable and return an int (boxed)");
        assertTrue(leftPad instanceof Integer, "getLeftPad() should return an int value");
        // pad method: signature pad(boolean, CharSequence)
        Method padMethod = textStyle.getClass().getMethod("pad", boolean.class, CharSequence.class);
        Object r = padMethod.invoke(textStyle, true, "x");
        assertNotNull(r, "pad(...) should be callable and return a CharSequence (or non-null result)");
        assertTrue(r instanceof CharSequence, "pad(...) should return a CharSequence");
    }

    @Test
    void privateConstructorCanBeInvokedWithBuilder() throws Exception {
        Object builder = TextStyle.builder();
        assertNotNull(builder, "builder() should not return null");
        // Obtain the private constructor TextStyle(Builder) and invoke it
        Constructor<TextStyle> ctor = TextStyle.class.getDeclaredConstructor(builder.getClass());
        ctor.setAccessible(true);
        TextStyle ts = ctor.newInstance(builder);
        assertNotNull(ts, "Invoking the private TextStyle(Builder) constructor should produce a TextStyle instance");
        assertTrue(ts instanceof TextStyle, "Constructed object should be an instance of TextStyle");
        // Basic sanity: toString and isScalable shouldn't throw
        assertNotNull(ts.toString(), "toString on constructed TextStyle should not be null");
        Method isScalable = ts.getClass().getMethod("isScalable");
        Object scalable = isScalable.invoke(ts);
        assertNotNull(scalable, "isScalable() on constructed TextStyle should be callable");
    }

    @Test
    void constantsAndDefaultInstance() {
        assertEquals(Integer.MAX_VALUE, TextStyle.UNSET_MAX_WIDTH, "UNSET_MAX_WIDTH should equal Integer.MAX_VALUE");
        assertNotNull(TextStyle.DEFAULT, "DEFAULT should be initialized and not null");
        assertTrue(TextStyle.DEFAULT instanceof TextStyle, "DEFAULT should be an instance of TextStyle");
    }
}
