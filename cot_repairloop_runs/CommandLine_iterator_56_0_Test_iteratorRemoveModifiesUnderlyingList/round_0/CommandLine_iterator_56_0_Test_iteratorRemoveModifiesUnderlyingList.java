package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_iterator_56_0_Test_iteratorRemoveModifiesUnderlyingList {

    @Test
    public void iteratorRemoveModifiesUnderlyingList() throws Exception {
        CommandLine cmd = new CommandLine();
        List<Option> options = new ArrayList<>();
        // Use the real Option constructors from commons-cli (avoid providing a test-local Option stub)
        options.add(new Option("x", "desc-x"));
        options.add(new Option("y", "desc-y"));
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(cmd, options);
        Iterator<Option> it = cmd.iterator();
        assertTrue(it.hasNext());
        Option first = it.next();
        assertEquals("x", first.getOpt());
        // remove the last returned element via iterator
        it.remove();
        // underlying list must reflect removal
        assertEquals(1, options.size());
        assertEquals("y", options.get(0).getOpt());
    }

}
