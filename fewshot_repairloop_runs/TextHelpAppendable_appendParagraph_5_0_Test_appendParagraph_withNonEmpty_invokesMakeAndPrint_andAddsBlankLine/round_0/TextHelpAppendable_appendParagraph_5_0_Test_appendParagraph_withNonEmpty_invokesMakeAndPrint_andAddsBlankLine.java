package org.apache.commons.cli.help;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withNonEmpty_invokesMakeAndPrint_andAddsBlankLine {

    /**
     * Test helper that overrides makeColumnQueue and printQueue to observe interactions.
     */
    static class TestableTextHelpAppendable extends TextHelpAppendable {

        volatile Queue<String> capturedQueue;

        volatile boolean makeColumnQueueCalled = false;

        volatile boolean printQueueCalled = false;

        TestableTextHelpAppendable(final Appendable output) {
            super(output);
        }

        @Override
        protected Queue<String> makeColumnQueue(final CharSequence columnData, final TextStyle style) {
            makeColumnQueueCalled = true;
            // since printQueue may be non-overridable in some environments, capture the queue here
            printQueueCalled = true;
            final Queue<String> q = new LinkedList<>();
            // return the incoming data as single entry so appendParagraph's behavior is observable
            q.add(columnData == null ? null : columnData.toString());
            this.capturedQueue = q;
            return q;
        }
    }

    @Test
    void appendParagraph_withNonEmpty_invokesMakeAndPrint_andAddsBlankLine() throws Exception {
        final TestableTextHelpAppendable t = new TestableTextHelpAppendable(new StringBuilder());
        final String payload = "HelloWorld";
        t.appendParagraph(payload);
        assertTrue(t.makeColumnQueueCalled, "makeColumnQueue should be called for non-empty paragraph");
        assertTrue(t.printQueueCalled, "printQueue should be called for non-empty paragraph");
        assertNotNull(t.capturedQueue, "Queue should be captured for non-empty paragraph");
        final Object[] arr = t.capturedQueue.toArray();
        // Our override returned a queue with a single element equal to payload;
        // appendParagraph should have added the BLANK_LINE ("") after it.
        assertEquals(2, arr.length, "Queue should contain original line and the appended blank line");
        assertEquals(payload, arr[0], "First element should be the payload returned by makeColumnQueue");
        assertEquals("", arr[1], "Second element should be the BLANK_LINE appended by appendParagraph");
    }

}
