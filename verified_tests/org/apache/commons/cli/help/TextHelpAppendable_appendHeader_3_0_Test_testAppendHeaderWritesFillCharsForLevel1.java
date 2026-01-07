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

class TextHelpAppendable_appendHeader_3_0_Test_testAppendHeaderWritesFillCharsForLevel1 {

    private RecordingAppendable out;

    private TextHelpAppendable tha;

    @BeforeEach
    void setup() {
        out = new RecordingAppendable();
        tha = new TextHelpAppendable(out);
    }



    @Test
    void testAppendHeaderWritesFillCharsForLevel1() throws IOException {
        final String text = "Hello";
        // default leftPad is 1 and default max width is large, so repeated fill count = text.length()
        tha.appendHeader(1, text);
        String output = out.getContent();
        String expectedFillLine = Util.repeatSpace(tha.getTextStyleBuilder().get().getLeftPad()) + Util.repeat(Math.min(text.length(), tha.getTextStyleBuilder().get().getMaxWidth()), '=');
        assertTrue(output.contains(expectedFillLine), () -> "Output should contain fill line: [" + expectedFillLine + "] but was: [" + output + "]");
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
