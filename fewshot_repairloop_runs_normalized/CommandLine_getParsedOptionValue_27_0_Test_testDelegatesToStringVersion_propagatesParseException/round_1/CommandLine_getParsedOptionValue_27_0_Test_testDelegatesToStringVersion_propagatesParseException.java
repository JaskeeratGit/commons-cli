package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public class CommandLine_getParsedOptionValue_27_0_Test_testDelegatesToStringVersion_propagatesParseException {


    @Test
    public void testDelegatesToStringVersion_propagatesParseException() {
        CommandLine cmd = new CommandLine() {

            @Override
            public <T> T getParsedOptionValue(final String optionName) throws ParseException {
                throw new ParseException("simulated failure for: " + optionName);
            }
        };
        assertThrows(ParseException.class, () -> cmd.getParsedOptionValue('z'));
    }

}

/*
 * Minimal supporting stubs to allow this test to compile and run independently.
 * These match the minimal signatures referenced by CommandLine and the tests.
 * In a real project these would be provided by the library under test.
 */
class Option {
    private String opt;
    private String longOpt;
    private boolean hasArg;
    private String description;
    private final List<String> values = new ArrayList<>();

    public Option() {
    }

    public Option(final String opt, final String description) {
        this.opt = opt;
        this.description = description;
    }

    public Option(final String opt, final boolean hasArg, final String description) {
        this.opt = opt;
        this.hasArg = hasArg;
        this.description = description;
    }

    public Option(final String opt, final String longOpt, final boolean hasArg, final String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArg = hasArg;
        this.description = description;
    }

    public List<String> getValuesList() {
        return values;
    }

    public String[] getValues() {
        if (values.isEmpty()) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    public String getValue() {
        return values.isEmpty() ? null : values.get(0);
    }

    public void addValueForProcessing(final String value) {
        values.add(value);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        final Option option = (Option) o;
        return Objects.equals(opt, option.opt) && Objects.equals(longOpt, option.longOpt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opt, longOpt);
    }

    @Override
    public String toString() {
        return "Option{opt=" + opt + ", longOpt=" + longOpt + "}";
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
