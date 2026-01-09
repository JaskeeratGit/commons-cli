package org.apache.commons.cli.help;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TextHelpAppendable#printWrapped(String)
 */
public class TextHelpAppendable_printWrapped_15_0_Test_testPrintWrapped_emptyString_resultsInEmptyQueue {

    private TextHelpAppendable subject;
    private StringBuilder output;

    @BeforeEach
    public void setUp() {
        // Use a StringBuilder as the Appendable target (actual output is what we'll assert)
        output = new StringBuilder();
        subject = new TextHelpAppendable(output);
    }

    @Test
    public void testPrintWrapped_emptyString_resultsInEmptyQueue() throws Exception {
        // configure the TextStyle via the builder on the subject
        subject.getTextStyleBuilder().setMaxWidth(20).setLeftPad(0).setIndent(0);

        // call the focal method with empty string
        subject.printWrapped("");

        // For empty input the output Appendable should remain empty
        assertNotNull(output, "Appendable should be present");
        assertEquals("", output.toString(), "No characters should have been appended for empty input");
    }
}
