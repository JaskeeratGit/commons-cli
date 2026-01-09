package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Test for CommandLine.iterator()
 */
public class CommandLine_iterator_56_0_Test_invokePrivateConstructorViaReflectionAndUseIterator {

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
        assertEquals(Arrays.asList("p", "q"), collected);
    }
}

// Minimal Option stub for tests (package-private, same package as CommandLine)
class Option {

    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;
    private Class<?> type;
    private Object converter;
    private List<String> values = new ArrayList<>();

    Option(String opt) {
        this(opt, (String) null, false, null);
    }

    Option(String opt, String description) {
        this(opt, (String) null, false, description);
    }

    Option(String opt, boolean hasArg, String description) {
        this(opt, (String) null, hasArg, description);
    }

    Option(String opt, String longOpt, boolean hasArg, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
    }

    String getOpt() {
        return opt;
    }

    String getLongOpt() {
        return longOpt;
    }

    List<String> getValuesList() {
        return values == null ? Collections.<String>emptyList() : new ArrayList<>(values);
    }

    String[] getValues() {
        if (values == null) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    void setType(Class<?> type) {
        this.type = type;
    }

    <T, E extends RuntimeException> void setConverter(org.apache.commons.cli.Converter<T, E> conv) {
        this.converter = conv;
    }

    void addValue(String v) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(v);
    }

    void setValues(List<String> vals) {
        if (vals == null) {
            this.values = null;
        } else {
            this.values = new ArrayList<>(vals);
        }
    }

    @Override
    public String toString() {
        return opt;
    }
}
