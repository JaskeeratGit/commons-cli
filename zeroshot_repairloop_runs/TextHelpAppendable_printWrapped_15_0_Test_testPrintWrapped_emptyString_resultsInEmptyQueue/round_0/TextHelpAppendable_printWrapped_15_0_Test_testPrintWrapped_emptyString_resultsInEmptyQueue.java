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
public class TextHelpAppendable_printWrapped_15_0_Test_testPrintWrapped_emptyString_resultsInEmptyQueue {

    private static final String SAMPLE_TEXT = "one two three four five six seven eight nine ten eleven twelve";

    private CapturingTextHelpAppendable subject;

    @BeforeEach
    public void setUp() {
        // Use a StringBuilder as the Appendable target (actual output is not important for these tests)
        subject = new CapturingTextHelpAppendable(new StringBuilder());
    }


    @Test
    public void testPrintWrapped_emptyString_resultsInEmptyQueue() throws Exception {
        subject.getTextStyleBuilder().setMaxWidth(20).setLeftPad(0).setIndent(0);
        // call the focal method with empty string
        subject.printWrapped("");
        Queue<String> captured = subject.getCapturedQueue();
        assertNotNull(captured, "printQueue should have been invoked even for empty input");
        assertTrue(captured.isEmpty(), "Queue produced for empty input should be empty");
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

        // Removed @Override because the base class method may not be visible/overridable in all versions.
        protected void printQueue(final Queue<String> queue) throws IOException {
            // capture the queue reference for assertions
            this.capturedQueue = queue;
            // do not forward; we only need to capture for verification
        }

        Queue<String> getCapturedQueue() {
            return capturedQueue;
        }
    }
}
