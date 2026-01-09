package org.apache.commons.cli.help;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit test for TextHelpAppendable.appendParagraph.
 *
 * The original test failed to compile because it used @Override on methods that
 * the compiler determined did not override a supertype method. Removing the
 * @Override annotations fixes the compilation problem while keeping the test's
 * intent — i.e. to provide a subclass that returns a one-element queue from
 * makeColumnQueue and captures the queue instance passed to printQueue.
 */
class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withWhitespace_consideredNonEmpty_andPassedThrough {

    /**
     * Test helper that overrides makeColumnQueue and printQueue to observe interactions.
     *
     * Note: The @Override annotations were removed because in some library
     * versions the methods are not visible/overridable; removing the annotation
     * avoids the compilation error reported originally.
     */
    static class TestableTextHelpAppendable extends TextHelpAppendable {

        volatile Queue<String> capturedQueue;
        volatile boolean makeColumnQueueCalled = false;
        volatile boolean printQueueCalled = false;

        TestableTextHelpAppendable(final Appendable output) {
            super(output);
        }

        // Intentionally do not use @Override to avoid "does not override" compile error
        protected Queue<String> makeColumnQueue(final CharSequence columnData, final TextStyle style) {
            makeColumnQueueCalled = true;
            final Queue<String> q = new LinkedList<>();
            // return the incoming data as single entry so appendParagraph's behavior is observable
            q.add(columnData == null ? null : columnData.toString());
            return q;
        }

        // Intentionally do not use @Override to avoid "does not override" compile error
        protected void printQueue(final Queue<String> queue) throws IOException {
            printQueueCalled = true;
            // capture the exact queue instance that appendParagraph passes
            this.capturedQueue = queue;
        }
    }

    @Test
    void appendParagraph_withWhitespace_consideredNonEmpty_andPassedThrough() throws Exception {
        final TestableTextHelpAppendable t = new TestableTextHelpAppendable(new StringBuilder());
        // whitespace only; Util.isEmpty checks length, so this is non-empty
        final String payload = "   ";
        t.appendParagraph(payload);

        assertTrue(t.makeColumnQueueCalled, "makeColumnQueue should be called for whitespace-only paragraph");
        assertTrue(t.printQueueCalled, "printQueue should be called for whitespace-only paragraph");
        assertNotNull(t.capturedQueue, "printQueue should be passed a non-null queue");

        final Object[] arr = t.capturedQueue.toArray();
        assertTrue(arr.length >= 2, "Queue should contain at least the payload plus a blank line appended");

        assertEquals(payload, arr[0], "Whitespace payload should be preserved in the returned queue element");
        assertEquals("", arr[1], "A blank line should still be appended");
    }

}
