package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandLine_iterator_56_0_Test_invokePrivateConstructorViaReflectionAndUseIterator {

    @Test
    public void invokePrivateConstructorViaReflectionAndUseIterator() throws Exception {
        // Prepare options list to pass to constructor (and later set explicitly)
        List<Option> options = new LinkedList<>();
        options.add(Option.builder("p").build());
        options.add(Option.builder("q").build());
        // Invoke the private constructor reflectively to satisfy requirement of using reflection
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, java.util.function.Consumer.class);
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
        assertEquals(Arrays.asList("p", "q"), collected);
    }
}
