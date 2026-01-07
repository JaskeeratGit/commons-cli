package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValue_38_0_Test_testResolveOption_privateMethod_behavior {

    /**
     * A test subclass of CommandLine that overrides getParsedOptionValue(Option, T)
     * so we can observe delegation from the focal method getParsedOptionValue(String, T).
     */
    static class TestCommandLine extends CommandLine {

        Option lastOption;

        Object lastDefault;

        @Override
        public <T> T getParsedOptionValue(final Option option, final T defaultValue) throws ParseException {
            this.lastOption = option;
            this.lastDefault = defaultValue;
            if (option == null) {
                return defaultValue;
            }
            @SuppressWarnings("unchecked")
            T result = (T) ("parsed:" + option.getOpt());
            return result;
        }
    }




    @Test
    public void testResolveOption_privateMethod_behavior() throws Exception {
        TestCommandLine cmd = new TestCommandLine();
        Option opt = new Option("x", "longx", false, "desc");
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Option> options = (List<Option>) optionsField.get(cmd);
        options.add(opt);
        // access private resolveOption via reflection
        Method resolveMethod = CommandLine.class.getDeclaredMethod("resolveOption", String.class);
        resolveMethod.setAccessible(true);
        // pass a value with hyphens, ensure the same Option instance is returned
        Object returned = resolveMethod.invoke(cmd, "--longx");
        assertTrue(returned instanceof Option);
        assertSame(opt, returned);
        // passing null should return null
        Object returnedNull = resolveMethod.invoke(cmd, (Object) null);
        assertNull(returnedNull);
    }
}
