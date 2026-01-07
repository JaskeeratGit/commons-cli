package org.apache.commons.cli.help;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Queue;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
     * The override signature is intentionally minimal to remain compatible regardless of checked exceptions
     * declared on the base class method.
     */
    private static class CapturingTextHelpAppendable extends TextHelpAppendable {

        private Queue<String> capturedQueue;

        CapturingTextHelpAppendable(Appendable output) {
            super(output);
        }

        @Override
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
