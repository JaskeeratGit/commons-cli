package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public class CommandLine_getOptionValue_20_0_Test_getOptionValue_resolvesLongOption_withLeadingHyphens {

    // Helper to instantiate CommandLine via its private constructor
    private CommandLine createCommandLineWithOptions(final List<Option> options) throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        final List<String> args = new ArrayList<>();
        // pass null for deprecatedHandler
        return ctor.newInstance(args, options, (Consumer<Option>) null);
    }

    private void setOptionValueReflectively(Option opt, String value) throws Exception {
        Class<?> cls = opt.getClass();
        // try known methods
        try {
            Method m = cls.getMethod("addValueForProcessing", String.class);
            m.invoke(opt, value);
            return;
        } catch (NoSuchMethodException ignored) {}
        try {
            Method m = cls.getMethod("addValue", String.class);
            m.invoke(opt, value);
            return;
        } catch (NoSuchMethodException ignored) {}
        try {
            Method m = cls.getMethod("setValue", String.class);
            m.invoke(opt, value);
            return;
        } catch (NoSuchMethodException ignored) {}
        try {
            Method m = cls.getMethod("setValues", String[].class);
            m.invoke(opt, (Object) new String[] { value });
            return;
        } catch (NoSuchMethodException ignored) {}

        // try fields
        Field f = null;
        try {
            f = cls.getDeclaredField("values");
        } catch (NoSuchFieldException e) {
            try {
                f = cls.getDeclaredField("value");
            } catch (NoSuchFieldException ex) {
                try {
                    f = cls.getDeclaredField("valuesList");
                } catch (NoSuchFieldException ex2) {
                    // leave f null
                }
            }
        }
        if (f != null) {
            f.setAccessible(true);
            Object fieldVal = f.get(opt);
            if (fieldVal == null) {
                if (List.class.isAssignableFrom(f.getType())) {
                    List<String> list = new ArrayList<>();
                    list.add(value);
                    f.set(opt, list);
                    return;
                }
                if (f.getType().isArray()) {
                    Object arr = Array.newInstance(f.getType().getComponentType(), 1);
                    Array.set(arr, 0, value);
                    f.set(opt, arr);
                    return;
                }
                f.set(opt, value);
                return;
            } else {
                if (fieldVal instanceof List) {
                    ((List) fieldVal).add(value);
                    return;
                }
                if (fieldVal.getClass().isArray()) {
                    int len = Array.getLength(fieldVal);
                    Object arr = Array.newInstance(fieldVal.getClass().getComponentType(), len + 1);
                    System.arraycopy(fieldVal, 0, arr, 0, len);
                    Array.set(arr, len, value);
                    f.set(opt, arr);
                    return;
                }
                f.set(opt, value);
                return;
            }
        }

        throw new IllegalStateException("Could not set option value reflectively");
    }

    @Test
    public void getOptionValue_resolvesLongOption_withLeadingHyphens() throws Exception {
        Option opt = new Option("c", true, "long opt");
        opt.setLongOpt("charlie");
        setOptionValueReflectively(opt, "longValue");
        List<Option> opts = new ArrayList<>();
        opts.add(opt);
        CommandLine cmd = createCommandLineWithOptions(opts);
        // with single or double hyphens or bare name, resolveOption should match the longOpt
        assertEquals("longValue", cmd.getOptionValue("charlie"));
        assertEquals("longValue", cmd.getOptionValue("-charlie"));
        assertEquals("longValue", cmd.getOptionValue("--charlie"));
    }
}
