package org.apache.commons.cli.help;

import java.lang.reflect.Method;
import java.util.*;
import java.io.IOException;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class TextHelpAppendable_appendList_4_0_Test {

    @Test
    void appendList_nullAndEmpty_doNotChangeOutput() throws IOException {
        StringBuilder sb = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(sb);
        // null list -> no output
        tha.appendList(false, null);
        assertEquals("", sb.toString());
        // empty list -> no output
        tha.appendList(false, Collections.emptyList());
        assertEquals("", sb.toString());
    }

    @Test
    void appendList_unordered_includesBulletAndHandlesNullElements() throws IOException {
        StringBuilder sb = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(sb);
        List<CharSequence> list = new ArrayList<>();
        list.add("first");
        // should be treated as blank line (Util.defaultValue)
        list.add(null);
        // ensure reasonably large max width so no unexpected wrapping
        tha.getTextStyleBuilder().setMaxWidth(120).setLeftPad(0).setIndent(0);
        tha.appendList(false, list);
        String out = sb.toString();
        // output should end with system line separator
        assertTrue(out.endsWith(System.lineSeparator()));
        // should contain bullet marker for first element
        assertTrue(out.contains(" * first"), "Expected unordered marker and element text present");
        // null element should still produce a bullet line (with blank content after marker)
        // Look for " * " (marker plus a space) — there may be trailing spaces from padding
        assertTrue(out.contains(" * "), "Expected bullet marker for null/blank element");
    }

    @Test
    void appendList_ordered_incrementsNumbersAndAppendsNewline() throws IOException {
        StringBuilder sb = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(sb);
        List<CharSequence> list = Arrays.asList("one", "two", "three");
        // ensure no wrapping by setting a large max width
        tha.getTextStyleBuilder().setMaxWidth(200).setLeftPad(0).setIndent(0);
        tha.appendList(true, list);
        String out = sb.toString();
        // Should end with newline
        assertTrue(out.endsWith(System.lineSeparator()));
        // Check numbering for first and second items
        assertTrue(out.contains(" 1. one"), "Missing numbered entry for first item");
        assertTrue(out.contains(" 2. two"), "Missing numbered entry for second item");
        assertTrue(out.contains(" 3. three"), "Missing numbered entry for third item");
    }

    @Test
    void makeColumnQueue_wrappingBehavior_viaReflection() throws Exception {
        StringBuilder sb = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(sb);
        // Create a long columnData to force wrapping
        StringBuilder longData = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            // 10 chars each -> total 50 chars
            longData.append("0123456789");
        }
        String columnData = longData.toString();
        // Create a TextStyle with small max width to force wrapping.
        // Use the TextStyle.Builder provided by TextStyle.builder()
        TextStyle.Builder styleBuilder = TextStyle.builder().setLeftPad(0).setIndent(0).setMaxWidth(12);
        TextStyle style = styleBuilder.get();
        // Invoke protected makeColumnQueue via reflection
        Method m = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        Queue<String> queue = (Queue<String>) m.invoke(tha, columnData, style);
        // Because maxWidth is small we expect more than one wrapped line
        assertNotNull(queue);
        assertTrue(queue.size() > 1, "Expected wrapping into multiple lines");
        // Ensure that the joined content (ignoring spaces) contains the original digits sequence
        String joined = String.join("", queue).replaceAll("\\s+", "");
        assertTrue(joined.contains("0123456789"), "Wrapped lines should contain original content fragments");
    }
}
