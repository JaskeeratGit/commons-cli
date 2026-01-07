package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * JUnit 5 tests for CommandLine.hasOption(char)
 *
 * The tests instantiate CommandLine via its private constructor using reflection,
 * and create Option instances via reflection to match the runtime library constructors.
 */
public class CommandLine_hasOption_52_0_Test {

    /**
     * Helper to create a CommandLine instance using the private constructor:
     * CommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler)
     */
    private CommandLine createCommandLine(List<String> args, List<Option> options, Consumer<Option> deprecatedHandler) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, java.util.function.Consumer.class);
        ctor.setAccessible(true);
        return ctor.newInstance(args, options, deprecatedHandler);
    }

    /**
     * Create an org.apache.commons.cli.Option instance using reflection,
     * trying a few common constructor signatures that may exist in different versions
     * of commons-cli. If a constructor has more parameters, reasonable defaults are supplied.
     */
    private Option createOption(String opt) throws Exception {
        Class<?> optClass = Class.forName("org.apache.commons.cli.Option");
        // Try common String-based constructor signatures
        try {
            Constructor<?> c = optClass.getConstructor(String.class, String.class);
            return (Option) c.newInstance(opt, (String) null);
        } catch (NoSuchMethodException ignored) {
        }
        try {
            Constructor<?> c = optClass.getConstructor(String.class, boolean.class, String.class);
            return (Option) c.newInstance(opt, false, (String) null);
        } catch (NoSuchMethodException ignored) {
        }
        try {
            Constructor<?> c = optClass.getConstructor(String.class, String.class, boolean.class, String.class);
            return (Option) c.newInstance(opt, null, false, (String) null);
        } catch (NoSuchMethodException ignored) {
        }
        // Try char-based constructors if opt is single-character
        if (opt != null && opt.length() == 1) {
            char ch = opt.charAt(0);
            try {
                Constructor<?> c = optClass.getConstructor(char.class, String.class);
                return (Option) c.newInstance(ch, (String) null);
            } catch (NoSuchMethodException ignored) {
            }
            try {
                Constructor<?> c = optClass.getConstructor(char.class, boolean.class, String.class);
                return (Option) c.newInstance(ch, false, (String) null);
            } catch (NoSuchMethodException ignored) {
            }
        }
        // Fallback: find any constructor whose first parameter is String and fill defaults for others
        for (Constructor<?> c : optClass.getConstructors()) {
            Class<?>[] pts = c.getParameterTypes();
            if (pts.length >= 1 && pts[0] == String.class) {
                Object[] args = new Object[pts.length];
                args[0] = opt;
                for (int i = 1; i < pts.length; i++) {
                    if (pts[i] == String.class)
                        args[i] = null;
                    else if (pts[i] == boolean.class || pts[i] == Boolean.class)
                        args[i] = false;
                    else if (pts[i] == int.class || pts[i] == Integer.class)
                        args[i] = 0;
                    else
                        args[i] = null;
                }
                return (Option) c.newInstance(args);
            }
            // also allow fallback for char first parameter if opt is single char
            if (opt != null && opt.length() == 1 && pts.length >= 1 && (pts[0] == char.class || pts[0] == Character.class)) {
                Object[] args = new Object[pts.length];
                args[0] = opt.charAt(0);
                for (int i = 1; i < pts.length; i++) {
                    if (pts[i] == String.class)
                        args[i] = null;
                    else if (pts[i] == boolean.class || pts[i] == Boolean.class)
                        args[i] = false;
                    else if (pts[i] == int.class || pts[i] == Integer.class)
                        args[i] = 0;
                    else
                        args[i] = null;
                }
                return (Option) c.newInstance(args);
            }
        }
        throw new IllegalStateException("No suitable Option constructor found in runtime org.apache.commons.cli.Option");
    }

    @Test
    public void testHasOptionChar_presentLetter() throws Exception {
        Option o = createOption("a");
        List<Option> opts = new ArrayList<>();
        opts.add(o);
        CommandLine cl = createCommandLine(new ArrayList<>(), opts, null);
        assertTrue(cl.hasOption('a'));
        assertFalse(cl.hasOption('b'));
    }

    @Test
    public void testHasOptionChar_digit() throws Exception {
        Option o = createOption("1");
        List<Option> opts = new ArrayList<>();
        opts.add(o);
        CommandLine cl = createCommandLine(new ArrayList<>(), opts, null);
        assertTrue(cl.hasOption('1'));
        assertFalse(cl.hasOption('2'));
    }

    @Test
    public void testHasOptionChar_nonAlphanumericChar() throws Exception {
        // Do not attempt to create an Option with name "-" (invalid in some commons-cli versions).
        // Instead, ensure that a CommandLine with only normal options does not report a non-alphanumeric option as present.
        Option o = createOption("x");
        List<Option> opts = new ArrayList<>();
        opts.add(o);
        CommandLine cl = createCommandLine(new ArrayList<>(), opts, null);
        // '-' should not be reported as present (we never created an option named "-")
        assertFalse(cl.hasOption('-'));
        // some other non-alphanumeric char likewise should be absent
        assertFalse(cl.hasOption('@'));
    }

    @Test
    public void testHasOptionChar_presentShortName() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Option short name "a"
        options.add(createOption("a"));
        CommandLine cmd = createCommandLine(args, options, null);
        assertTrue(cmd.hasOption('a'), "Expected hasOption('a') to be true when an Option with opt 'a' is present");
        assertFalse(cmd.hasOption('b'), "Expected hasOption('b') to be false when no Option with opt 'b' is present");
    }

    @Test
    public void testHasOptionChar_absent() throws Exception {
        List<String> args = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        // Option with different short names
        options.add(createOption("x"));
        options.add(createOption("y"));
        CommandLine cmd = createCommandLine(args, options, o -> {
            /* noop deprecated handler */
        });
        assertFalse(cmd.hasOption('a'), "Expected hasOption('a') to be false when no matching Option exists");
        assertTrue(cmd.hasOption('x'), "Expected hasOption('x') to be true for matching option 'x'");
    }
}
