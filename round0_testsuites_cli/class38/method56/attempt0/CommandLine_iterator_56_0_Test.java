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

public class CommandLine_iterator_56_0_Test {

    @Test
    public void iteratorReturnsOptionsInOrder() throws Exception {
        // create instance via protected no-arg constructor
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
        assertEquals(List.of("a", "b", "c"), collected);
    }

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

    @Test
    public void invokePrivateConstructorViaReflectionAndUseIterator() throws Exception {
        // Prepare options list to pass to constructor (and later set explicitly)
        List<Option> options = new LinkedList<>();
        options.add(new Option("p"));
        options.add(new Option("q"));
        // Invoke the private constructor reflectively to satisfy requirement of using reflection
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        // We call the constructor but do not rely on its internal initialization (it may be empty in the provided snippet)
        CommandLine cmdFromPrivateCtor = ctor.newInstance(new ArrayList<>(), new ArrayList<>(), (Consumer<Option>) o -> {
        });
        assertNotNull(cmdFromPrivateCtor);
        // Ensure iterator works by explicitly setting the private field afterwards
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(cmdFromPrivateCtor, options);
        Iterator<Option> it = cmdFromPrivateCtor.iterator();
        List<String> collected = new ArrayList<>();
        while (it.hasNext()) {
            collected.add(it.next().getOpt());
        }
        assertEquals(List.of("p", "q"), collected);
    }
}

// Minimal Option stub for tests (package-private, same package as CommandLine)
class Option {

    private final String opt;

    Option(String opt) {
        this.opt = opt;
    }

    String getOpt() {
        return opt;
    }

    @Override
    public String toString() {
        return opt;
    }
}
