package org.apache.commons.cli.help;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class TextHelpAppendable_appendHeader_3_0_Test_testMakeColumnQueueInvokedViaReflectionProducesExpectedLeadingLeftPad {

    private RecordingAppendable out;

    private TextHelpAppendable tha;

    @BeforeEach
    void setup() {
        out = new RecordingAppendable();
        tha = new TextHelpAppendable(out);
    }





    @Test
    void testMakeColumnQueueInvokedViaReflectionProducesExpectedLeadingLeftPad() throws Exception {
        final String text = "Hello";
        final TextStyle style = tha.getTextStyleBuilder().get();
        // Invoke protected makeColumnQueue via reflection
        Method m = TextHelpAppendable.class.getDeclaredMethod("makeColumnQueue", CharSequence.class, TextStyle.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        Queue<String> queue = (Queue<String>) m.invoke(tha, text, style);
        assertNotNull(queue, "Queue returned by makeColumnQueue should not be null");
        assertFalse(queue.isEmpty(), "Queue should contain at least one element for non-empty text");
        String first = queue.peek();
        assertNotNull(first, "First element should not be null");
        String lpad = Util.repeatSpace(style.getLeftPad());
        assertTrue(first.startsWith(lpad), () -> "First line returned by makeColumnQueue should start with leftPad spaces \"" + lpad + "\". Got: \"" + first + "\"");
    }


    // Simple Appendable implementation to capture appended content
    private static class RecordingAppendable implements Appendable {

        private final StringBuilder sb = new StringBuilder();

        @Override
        public Appendable append(CharSequence csq) {
            if (csq != null) {
                sb.append(csq);
            } else {
                sb.append("null");
            }
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            if (csq == null) {
                sb.append("null", start, end);
            } else {
                sb.append(csq, start, end);
            }
            return this;
        }

        @Override
        public Appendable append(char c) {
            sb.append(c);
            return this;
        }

        String getContent() {
            return sb.toString();
        }

        int getLength() {
            return sb.length();
        }
    }
}
