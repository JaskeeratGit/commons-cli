package org.apache.commons.cli.help;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Queue;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class TextHelpAppendable_appendHeader_3_0_Test {

    private RecordingAppendable out;

    private TextHelpAppendable tha;

    @BeforeEach
    void setup() {
        out = new RecordingAppendable();
        tha = new TextHelpAppendable(out);
    }

    @Test
    void testAppendHeaderDoesNothingForNullOrEmpty() throws IOException {
        // null should be treated as empty -> nothing appended
        tha.appendHeader(1, (CharSequence) null);
        assertEquals(0, out.getLength(), "Nothing should have been appended for null text");
        // empty string should be treated as empty -> nothing appended
        tha.appendHeader(1, "");
        assertEquals(0, out.getLength(), "Nothing should have been appended for empty text");
    }

    @Test
    void testAppendHeaderThrowsForInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> tha.appendHeader(0, "x"));
        assertThrows(IllegalArgumentException.class, () -> tha.appendHeader(-2, "x"));
    }

    @Test
    void testAppendHeaderWritesFillCharsForLevel1() throws IOException {
        final String text = "Hello";
        // default leftPad is 1 and default max width is large, so repeated fill count = text.length()
        tha.appendHeader(1, text);
        String output = out.getContent();
        String expectedFillLine = Util.repeatSpace(tha.getTextStyleBuilder().get().getLeftPad()) + Util.repeat(Math.min(text.length(), tha.getTextStyleBuilder().get().getMaxWidth()), '=');
        assertTrue(output.contains(expectedFillLine), () -> "Output should contain fill line: [" + expectedFillLine + "] but was: [" + output + "]");
    }

    @Test
    void testAppendHeaderUsesLastFillCharWhenLevelGreaterThanFillArrayAndRespectsLeftPad() throws IOException {
        final String text = "Hello";
        // set a non-default left pad to verify Util.repeatSpace usage
        tha.getTextStyleBuilder().setLeftPad(3);
        // level 5 should use last fill char '_' (fillChars length is 4)
        tha.appendHeader(5, text);
        String output = out.getContent();
        String expectedFillLine = Util.repeatSpace(3) + Util.repeat(Math.min(text.length(), tha.getTextStyleBuilder().get().getMaxWidth()), '_');
        assertTrue(output.contains(expectedFillLine), () -> "Output should contain fill line with last fill char and leftPad=3: [" + expectedFillLine + "] but was: [" + output + "]");
    }

    @Test
    void testMakeColumnQueueInvokedViaReflectionProducesExpectedLeadingLeftPad() throws Exception {
        final String text = "Hello";
        final TextStyle style = tha.getTextStyleBuilder().get();
        // Invoke protected makeColumnQueue via reflection
        Method m = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        Queue<String> queue = (Queue<String>) m.invoke(tha, text, style);
        assertNotNull(queue, "Queue returned by makeColumnQueue should not be null");
        assertFalse(queue.isEmpty(), "Queue should contain at least one element for non-empty text");
        String first = queue.peek();
        assertNotNull(first, "First element should not be null");
        String lpad = Util.repeatSpace(style.getLeftPad());
        assertTrue(first.startsWith(lpad), () -> "First line returned by makeColumnQueue should start with leftPad spaces \"" + lpad + "\". Got: \"" + first + "\"");
    }

    @Test
    void testAccessPrivateBlankLineFieldViaReflection() throws Exception {
        // Ensure the private static BLANK_LINE is present and equals empty string
        Field f = TextHelpAppendable.class.getDeclaredField("BLANK_LINE");
        f.setAccessible(true);
        Object val = f.get(null);
        assertTrue(val instanceof String);
        assertEquals("", val);
    }

    // Simple Appendable implementation to capture appended content
    private static class RecordingAppendable implements Appendable {

        private final StringBuilder sb = new StringBuilder();

        @Override
        public Appendable append(CharSequence csq) {
            if (csq != null) {
                sb.append(csq);
            } else {
                sb.append("null");
            }
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            if (csq == null) {
                sb.append("null", start, end);
            } else {
                sb.append(csq, start, end);
            }
            return this;
        }

        @Override
        public Appendable append(char c) {
            sb.append(c);
            return this;
        }

        String getContent() {
            return sb.toString();
        }

        int getLength() {
            return sb.length();
        }
    }
}
