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

class TextHelpAppendable_appendTable_6_0_Test_appendTable_printsCaption_and_appendsNewline {

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


}
