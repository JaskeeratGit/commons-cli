package org.apache.commons.cli.help;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
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
import java.util.List;
import java.util.Set;

class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withWhitespace_consideredNonEmpty_andPassedThrough {

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

        // Do not use @Override because printQueue is not necessarily overridable in all versions.
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
        assertNotNull(t.capturedQueue);
        final Object[] arr = t.capturedQueue.toArray();
        assertEquals(payload, arr[0], "Whitespace payload should be preserved in the returned queue element");
        assertEquals("", arr[1], "A blank line should still be appended");
    }

}
