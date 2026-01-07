package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * Unit tests for DefaultParser.parse(Options, String[], Properties)
 *
 * These tests use reflection so they compile even if the concrete runtime types
 * for CommandLine / ParseException / other internals are not available at compile time.
 */
public class DefaultParser_parse_25_0_Test {

    @Test
    @DisplayName("parse using public no-arg constructor with empty args should return a CommandLine object (non-null)")
    public void testParseWithDefaultConstructorEmptyArgs() throws Exception {
        Class<?> parserClass = Class.forName("org.apache.commons.cli.DefaultParser");
        Class<?> optionsClass = Class.forName("org.apache.commons.cli.Options");
        // instantiate Options
        Object options = optionsClass.getDeclaredConstructor().newInstance();
        // instantiate DefaultParser via public no-arg constructor
        Constructor<?> ctorDefault = parserClass.getConstructor();
        Object parser = ctorDefault.newInstance();
        // prepare arguments: empty String[] and empty Properties
        String[] args = new String[0];
        Properties props = new Properties();
        // invoke parse(Options, String[], Properties)
        Method parseMethod = parserClass.getMethod("parse", optionsClass, String[].class, Properties.class);
        Object result;
        try {
            result = parseMethod.invoke(parser, options, args, props);
        } catch (InvocationTargetException ite) {
            // If the underlying parse throws a checked ParseException (or other), fail the test with cause info.
            Throwable cause = ite.getCause();
            Assertions.fail("parse threw an exception: " + (cause == null ? ite.toString() : cause.toString()));
            // unreachable, but keeps compiler happy
            return;
        }
        // Basic sanity: ensure we got a non-null result (a CommandLine instance expected)
        Assertions.assertNotNull(result, "parse(...) returned null, expected a CommandLine object");
    }

    @Test
    @DisplayName("parse using public boolean constructor with some args should return non-null or produce a clear failure")
    public void testParseWithBooleanConstructor() throws Exception {
        Class<?> parserClass = Class.forName("org.apache.commons.cli.DefaultParser");
        Class<?> optionsClass = Class.forName("org.apache.commons.cli.Options");
        // instantiate Options
        Object options = optionsClass.getDeclaredConstructor().newInstance();
        // instantiate DefaultParser via public boolean constructor
        Constructor<?> ctorBool = parserClass.getConstructor(boolean.class);
        Object parser = ctorBool.newInstance(true);
        // prepare arguments: simple args array and empty Properties
        String[] args = new String[] { "arg1", "arg2" };
        Properties props = new Properties();
        // invoke parse(Options, String[], Properties)
        Method parseMethod = parserClass.getMethod("parse", optionsClass, String[].class, Properties.class);
        Object result;
        try {
            result = parseMethod.invoke(parser, options, args, props);
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            // Provide clear failure message including the cause
            Assertions.fail("parse threw an exception: " + (cause == null ? ite.toString() : cause.toString()));
            return;
        }
        Assertions.assertNotNull(result, "parse(...) returned null when called with boolean constructor");
    }

    @Test
    @DisplayName("invoke private constructor and private overloaded parse (if present) via reflection")
    public void testPrivateConstructorAndPrivateParseInvocation() throws Exception {
        Class<?> parserClass = Class.forName("org.apache.commons.cli.DefaultParser");
        Class<?> optionsClass = Class.forName("org.apache.commons.cli.Options");
        // instantiate Options
        Object options = optionsClass.getDeclaredConstructor().newInstance();
        // Try to locate the private constructor: (boolean, Boolean, Consumer)
        Constructor<?> privateCtor = null;
        for (Constructor<?> c : parserClass.getDeclaredConstructors()) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 3) {
                // look for boolean, Boolean, and java.util.function.Consumer (or a compatible type)
                if (params[0] == boolean.class && (params[1] == Boolean.class || params[1] == java.lang.Boolean.class) && java.util.function.Consumer.class.isAssignableFrom(params[2])) {
                    privateCtor = c;
                    break;
                }
            }
        }
        // If not found, fall back to public constructors (test is still meaningful)
        Object parser;
        if (privateCtor != null) {
            privateCtor.setAccessible(true);
            // pass allowPartialMatching=false, stripLeadingAndTrailingQuotes=Boolean.TRUE, deprecatedHandler as a no-op Consumer
            @SuppressWarnings("unchecked")
            Consumer<Object> noOp = o -> {
                /* no-op */
            };
            parser = privateCtor.newInstance(false, Boolean.TRUE, noOp);
        } else {
            // fallback: use the no-arg constructor
            Constructor<?> ctorDefault = parserClass.getConstructor();
            parser = ctorDefault.newInstance();
        }
        // Try to find the private overloaded parse: parse(Options, Properties, NonOptionAction, String[])
        Method privateParse = null;
        // Use reflection to find a declared method named "parse" with 4 parameters
        for (Method m : parserClass.getDeclaredMethods()) {
            if (!m.getName().equals("parse"))
                continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length == 4) {
                // first param should be Options-like, second Properties, last String[]
                if (params[0].isAssignableFrom(optionsClass) && params[1] == Properties.class && params[3] == String[].class) {
                    privateParse = m;
                    break;
                }
            }
        }
        // If the private overloaded parse exists, invoke it with a specific NonOptionAction if possible.
        if (privateParse != null) {
            privateParse.setAccessible(true);
            // Prepare Properties and args
            Properties props = new Properties();
            String[] args = new String[0];
            // Attempt to obtain a suitable enum constant for the third parameter (NonOptionAction)
            Class<?> noaClass = privateParse.getParameterTypes()[2];
            Object noaValue = null;
            if (noaClass.isEnum()) {
                // try common names STOP or THROW if present
                try {
                    noaValue = Enum.valueOf((Class<Enum>) noaClass, "STOP");
                } catch (IllegalArgumentException ignored) {
                    try {
                        noaValue = Enum.valueOf((Class<Enum>) noaClass, "THROW");
                    } catch (IllegalArgumentException ignored2) {
                        // fallback to first enum constant
                        Object[] consts = noaClass.getEnumConstants();
                        if (consts != null && consts.length > 0) {
                            noaValue = consts[0];
                        }
                    }
                }
            } else {
                // if it's not an enum for some reason, try null (may or may not be acceptable)
                noaValue = null;
            }
            Object result;
            try {
                result = privateParse.invoke(parser, options, props, noaValue, args);
            } catch (InvocationTargetException ite) {
                Throwable cause = ite.getCause();
                Assertions.fail("private parse(...) threw an exception: " + (cause == null ? ite.toString() : cause.toString()));
                return;
            }
            Assertions.assertNotNull(result, "private parse(...) returned null");
        } else {
            // If no private overloaded parse exists, at least call the public parse to ensure parser instance works.
            Method parseMethod = parserClass.getMethod("parse", optionsClass, String[].class, Properties.class);
            Object result;
            try {
                result = parseMethod.invoke(parser, options, new String[0], new Properties());
            } catch (InvocationTargetException ite) {
                Throwable cause = ite.getCause();
                Assertions.fail("parse threw an exception: " + (cause == null ? ite.toString() : cause.toString()));
                return;
            }
            Assertions.assertNotNull(result, "public parse(...) returned null in fallback scenario");
        }
    }
}
