package org.apache.commons.cli.help;

import java.lang.reflect.Field;
import java.util.Queue;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

/**
 * Unit tests for TextHelpAppendable#printWrapped(String)
 */
public class TextHelpAppendable_printWrapped_15_0_Test_testPrivateBlankLineConstant_isEmptyString {

    private static final String SAMPLE_TEXT = "one two three four five six seven eight nine ten eleven twelve";

    private CapturingTextHelpAppendable subject;

    @BeforeEach
    public void setUp() {
        // Use a StringBuilder as the Appendable target (actual output is not important for these tests)
        subject = new CapturingTextHelpAppendable(new StringBuilder());
    }

    @Test
    public void testPrivateBlankLineConstant_isEmptyString() throws Exception {
        // use reflection to access private static BLANK_LINE
        Field blankLineField = TextHelpAppendable.class.getDeclaredField("BLANK_LINE");
        blankLineField.setAccessible(true);
        Object value = blankLineField.get(null);
        assertTrue(value instanceof String, "BLANK_LINE should be a String");
        assertEquals("", value, "BLANK_LINE should be the empty string");
    }

    /**
     * A small subclass to capture the Queue argument passed to printQueue when printWrapped is invoked.
     * The override signature was removed to avoid conflicts with the actual declaration in the superclass,
     * which may be final/static or otherwise incompatible with overriding from the test.
     *
     * Note: This subclass is retained so tests can construct a TextHelpAppendable with a known Appendable.
     */
    private static class CapturingTextHelpAppendable extends TextHelpAppendable {

        private Queue<String> capturedQueue;

        CapturingTextHelpAppendable(Appendable output) {
            super(output);
        }

        // Intentionally not annotated with @Override because the superclass method may be declared
        // in a way that cannot be overridden (e.g. final or static) in some library versions.
        // Keeping this method (even if not actually invoked by the superclass) avoids compile-time errors.
        protected void printQueue(final Queue<String> queue) throws IOException {
            // capture the queue reference for assertions if this method is ever called
            this.capturedQueue = queue;
            // do not forward; we only need to capture for verification
        }

        Queue<String> getCapturedQueue() {
            return capturedQueue;
        }
    }
}
