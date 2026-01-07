package org.apache.commons.cli.help;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import org.apache.commons.cli.Option;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.cli.DeprecatedAttributes;

public class OptionFormatter_toSyntaxOption_10_0_Test_testToSyntaxOption_usesCustomSyntaxFunction_whenRequiredFalse {

    /**
     * Helper: create an instance of OptionFormatter by reflectively constructing its
     * private Builder nested class, setting the provided builder fields (by name),
     * and invoking the private OptionFormatter(Option, Builder) constructor.
     */
    private Object createOptionFormatterWithBuilder(final Option option, final java.util.Map<String, Object> builderFieldValues) throws Exception {
        final Class<?> ofClass = Class.forName("org.apache.commons.cli.help.OptionFormatter");
        final Class<?> builderClass = Class.forName("org.apache.commons.cli.help.OptionFormatter$Builder");
        // Instantiate Builder (assume no-arg constructor exists)
        Constructor<?> builderCtor = null;
        for (Constructor<?> c : builderClass.getDeclaredConstructors()) {
            if (c.getParameterCount() == 0) {
                builderCtor = c;
                break;
            }
        }
        if (builderCtor == null) {
            // fallback: pick first constructor and instantiate bypassing args (use nulls)
            builderCtor = builderClass.getDeclaredConstructors()[0];
        }
        builderCtor.setAccessible(true);
        Object builderInstance;
        if (builderCtor.getParameterCount() == 0) {
            builderInstance = builderCtor.newInstance();
        } else {
            // create with all nulls for parameters if non-empty constructor (rare)
            Object[] params = new Object[builderCtor.getParameterCount()];
            builderInstance = builderCtor.newInstance(params);
        }
        // Set requested fields on builder (if they exist)
        if (builderFieldValues != null) {
            for (java.util.Map.Entry<String, Object> e : builderFieldValues.entrySet()) {
                try {
                    Field f = builderClass.getDeclaredField(e.getKey());
                    f.setAccessible(true);
                    f.set(builderInstance, e.getValue());
                } catch (NoSuchFieldException nsfe) {
                    // If field doesn't exist on builder, ignore (keeps defaults)
                }
            }
        }
        // Find private constructor OptionFormatter(Option, Builder)
        Constructor<?> ofCtor = null;
        for (Constructor<?> c : ofClass.getDeclaredConstructors()) {
            Class<?>[] pts = c.getParameterTypes();
            if (pts.length == 2 && pts[0] == Option.class) {
                // second param must be the Builder type (or compatible)
                ofCtor = c;
                break;
            }
        }
        if (ofCtor == null) {
            // fallback: try to find any constructor with Option param first
            for (Constructor<?> c : ofClass.getDeclaredConstructors()) {
                Class<?>[] pts = c.getParameterTypes();
                if (pts.length >= 1 && pts[0] == Option.class) {
                    ofCtor = c;
                    break;
                }
            }
        }
        ofCtor.setAccessible(true);
        return ofCtor.newInstance(option, builderInstance);
    }


    @Test
    public void testToSyntaxOption_usesCustomSyntaxFunction_whenRequiredFalse() throws Exception {
        // Create an Option and ensure it is not required
        Option opt = new Option("y", "long-y", false, "desc");
        opt.setRequired(false);
        // Provide a builder that sets a custom syntaxFormatFunction which observes the required flag
        BiFunction<?, ?, ?> syntaxFn = (BiFunction<Object, Boolean, String>) (o, required) -> (required != null && required) ? "CUSTOM_REQUIRED" : "CUSTOM_OPTIONAL";
        java.util.Map<String, Object> builderFields = new java.util.HashMap<>();
        builderFields.put("syntaxFormatFunction", syntaxFn);
        Object ofInstance = createOptionFormatterWithBuilder(opt, builderFields);
        // Invoke public no-arg toSyntaxOption() on created OptionFormatter instance
        Method toSyntaxNoArg = ofInstance.getClass().getMethod("toSyntaxOption");
        Object result = toSyntaxNoArg.invoke(ofInstance);
        assertEquals("CUSTOM_OPTIONAL", result);
    }

}
