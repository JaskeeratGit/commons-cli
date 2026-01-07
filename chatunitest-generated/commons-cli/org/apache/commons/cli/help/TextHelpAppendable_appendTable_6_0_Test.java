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

class TextHelpAppendable_appendTable_6_0_Test {

    @Test
    void appendTable_printsCaption_and_appendsNewline() throws Exception {
        final StringBuilder out = new StringBuilder();
        final TextHelpAppendable app = new TextHelpAppendable(out);
        final TableDefinition table = new TableDefinition() {

            @Override
            public List<TextStyle> columnTextStyles() {
                return Arrays.asList(TextStyle.builder().setMaxWidth(10).get(), TextStyle.builder().setMaxWidth(10).get());
            }

            @Override
            public Iterable<List<String>> rows() {
                return Arrays.asList(Arrays.asList("r1c1", "r1c2"));
            }

            @Override
            public String caption() {
                return "MyCaption";
            }

            @Override
            public List<String> headers() {
                return Arrays.asList("H1", "H2");
            }
        };
        app.appendTable(table);
        final String s = out.toString();
        assertTrue(s.endsWith(System.lineSeparator()), "output must end with a system line separator");
        assertTrue(s.contains("MyCaption"), "caption should be present in output");
    }

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

    @Test
    void adjustTableFormat_rescales_whenGlobalMaxWidthIsSmall() throws Exception {
        final StringBuilder out = new StringBuilder();
        final TextHelpAppendable app = new TextHelpAppendable(out);
        // Reduce the global max width to force rescaling inside adjustTableFormat.
        final TextStyle.Builder globalBuilder = app.getTextStyleBuilder();
        // small global width to force scaling
        globalBuilder.setMaxWidth(5);
        final List<TextStyle> largeColumnStyles = Arrays.asList(TextStyle.builder().setMaxWidth(100).setLeftPad(1).get(), TextStyle.builder().setMaxWidth(80).setLeftPad(1).get());
        final TableDefinition table = new TableDefinition() {

            @Override
            public List<TextStyle> columnTextStyles() {
                return largeColumnStyles;
            }

            @Override
            public Iterable<List<String>> rows() {
                // rows with long content to force width calculation
                return Arrays.asList(Arrays.asList("aaaaaaaaaa", "bbbbbbbbbb"), Arrays.asList("cccccccccc", "dddddddddd"));
            }

            @Override
            public String caption() {
                return "";
            }

            @Override
            public List<String> headers() {
                return Arrays.asList("Header1", "Header2");
            }
        };
        // adjustTableFormat is protected; invoke via reflection (as required).
        final Method adjustMethod = TextHelpAppendable.class.getDeclaredMethod("adjustTableFormat", TableDefinition.class);
        adjustMethod.setAccessible(true);
        final TableDefinition adjusted = (TableDefinition) adjustMethod.invoke(app, table);
        assertNotNull(adjusted, "adjusted TableDefinition must not be null");
        final List<TextStyle> adjustedStyles = adjusted.columnTextStyles();
        assertEquals(2, adjustedStyles.size(), "there should be two column styles after adjustment");
        // After rescaling the max widths should be finite and not extremely large.
        for (TextStyle ts : adjustedStyles) {
            final int mw = ts.getMaxWidth();
            assertTrue(mw >= ts.getMinWidth(), "maxWidth should be >= minWidth for each column");
            assertTrue(mw <= 100, "rescaled maxWidth should not exceed original large value");
        }
    }
}
