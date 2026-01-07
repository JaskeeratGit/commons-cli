package org.apache.commons.cli.help;

import sun.misc.Unsafe;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

public final class TextStyle_toString_8_0_Test {

    private static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    private static void removeFinalModifier(Field field) throws Exception {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }

    private static void setFieldValue(Object target, String fieldName, Object value) throws Exception {
        Field field = TextStyle.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        // remove final if present
        if (Modifier.isFinal(field.getModifiers())) {
            removeFinalModifier(field);
        }
        field.set(target, value);
    }

    private static Object getSomeAlignmentConstant() throws Exception {
        try {
            Class<?> alignmentClass = Class.forName("org.apache.commons.cli.help.Alignment");
            Object[] consts = alignmentClass.getEnumConstants();
            if (consts != null && consts.length > 0) {
                return consts[0];
            } else {
                // If it's not enum or empty, try to find a public static field
                for (Field f : alignmentClass.getFields()) {
                    if (Modifier.isStatic(f.getModifiers())) {
                        return f.get(null);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            // If Alignment is not present, fall through to null (we will handle null in tests)
        }
        return null;
    }

    private static TextStyle allocateTextStyle(Object alignment, int leftPad, int indent, boolean scalable, int minWidth, int maxWidth) throws Exception {
        Unsafe unsafe = getUnsafe();
        TextStyle ts = (TextStyle) unsafe.allocateInstance(TextStyle.class);
        setFieldValue(ts, "alignment", alignment);
        setFieldValue(ts, "leftPad", leftPad);
        setFieldValue(ts, "indent", indent);
        setFieldValue(ts, "scalable", scalable);
        setFieldValue(ts, "minWidth", minWidth);
        setFieldValue(ts, "maxWidth", maxWidth);
        return ts;
    }

    @Test
    public void testToStringWithUnsetMaxWidth() throws Exception {
        Object alignment = getSomeAlignmentConstant();
        int leftPad = 2;
        int indent = 3;
        boolean scalable = true;
        int minWidth = 1;
        int maxWidth = TextStyle.UNSET_MAX_WIDTH;
        TextStyle ts = allocateTextStyle(alignment, leftPad, indent, scalable, minWidth, maxWidth);
        String expected = String.format("TextStyle{%s, l:%s, i:%s, %s, min:%s, max:%s}", alignment, leftPad, indent, scalable, minWidth, "unset");
        assertEquals(expected, ts.toString());
    }

    @Test
    public void testToStringWithSetMaxWidth() throws Exception {
        Object alignment = getSomeAlignmentConstant();
        int leftPad = 5;
        int indent = 7;
        boolean scalable = false;
        int minWidth = 0;
        int maxWidth = 50;
        TextStyle ts = allocateTextStyle(alignment, leftPad, indent, scalable, minWidth, maxWidth);
        String expected = String.format("TextStyle{%s, l:%s, i:%s, %s, min:%s, max:%s}", alignment, leftPad, indent, scalable, minWidth, maxWidth);
        assertEquals(expected, ts.toString());
    }

    @Test
    public void testToStringWithNullAlignment() throws Exception {
        Object alignment = null;
        int leftPad = 0;
        int indent = 0;
        boolean scalable = false;
        int minWidth = 0;
        int maxWidth = 10;
        TextStyle ts = allocateTextStyle(alignment, leftPad, indent, scalable, minWidth, maxWidth);
        String expected = String.format("TextStyle{%s, l:%s, i:%s, %s, min:%s, max:%s}", "null", leftPad, indent, scalable, minWidth, maxWidth);
        assertEquals(expected, ts.toString());
    }
}
