package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for CommandLine.iterator()
 */
public class CommandLine_iterator_56_0_Test_iteratorReturnsOptionsInOrder {

    @Test
    public void iteratorReturnsOptionsInOrder() throws Exception {
        // create instance via protected no-arg constructor (same package so accessible)
        CommandLine cmd = new CommandLine();

        // prepare a mutable list of options
        List<Option> options = new ArrayList<>();
        options.add(new Option("a"));
        options.add(new Option("b"));
        options.add(new Option("c"));

        // set the private final field 'options' via reflection
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(cmd, options);

        // get iterator and collect items
        Iterator<Option> it = cmd.iterator();
        List<String> collected = new ArrayList<>();
        while (it.hasNext()) {
            collected.add(it.next().getOpt());
        }

        assertEquals(Arrays.asList("a", "b", "c"), collected);
    }
}
