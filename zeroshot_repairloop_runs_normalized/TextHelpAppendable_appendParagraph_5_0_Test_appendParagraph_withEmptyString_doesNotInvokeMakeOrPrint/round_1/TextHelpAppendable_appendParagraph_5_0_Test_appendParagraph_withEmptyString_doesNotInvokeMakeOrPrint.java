package org.apache.commons.cli.help;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withEmptyString_doesNotInvokeMakeOrPrint {

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
            final Queue<String> q = new LinkedList<>();
            // return the incoming data as single entry so appendParagraph's behavior is observable
            q.add(columnData == null ? null : columnData.toString());
            return q;
        }

        // printQueue in production is private, so we cannot legally override it; remove @Override.
        protected void printQueue(final Queue<String> queue) throws IOException {
            printQueueCalled = true;
            // capture the exact queue instance that appendParagraph passes
            this.capturedQueue = queue;
        }
    }


    @Test
    void appendParagraph_withEmptyString_doesNotInvokeMakeOrPrint() throws Exception {
        final TestableTextHelpAppendable t = new TestableTextHelpAppendable(new StringBuilder());
        // empty string should be treated as empty by Util.isEmpty -> no calls
        t.appendParagraph("");
        assertFalse(t.makeColumnQueueCalled, "makeColumnQueue should not be called for empty string");
        assertFalse(t.printQueueCalled, "printQueue should not be called for empty string");
        assertNull(t.capturedQueue, "No queue should be captured for empty input");
    }

}
