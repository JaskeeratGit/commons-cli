package org.apache.commons.cli.help;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Supplier;

/**
 * Unit tests for TextStyle.pad(boolean, CharSequence)
 */
@ExtendWith(MockitoExtension.class)
public class TextStyle_pad_7_0_Test {

    private static TextStyle createTextStyle(String alignmentName, int leftPad, int indent, boolean scalable, int minWidth, int maxWidth) throws Exception {
        // obtain a default instance via the builder, then override private fields via reflection
        Method builderMethod = TextStyle.class.getDeclaredMethod("builder");
        builderMethod.setAccessible(true);
        Object builder = builderMethod.invoke(null);
        // try to find get() method on builder
        Method getMethod = null;
        try {
            getMethod = builder.getClass().getMethod("get");
        } catch (NoSuchMethodException e) {
            // fallback to build() if get() is not present
            getMethod = builder.getClass().getMethod("build");
        }
        Object tsObj = getMethod.invoke(builder);
        TextStyle instance = (TextStyle) tsObj;
        // set fields reflectively, removing final modifiers if necessary
        setField(instance, "alignment", enumConstant(alignmentName));
        setField(instance, "leftPad", leftPad);
        setField(instance, "indent", indent);
        setField(instance, "scalable", scalable);
        setField(instance, "minWidth", minWidth);
        setField(instance, "maxWidth", maxWidth);
        return instance;
    }

    private static Object enumConstant(String name) throws Exception {
        // Try to find an Alignment enum declared inside TextStyle
        for (Class<?> c : TextStyle.class.getDeclaredClasses()) {
            if ("Alignment".equals(c.getSimpleName()) && c.isEnum()) {
                @SuppressWarnings("unchecked")
                Class<Enum> enumClass = (Class<Enum>) c.asSubclass(Enum.class);
                return Enum.valueOf(enumClass, name);
            }
        }
        // Fallback: try to load by known fully-qualified names
        try {
            Class<?> enumClass = Class.forName("org.apache.commons.cli.help.Alignment");
            @SuppressWarnings("unchecked")
            Class<Enum> enumC = (Class<Enum>) enumClass.asSubclass(Enum.class);
            return Enum.valueOf(enumC, name);
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class<?> enumClass = Class.forName("org.apache.commons.cli.help.TextStyle$Alignment");
            @SuppressWarnings("unchecked")
            Class<Enum> enumC = (Class<Enum>) enumClass.asSubclass(Enum.class);
            return Enum.valueOf(enumC, name);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Alignment enum not found in expected locations");
        }
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = TextStyle.class.getDeclaredField(name);
        field.setAccessible(true);
        // remove final modifier if present
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // some runtimes don't allow modifying modifiers; continue anyway
        }
        field.set(target, value);
    }

    @Test
    public void testPad_ReturnsSameWhenTextLongerOrEqualMaxWidth() throws Exception {
        TextStyle ts = createTextStyle("LEFT", 0, 0, false, 0, 3);
        String text = "abcd";
        CharSequence result = ts.pad(false, text);
        // should return the same CharSequence instance when text.length() >= maxWidth
        assertSame(text, result);
    }

    @Test
    public void testPad_Center_UnsetMaxWidth_AddIndentTrue() throws Exception {
        TextStyle ts = createTextStyle("CENTER", 0, 5, false, 0, TextStyle.UNSET_MAX_WIDTH);
        String text = "x";
        CharSequence result = ts.pad(true, text);
        // indent = 5 -> padLen = 5 -> left = 2, right = 3
        assertEquals("  x   ", result.toString());
    }

    @Test
    public void testPad_Center_WithMaxWidth() throws Exception {
        TextStyle ts = createTextStyle("CENTER", 0, 0, false, 0, 7);
        String text = "abc";
        CharSequence result = ts.pad(false, text);
        // maxWidth=7, text length=3 -> padLen=4 -> left=2, right=2
        assertEquals("  abc  ", result.toString());
    }

    @Test
    public void testPad_Left_UnsetMaxWidth_AddIndent() throws Exception {
        TextStyle ts = createTextStyle("LEFT", 0, 3, false, 0, TextStyle.UNSET_MAX_WIDTH);
        String text = "ab";
        CharSequence result = ts.pad(true, text);
        // addIndent true -> indentPad = 3 spaces, rest = ""
        assertEquals("   ab", result.toString());
    }

    @Test
    public void testPad_Right_WithIndentAndRestReduced() throws Exception {
        TextStyle ts = createTextStyle("RIGHT", 0, 2, false, 0, 8);
        String text = "abc";
        CharSequence result = ts.pad(true, text);
        // maxWidth=8, text len=3 -> restLen=5 > indent(2) -> indentPad=2, restLen -> 3
        // result = indentPad + rest + text => 2 + 3 = 5 spaces before text
        assertEquals("     abc", result.toString());
    }

    @Test
    public void testPad_Right_AddIndentButRestLessOrEqualIndent() throws Exception {
        TextStyle ts = createTextStyle("RIGHT", 0, 3, false, 0, 6);
        // length 5
        String text = "abcde";
        CharSequence result = ts.pad(true, text);
        // maxWidth=6, text len=5 -> restLen=1 <= indent(3) -> indentPad="", rest=1 space
        // result = "" + rest + text
        assertEquals(" abcde", result.toString());
    }
}
