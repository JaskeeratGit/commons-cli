package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.function.Consumer;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Unit tests for DefaultParser.parse(Options, String[]).
 *
 * These tests verify:
 * - parse(Options, String[]) delegates to parse(Options, String[], Properties) with a null Properties.
 * - the private constructor sets internal fields as expected (using reflection).
 * - public constructors set expected default values (using reflection).
 *
 * Note: reflection is used to exercise private constructor and private fields.
 */
public class DefaultParser_parse_23_0_Test {

    @Test
    public void testParseDelegatesToThreeArg() throws Exception {
        Options options = new Options();
        String[] args = new String[] { "one", "two" };
        // Create a small subclass that overrides the 3-arg parse method to record invocation
        class DelegatingParser extends DefaultParser {

            boolean called = false;

            Properties receivedProperties = null;

            final CommandLine returned = new CommandLine();

            public DelegatingParser() {
                super();
            }

            @Override
            public CommandLine parse(final Options opts, final String[] arguments, final Properties properties) throws ParseException {
                this.called = true;
                this.receivedProperties = properties;
                return returned;
            }
        }
        DelegatingParser parser = new DelegatingParser();
        // should delegate to overridden 3-arg method
        CommandLine result = parser.parse(options, args);
        assertTrue(parser.called, "Expected the 3-arg parse method to be called");
        assertNull(parser.receivedProperties, "Expected Properties passed to 3-arg parse to be null");
        assertSame(parser.returned, result, "Expected the returned CommandLine to be the one from the overridden method");
    }

    @Test
    public void testPrivateConstructorReflectivelySetsFields() throws Exception {
        // Locate the private constructor (boolean, Boolean, Consumer<Option>)
        Constructor<DefaultParser> ctor = DefaultParser.class.getDeclaredConstructor(boolean.class, Boolean.class, Consumer.class);
        ctor.setAccessible(true);
        // Prepare a Consumer<Option> instance to pass into constructor
        Consumer<Option> handler = opt -> {
            // no-op for test
        };
        // Instantiate using the private constructor
        DefaultParser parser = ctor.newInstance(Boolean.FALSE, Boolean.TRUE, handler);
        // Verify private fields were set correctly via reflection
        Field fAllow = DefaultParser.class.getDeclaredField("allowPartialMatching");
        fAllow.setAccessible(true);
        boolean allowVal = fAllow.getBoolean(parser);
        assertFalse(allowVal, "allowPartialMatching should be false as passed to the private constructor");
        Field fStrip = DefaultParser.class.getDeclaredField("stripLeadingAndTrailingQuotes");
        fStrip.setAccessible(true);
        Object stripVal = fStrip.get(parser);
        assertEquals(Boolean.TRUE, stripVal, "stripLeadingAndTrailingQuotes should be Boolean.TRUE as passed to the private constructor");
        Field fHandler = DefaultParser.class.getDeclaredField("deprecatedHandler");
        fHandler.setAccessible(true);
        Object handlerVal = fHandler.get(parser);
        assertSame(handler, handlerVal, "deprecatedHandler should be the same Consumer instance passed to the private constructor");
    }

    @Test
    public void testPublicConstructorsDefaultValues() throws Exception {
        // Default (no-arg) constructor
        DefaultParser pDefault = new DefaultParser();
        Field fAllow = DefaultParser.class.getDeclaredField("allowPartialMatching");
        fAllow.setAccessible(true);
        boolean defaultAllow = fAllow.getBoolean(pDefault);
        assertTrue(defaultAllow, "Default constructor should set allowPartialMatching to true");
        Field fStrip = DefaultParser.class.getDeclaredField("stripLeadingAndTrailingQuotes");
        fStrip.setAccessible(true);
        Object defaultStrip = fStrip.get(pDefault);
        assertNull(defaultStrip, "Default constructor should set stripLeadingAndTrailingQuotes to null");
        // boolean-arg constructor
        DefaultParser pFalse = new DefaultParser(false);
        boolean falseAllow = fAllow.getBoolean(pFalse);
        assertFalse(falseAllow, "Constructor DefaultParser(false) should set allowPartialMatching to false");
    }
}
