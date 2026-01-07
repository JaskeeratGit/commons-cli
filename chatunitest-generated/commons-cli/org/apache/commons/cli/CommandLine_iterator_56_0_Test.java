package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Arrays;
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
