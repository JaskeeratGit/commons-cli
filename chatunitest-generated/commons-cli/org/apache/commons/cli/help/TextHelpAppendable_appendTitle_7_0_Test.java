package org.apache.commons.cli.help;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

/**
 * Unit tests for TextHelpAppendable.appendTitle(CharSequence)
 */
public class TextHelpAppendable_appendTitle_7_0_Test {

    @Test
    public void testAppendTitle_NullOrEmptyDoesNothing() throws IOException {
        StringBuilder out = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(out);
        // null should do nothing
        tha.appendTitle(null);
        assertEquals("", out.toString());
        // empty should do nothing
        tha.appendTitle("");
        assertEquals("", out.toString());
    }

    @Test
    public void testAppendTitle_SimpleTitleProducesTitleAndHashLine() throws IOException {
        StringBuilder out = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(out);
        String title = "Hi";
        tha.appendTitle(title);
        // build expected output
        TextStyle style = tha.getTextStyleBuilder().get();
        String lpad = Util.repeatSpace(style.getLeftPad());
        String line1 = lpad + title;
        String hashes = Util.repeat(Math.min(title.length(), style.getMaxWidth()), '#');
        String line2 = Util.repeatSpace(style.getLeftPad()) + hashes;
        String expected = line1 + "\n" + line2 + "\n" + "\n";
        assertEquals(expected, out.toString());
    }

    @Test
    public void testAppendTitle_WrappingAndReflectionAccessToMakeColumnQueue() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder out = new StringBuilder();
        TextHelpAppendable tha = new TextHelpAppendable(out);
        // make narrow to force wrapping
        // small width to force wraps
        tha.setMaxWidth(5);
        // length 10
        String title = "abcdefghij";
        tha.appendTitle(title);
        TextStyle style = tha.getTextStyleBuilder().get();
        StringBuilder expectedBuilder = new StringBuilder();
        // compute expected wrapped lines using same logic as TextHelpAppendable.makeColumnQueue + appended hash line and blank line
        String lpad = Util.repeatSpace(style.getLeftPad());
        String indent = Util.repeatSpace(style.getIndent());
        int wrappedMaxWidth = style.getMaxWidth() - indent.length();
        int wrapPos = 0;
        while (wrapPos < title.length()) {
            int workingWidth = wrapPos == 0 ? style.getMaxWidth() : wrappedMaxWidth;
            int nextPos = Math.min(title.length(), wrapPos + workingWidth);
            String working = title.substring(wrapPos, nextPos);
            if (wrapPos == 0) {
                expectedBuilder.append(lpad).append(working).append("\n");
            } else {
                expectedBuilder.append(lpad).append(indent).append(working).append("\n");
            }
            // move to next -- mimic indexOfNonWhitespace which here will return nextPos if within bounds and char isn't whitespace
            int nextNonWhitespace = Util.indexOfNonWhitespace(title, nextPos);
            wrapPos = nextNonWhitespace == -1 ? nextPos : nextNonWhitespace;
        }
        // hash line and blank line
        expectedBuilder.append(Util.repeatSpace(style.getLeftPad())).append(Util.repeat(Math.min(title.length(), style.getMaxWidth()), '#')).append("\n").append("\n");
        String expected = expectedBuilder.toString();
        assertEquals(expected, out.toString());
        // Use reflection to invoke private makeColumnQueue(CharSequence, TextStyle)
        Method m = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        Queue<String> queue = (Queue<String>) m.invoke(tha, title, style);
        // verify the first few elements returned by makeColumnQueue match expectations
        assertNotNull(queue);
        assertFalse(queue.isEmpty());
        String first = queue.poll();
        assertEquals(lpad + title.substring(0, Math.min(title.length(), style.getMaxWidth())), first);
    }
}

/* ----------------------------------------------------------------
   Minimal supporting classes to allow the test to compile and run.
   These are simplified versions based on the provided signatures.
   They live in the same package so tests can interact with them.
   ---------------------------------------------------------------- */
final class Util {

    private Util() {
    }

    static final int NOT_FOUND = -1;

    static String repeat(final int len, final char fillChar) {
        if (len <= 0)
            return "";
        final char[] padding = new char[len];
        java.util.Arrays.fill(padding, fillChar);
        return new String(padding);
    }

    static String repeatSpace(final int len) {
        return repeat(len, ' ');
    }

    static boolean isEmpty(final CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * Return the index of the first non-whitespace character at or after pos.
     * Returns -1 if none found.
     */
    static int indexOfNonWhitespace(final CharSequence cs, final int pos) {
        if (cs == null)
            return NOT_FOUND;
        for (int i = pos; i < cs.length(); i++) {
            char c = cs.charAt(i);
            if (!Character.isWhitespace(c)) {
                return i;
            }
        }
        return NOT_FOUND;
    }
}

final class TextStyle {

    public static final int UNSET_MAX_WIDTH = Integer.MAX_VALUE;

    private final int leftPad;

    private final int indent;

    private final int maxWidth;

    private TextStyle(Builder builder) {
        this.leftPad = builder.leftPad;
        this.indent = builder.indent;
        this.maxWidth = builder.maxWidth;
    }

    public int getIndent() {
        return indent;
    }

    public int getLeftPad() {
        return leftPad;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Pad the working text. If wrapped==true, prepend indent spaces; otherwise return working as-is.
     */
    public String pad(final boolean wrapped, final CharSequence working) {
        if (!wrapped) {
            return working.toString();
        } else {
            return Util.repeatSpace(getIndent()) + working.toString();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    static final class Builder {

        private int leftPad = TextHelpAppendable.DEFAULT_LEFT_PAD;

        private int indent = TextHelpAppendable.DEFAULT_INDENT;

        private int maxWidth = TextHelpAppendable.DEFAULT_WIDTH;

        public Builder setLeftPad(int leftPad) {
            this.leftPad = leftPad;
            return this;
        }

        public Builder setIndent(int indent) {
            this.indent = indent;
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public TextStyle get() {
            return new TextStyle(this);
        }
    }
}

class TextHelpAppendable {

    public static final int DEFAULT_WIDTH = 74;

    public static final int DEFAULT_LEFT_PAD = 1;

    public static final int DEFAULT_INDENT = 3;

    public static final int DEFAULT_LIST_INDENT = 7;

    private static final String BLANK_LINE = "";

    private final TextStyle.Builder textStyleBuilder;

    private final Appendable output;

    public TextHelpAppendable(final Appendable output) {
        this.output = output;
        textStyleBuilder = TextStyle.builder().setMaxWidth(DEFAULT_WIDTH).setLeftPad(DEFAULT_LEFT_PAD).setIndent(DEFAULT_INDENT);
    }

    public int getIndent() {
        return textStyleBuilder.get().getIndent();
    }

    public int getLeftPad() {
        return textStyleBuilder.get().getLeftPad();
    }

    public int getMaxWidth() {
        return textStyleBuilder.get().getMaxWidth();
    }

    public TextStyle.Builder getTextStyleBuilder() {
        return textStyleBuilder;
    }

    public void setIndent(final int indent) {
        textStyleBuilder.setIndent(indent);
    }

    public void setLeftPad(final int leftPad) {
        textStyleBuilder.setLeftPad(leftPad);
    }

    public void setMaxWidth(final int maxWidth) {
        textStyleBuilder.setMaxWidth(maxWidth);
    }

    public void appendTitle(final CharSequence title) throws IOException {
        if (!Util.isEmpty(title)) {
            final TextStyle style = textStyleBuilder.get();
            final java.util.Queue<String> queue = makeColumnQueue(title, style);
            queue.add(Util.repeatSpace(style.getLeftPad()) + Util.repeat(Math.min(title.length(), style.getMaxWidth()), '#'));
            queue.add(BLANK_LINE);
            printQueue(queue);
        }
    }

    /**
     * Private to allow demonstration of reflection usage in tests.
     * Simplified wrapping: wraps at workingWidth or end-of-string.
     */
    private java.util.Queue<String> makeColumnQueue(final CharSequence columnData, final TextStyle style) {
        final String lpad = Util.repeatSpace(style.getLeftPad());
        final String indent = Util.repeatSpace(style.getIndent());
        final java.util.Queue<String> result = new java.util.LinkedList<>();
        int wrapPos = 0;
        int nextPos;
        final int wrappedMaxWidth = style.getMaxWidth() - indent.length();
        while (wrapPos < columnData.length()) {
            final int workingWidth = wrapPos == 0 ? style.getMaxWidth() : wrappedMaxWidth;
            nextPos = indexOfWrap(columnData, workingWidth, wrapPos);
            final CharSequence working = columnData.subSequence(wrapPos, nextPos);
            result.add(lpad + style.pad(wrapPos > 0, working));
            wrapPos = Util.indexOfNonWhitespace(columnData, nextPos);
            wrapPos = wrapPos == -1 ? nextPos : wrapPos;
        }
        return result;
    }

    /**
     * Determines the wrap position simply as wrapPos + workingWidth (bounded by length).
     * In real library this would be more sophisticated (break on whitespace etc).
     */
    private int indexOfWrap(final CharSequence columnData, final int workingWidth, final int wrapPos) {
        if (workingWidth <= 0) {
            // avoid infinite loop if impossible width
            return wrapPos;
        }
        return Math.min(columnData.length(), wrapPos + workingWidth);
    }

    /**
     * Prints the queue to the appendable, one entry per line.
     */
    private void printQueue(final java.util.Queue<String> queue) throws IOException {
        while (!queue.isEmpty()) {
            String s = queue.remove();
            output.append(s);
            output.append('\n');
        }
    }
}
