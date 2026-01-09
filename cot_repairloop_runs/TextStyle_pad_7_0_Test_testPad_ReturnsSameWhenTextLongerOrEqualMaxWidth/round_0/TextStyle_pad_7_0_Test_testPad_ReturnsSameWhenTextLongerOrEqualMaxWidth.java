package org.apache.commons.cli.help;

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TextStyle.pad(boolean, CharSequence)
 */
public class TextStyle_pad_7_0_Test_testPad_ReturnsSameWhenTextLongerOrEqualMaxWidth {

    private static TextStyle createTextStyle(String alignmentName, int leftPad, int indent, boolean scalable, int minWidth, int maxWidth) throws Exception {
        // allocate instance without invoking constructor
        Unsafe unsafe = getUnsafe();
        Object instance = unsafe.allocateInstance(TextStyle.class);

        // set alignment enum constant by name via reflection to avoid compile-time dependency on Alignment type
        Field alignField = TextStyle.class.getDeclaredField("alignment");
        alignField.setAccessible(true);
        Class<?> alignClass = alignField.getType();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Enum<?> alignmentValue = Enum.valueOf((Class) alignClass, alignmentName);
        alignField.set(instance, alignmentValue);

        setField(instance, "leftPad", leftPad);
        setField(instance, "indent", indent);
        setField(instance, "scalable", scalable);
        setField(instance, "minWidth", minWidth);
        setField(instance, "maxWidth", maxWidth);
        return (TextStyle) instance;
    }

    private static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = TextStyle.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testPad_ReturnsSameWhenTextLongerOrEqualMaxWidth() throws Exception {
        // use alignment name string to avoid compile-time dependency on an Alignment type
        TextStyle ts = createTextStyle("LEFT", 0, 0, false, 0, 3);
        String text = "abcd";
        CharSequence result = ts.pad(false, text);
        // should return the same CharSequence instance when text.length() >= maxWidth
        assertSame(text, result);
    }
}
