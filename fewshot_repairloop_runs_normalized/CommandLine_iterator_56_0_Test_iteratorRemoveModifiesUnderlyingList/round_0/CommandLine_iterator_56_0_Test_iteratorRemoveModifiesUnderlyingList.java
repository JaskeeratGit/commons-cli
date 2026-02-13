package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_iterator_56_0_Test_iteratorRemoveModifiesUnderlyingList {

    @Test
    public void iteratorRemoveModifiesUnderlyingList() throws Exception {
        CommandLine cmd = new CommandLine();
        List<Option> options = new ArrayList<>();
        options.add(new Option("x"));
        options.add(new Option("y"));
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
