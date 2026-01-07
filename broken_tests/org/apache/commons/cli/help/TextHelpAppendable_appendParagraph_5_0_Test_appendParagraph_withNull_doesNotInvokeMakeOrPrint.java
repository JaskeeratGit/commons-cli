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

class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withNull_doesNotInvokeMakeOrPrint {

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

        @Override
        protected void printQueue(final Queue<String> queue) throws IOException {
            printQueueCalled = true;
            // capture the exact queue instance that appendParagraph passes
            this.capturedQueue = queue;
        }
    }

    @Test
    void appendParagraph_withNull_doesNotInvokeMakeOrPrint() throws Exception {
        final TestableTextHelpAppendable t = new TestableTextHelpAppendable(new StringBuilder());
        // null should be treated as empty by Util.isEmpty -> no calls
        t.appendParagraph(null);
        assertFalse(t.makeColumnQueueCalled, "makeColumnQueue should not be called for null");
        assertFalse(t.printQueueCalled, "printQueue should not be called for null");
        assertNull(t.capturedQueue, "No queue should be captured for null input");
    }




}
