package org.apache.commons.cli.help;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TextHelpAppendable.appendParagraph to ensure no make/print interactions happen
 * when an empty string is passed.
 */
class TextHelpAppendable_appendParagraph_5_0_Test_appendParagraph_withEmptyString_doesNotInvokeMakeOrPrint {

    @Test
    void appendParagraph_withEmptyString_doesNotInvokeMakeOrPrint() throws Exception {
        // prepare a real instance with a backing StringBuilder so we can assert no output was produced
        final StringBuilder out = new StringBuilder();
        final TextHelpAppendable real = new TextHelpAppendable(out);
        final TextHelpAppendable spy = spy(real);

        // call the method under test with an empty string
        spy.appendParagraph("");

        // verify that makeColumnQueue was never invoked
        verify(spy, never()).makeColumnQueue(any(CharSequence.class), any(TextStyle.class));

        // verify nothing was written to the backing StringBuilder (i.e. printQueue was not effectively invoked)
        assertEquals("", out.toString());
    }
}
