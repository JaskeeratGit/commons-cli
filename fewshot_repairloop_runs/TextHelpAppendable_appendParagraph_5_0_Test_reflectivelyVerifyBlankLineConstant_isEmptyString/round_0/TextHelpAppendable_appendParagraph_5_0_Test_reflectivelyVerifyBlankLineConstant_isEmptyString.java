package org.apache.commons.cli.help;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TextHelpAppendable_appendParagraph_5_0_Test_reflectivelyVerifyBlankLineConstant_isEmptyString {

    /**
     * Test helper that overrides makeColumnQueue and captures printQueue interactions.
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

        // printQueue is private in the real class, so this cannot truly override it.
        // Remove @Override to avoid compilation error and still allow the test subclass to capture calls
        // if the real implementation calls a non-private hook; otherwise this method will simply be unused.
        protected void printQueue(final Queue<String> queue) throws IOException {
            printQueueCalled = true;
            // capture the exact queue instance that appendParagraph passes
            this.capturedQueue = queue;
        }
    }

    @Test
    void reflectivelyVerifyBlankLineConstant_isEmptyString() throws Exception {
        final Field f = TextHelpAppendable.class.getDeclaredField("BLANK_LINE");
        f.setAccessible(true);
        final String blank = (String) f.get(null);
        assertEquals("", blank, "BLANK_LINE should be the empty string");
    }
}
