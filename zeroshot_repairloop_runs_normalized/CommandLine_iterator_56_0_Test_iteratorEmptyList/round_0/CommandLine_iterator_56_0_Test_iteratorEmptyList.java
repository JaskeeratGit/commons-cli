package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_iterator_56_0_Test_iteratorEmptyList {


    @Test
    public void iteratorEmptyList() throws Exception {
        CommandLine cmd = new CommandLine();
        List<Option> options = new ArrayList<>();
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(cmd, options);
        Iterator<Option> it = cmd.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }


}
