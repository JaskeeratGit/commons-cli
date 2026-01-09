package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Test that verifies the private constructor sets fields correctly and fields are accessible via reflection.
 */
public class CommandLine_getParsedOptionValue_27_0_Test_testPrivateConstructor_assignsFields_andAccessibleViaReflection {

    @Test
    public void testPrivateConstructor_assignsFields_andAccessibleViaReflection() throws Exception {
        List<String> args = new LinkedList<>();
        args.add("arg1");
        final List<Option> options = new ArrayList<>();
        options.add(new Option());
        Consumer<Option> handler = o -> {
            // no-op for test
        };
        // Obtain the private constructor and create an instance.
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        CommandLine cmd = ctor.newInstance(args, options, handler);
        // Reflectively access private fields to ensure they were set by the constructor
        Field argsField = CommandLine.class.getDeclaredField("args");
        argsField.setAccessible(true);
        Object actualArgs = argsField.get(cmd);
        assertSame(args, actualArgs);
        Field optionsField = CommandLine.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        Object actualOptions = optionsField.get(cmd);
        assertSame(options, actualOptions);
        Field deprecatedHandlerField = CommandLine.class.getDeclaredField("deprecatedHandler");
        deprecatedHandlerField.setAccessible(true);
        Object actualHandler = deprecatedHandlerField.get(cmd);
        assertSame(handler, actualHandler);
    }
}

/*
 * Minimal supporting stubs to allow this test to compile and run independently.
 * These match the minimal signatures referenced by CommandLine and the tests.
 * In a real project these would be provided by the library under test.
 */
class Option {
    private final String opt;
    private final String longOpt;
    private final boolean hasArg;
    private final String description;
    private final List<String> valuesList = new ArrayList<>();

    public Option() {
        this.opt = null;
        this.longOpt = null;
        this.hasArg = false;
        this.description = null;
    }

    public Option(final String opt, final String description) {
        this(opt, null, false, description);
    }

    public Option(final String opt, final boolean hasArg, final String description) {
        this(opt, null, hasArg, description);
    }

    public Option(final String opt, final String longOpt, final boolean hasArg, final String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
    }

    public List<String> getValuesList() {
        return valuesList;
    }

    public void addValueForProcessing(final String value) {
        this.valuesList.add(value);
    }

    public String[] getValues() {
        if (valuesList.isEmpty()) {
            return null;
        }
        return valuesList.toArray(new String[0]);
    }

    public String getValue() {
        return valuesList.isEmpty() ? null : valuesList.get(0);
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public String getDescription() {
        return description;
    }
}

class ParseException extends Exception {

    public ParseException(final String message) {
        super(message);
    }
}

class Builder {

    // Provide a default deprecated handler used by the protected CommandLine() constructor.
    static final Consumer<Option> DEPRECATED_HANDLER = o -> {
        // no-op stub
    };
}
