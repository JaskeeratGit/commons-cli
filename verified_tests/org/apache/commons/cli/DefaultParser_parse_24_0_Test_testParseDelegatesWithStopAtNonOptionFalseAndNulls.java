package org.apache.commons.cli;

import java.lang.reflect.Method;
import java.util.Properties;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Unit tests for DefaultParser.parse(Options, String[], boolean)
 */
public class DefaultParser_parse_24_0_Test_testParseDelegatesWithStopAtNonOptionFalseAndNulls {

    /**
     * A small test helper parser that captures the parameters it receives when parse(Options, String[], Properties, boolean)
     * is invoked and returns a pre-configured CommandLine instance.
     */
    static class CapturingParser extends DefaultParser {

        Options capturedOptions;

        String[] capturedArguments;

        Properties capturedProperties;

        boolean capturedStopAtNonOption;

        boolean called = false;

        final CommandLine toReturn;

        CapturingParser(CommandLine toReturn) {
            super();
            this.toReturn = toReturn;
        }

        @Override
        public CommandLine parse(final Options options, final String[] arguments, final Properties properties, final boolean stopAtNonOption) throws ParseException {
            called = true;
            capturedOptions = options;
            capturedArguments = arguments;
            capturedProperties = properties;
            capturedStopAtNonOption = stopAtNonOption;
            return toReturn;
        }
    }


    @Test
    public void testParseDelegatesWithStopAtNonOptionFalseAndNulls() throws Exception {
        CommandLine expected = new CommandLine();
        CapturingParser parser = new CapturingParser(expected);
        // pass nulls to ensure delegation still receives nulls
        CommandLine result = parser.parse(null, null, false);
        assertSame(expected, result, "parse should return the CommandLine returned by the delegated overload");
        assertTrue(parser.called, "delegated parse overload should have been called");
        assertNull(parser.capturedOptions, "options should be passed through (null)");
        assertNull(parser.capturedArguments, "arguments should be passed through (null)");
        assertNull(parser.capturedProperties, "properties parameter forwarded to delegated overload should be null");
        assertFalse(parser.capturedStopAtNonOption, "stopAtNonOption should be forwarded as false");
    }

}
