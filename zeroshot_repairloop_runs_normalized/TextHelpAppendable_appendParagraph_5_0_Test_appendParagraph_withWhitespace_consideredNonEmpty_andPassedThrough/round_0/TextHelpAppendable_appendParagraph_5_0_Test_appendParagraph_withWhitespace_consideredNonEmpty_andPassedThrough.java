package org.apache.commons.cli.help;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withWhitespace_consideredNonEmpty_andPassedThrough {

    /**
     * Test helper that overrides makeColumnQueue to observe interactions.
     */
    static class TestableTextHelpAppendable extends TextHelpAppendable {

        volatile Queue<String> capturedQueue;

        volatile boolean makeColumnQueueCalled = false;

        TestableTextHelpAppendable(final Appendable output) {
            super(output);
        }

        @Override
        protected Queue<String> makeColumnQueue(final CharSequence columnData, final TextStyle style) {
            makeColumnQueueCalled = true;
            final Queue<String> q = new LinkedList<>();
            // capture the queue instance so appendParagraph's behavior is observable
            this.capturedQueue = q;
            // return the incoming data as single entry so appendParagraph's behavior is observable
            q.add(columnData == null ? null : columnData.toString());
            return q;
        }

        // Do not attempt to override printQueue since it is not accessible for overriding in the superclass.
    }

    @Test
    void appendParagraph_withWhitespace_consideredNonEmpty_and_passedThrough() throws Exception {
        final TestableTextHelpAppendable t = new TestableTextHelpAppendable(new StringBuilder());
        // whitespace only; Util.isEmpty checks length, so this is non-empty
        final String payload = "   ";
        t.appendParagraph(payload);
        assertTrue(t.makeColumnQueueCalled, "makeColumnQueue should be called for whitespace-only paragraph");
        assertNotNull(t.capturedQueue);
        final Object[] arr = t.capturedQueue.toArray();
        assertEquals(payload, arr[0], "Whitespace payload should be preserved in the returned queue element");
        assertEquals("", arr[1], "A blank line should still be appended");
    }

}
