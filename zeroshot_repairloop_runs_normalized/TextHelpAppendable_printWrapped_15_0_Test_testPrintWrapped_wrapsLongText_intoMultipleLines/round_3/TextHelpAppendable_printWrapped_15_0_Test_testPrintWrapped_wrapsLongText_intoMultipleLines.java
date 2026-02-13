package org.apache.commons.cli.help;

import java.lang.reflect.Method;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for TextHelpAppendable#printWrapped(String)
 */
public class TextHelpAppendable_printWrapped_15_0_Test_testPrintWrapped_wrapsLongText_intoMultipleLines {

    private static final String SAMPLE_TEXT = "one two three four five six seven eight nine ten eleven twelve";

    private CapturingTextHelpAppendable subject;
    private StringBuilder output;

    @BeforeEach
    public void setUp() {
        // Use a StringBuilder as the Appendable target (actual output is not important for these tests)
        output = new StringBuilder();
        subject = new CapturingTextHelpAppendable(output);
    }

    @Test
    public void testPrintWrapped_wrapsLongText_intoMultipleLines() throws Exception {
        // configure style to force wrapping at small width
        // small width forces wrapping
        subject.getTextStyleBuilder().setMaxWidth(10).setLeftPad(1).setIndent(0);
        // call the focal method
        subject.printWrapped(SAMPLE_TEXT);

        // For correctness assert that the same result is produced by directly invoking makeColumnQueue (via reflection).
        // This checks that printWrapped indeed produced the same lines as makeColumnQueue.
        Method makeColumnQueue = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        makeColumnQueue.setAccessible(true);
        TextStyle style = subject.getTextStyleBuilder().get();
        @SuppressWarnings("unchecked")
        Queue<String> expectedQueue = (Queue<String>) makeColumnQueue.invoke(subject, SAMPLE_TEXT, style);

        // Build actual lines from the captured Appendable output
        String out = output.toString();
        List<String> actualLines = new ArrayList<>(Arrays.asList(out.split("\\r?\\n", -1)));

        List<String> expectedLines = new ArrayList<>(expectedQueue);

        // If the implementation appends a trailing newline, split will produce a trailing empty string.
        // Remove a single trailing empty element if present and not expected.
        if (!actualLines.isEmpty() && actualLines.get(actualLines.size() - 1).isEmpty()
                && expectedLines.size() == actualLines.size() - 1) {
            actualLines.remove(actualLines.size() - 1);
        }

        // The makeColumnQueue may produce lines padded with trailing spaces to the column width,
        // whereas the actual Appendable output typically does not include those trailing spaces.
        // Normalize both expected and actual by trimming only trailing spaces before comparison.
        for (int i = 0; i < actualLines.size(); i++) {
            actualLines.set(i, rtrim(actualLines.get(i)));
        }
        for (int i = 0; i < expectedLines.size(); i++) {
            expectedLines.set(i, rtrim(expectedLines.get(i)));
        }

        assertEquals(expectedLines.size(), actualLines.size(), "Wrapped line count should match expected makeColumnQueue result");
        assertEquals(expectedLines, actualLines, "Wrapped output contents should match the queue produced by makeColumnQueue");
    }

    private static String rtrim(String s) {
        if (s == null) {
            return null;
        }
        int end = s.length();
        while (end > 0 && s.charAt(end - 1) == ' ') {
            end--;
        }
        return s.substring(0, end);
    }

    /**
     * A small subclass to provide an Appendable target for the base class to write into.
     * We do not attempt to override private methods of the base class.
     */
    private static class CapturingTextHelpAppendable extends TextHelpAppendable {

        CapturingTextHelpAppendable(Appendable output) {
            super(output);
        }
    }
}
