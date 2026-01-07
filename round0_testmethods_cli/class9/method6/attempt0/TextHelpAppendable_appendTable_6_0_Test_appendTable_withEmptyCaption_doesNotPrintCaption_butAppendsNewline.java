package org.apache.commons.cli.help;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class TextHelpAppendable_appendTable_6_0_Test_appendTable_withEmptyCaption_doesNotPrintCaption_butAppendsNewline {


    @Test
    void appendTable_withEmptyCaption_doesNotPrintCaption_butAppendsNewline() throws Exception {
        final StringBuilder out = new StringBuilder();
        final TextHelpAppendable app = new TextHelpAppendable(out);
        final TableDefinition table = new TableDefinition() {

            @Override
            public List<TextStyle> columnTextStyles() {
                return Arrays.asList(TextStyle.builder().setMaxWidth(8).get());
            }

            @Override
            public Iterable<List<String>> rows() {
                return Arrays.asList(Arrays.asList("cell1"));
            }

            @Override
            public String caption() {
                // empty caption -> appendParagraph should skip
                return "";
            }

            @Override
            public List<String> headers() {
                return Arrays.asList("H");
            }
        };
        app.appendTable(table);
        final String s = out.toString();
        assertTrue(s.endsWith(System.lineSeparator()), "output must end with a system line separator");
        assertFalse(s.contains("MyCaption"), "should not contain any unrelated caption");
    }

}
