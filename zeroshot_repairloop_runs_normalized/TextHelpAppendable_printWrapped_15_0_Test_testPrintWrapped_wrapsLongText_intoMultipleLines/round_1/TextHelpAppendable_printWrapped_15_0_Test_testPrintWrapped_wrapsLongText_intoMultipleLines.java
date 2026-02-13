package org.apache.commons.cli.help;

import java.lang.reflect.Method;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Unit tests for TextHelpAppendable#printWrapped(String)
 */
public class TextHelpAppendable_printWrapped_15_0_Test_testPrintWrapped_wrapsLongText_intoMultipleLines {

    private static final String SAMPLE_TEXT = "one two three four five six seven eight nine ten eleven twelve";

    private CapturingTextHelpAppendable subject;

    @BeforeEach
    public void setUp() {
        // Use a StringBuilder as the Appendable target (actual output is not important for these tests)
        subject = new CapturingTextHelpAppendable(new StringBuilder());
    }

    @Test
    public void testPrintWrapped_wrapsLongText_intoMultipleLines() throws Exception {
        // configure style to force wrapping at small width
        // small width forces wrapping
        // small width forces wrapping
        subject.getTextStyleBuilder().// a left pad so produced lines will have a leading space
        setMaxWidth(// a left pad so produced lines will have a leading space
        10).setLeftPad(1).setIndent(0);
        // call the focal method
        subject.printWrapped(SAMPLE_TEXT);
        // capturedQueue must have been set by overridden printQueue
        Queue<String> captured = subject.getCapturedQueue();
        assertNotNull(captured, "printQueue should have been invoked and capturedQueue set");
        // For correctness assert that the same result is produced by directly invoking makeColumnQueue (via reflection).
        // This checks that printWrapped indeed passed the makeColumnQueue result into printQueue.
        Method makeColumnQueue = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        makeColumnQueue.setAccessible(true);
        TextStyle style = subject.getTextStyleBuilder().get();
        @SuppressWarnings("unchecked")
        Queue<String> expected = (Queue<String>) makeColumnQueue.invoke(subject, SAMPLE_TEXT, style);
        assertEquals(expected.size(), captured.size(), "Wrapped line count should match expected makeColumnQueue result");
        assertEquals(expected, captured, "Wrapped queue contents should match the queue produced by makeColumnQueue");
    }



    /**
     * A small subclass to capture the Queue argument passed to printQueue when printWrapped is invoked.
     * The override signature is intentionally minimal to remain compatible regardless of checked exceptions
     * declared on the base class method.
     */
    private static class CapturingTextHelpAppendable extends TextHelpAppendable {

        private Queue<String> capturedQueue;

        CapturingTextHelpAppendable(Appendable output) {
            super(output);
        }

        protected void printQueue(final Queue<String> queue) {
            // capture the queue reference for assertions
            this.capturedQueue = queue;
            // do not forward; we only need to capture for verification
        }

        Queue<String> getCapturedQueue() {
            return capturedQueue;
        }
    }
}
