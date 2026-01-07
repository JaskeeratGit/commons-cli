package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

class CommandLine_getParsedOptionValue_33_0_Test_publicAndPrivateBehaveConsistently_whenOptionGroupHasSelectedFieldSet {

    // Helper to create a CommandLine instance via the private constructor
    private CommandLine newCommandLineInstance() throws Exception {
        Constructor<CommandLine> ctor = CommandLine.class.getDeclaredConstructor(List.class, List.class, Consumer.class);
        ctor.setAccessible(true);
        List<String> args = new LinkedList<>();
        List<Option> options = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Consumer<Option> deprecatedHandler = (Consumer<Option>) (o -> {
            // no-op
        });
        return ctor.newInstance(args, options, deprecatedHandler);
    }



    @Test
    void publicAndPrivateBehaveConsistently_whenOptionGroupHasSelectedFieldSet() throws Exception {
        CommandLine cmd = newCommandLineInstance();
        OptionGroup og = new OptionGroup();
        // Force the OptionGroup.selected private field to a non-null value to exercise that branch
        Field selectedField = OptionGroup.class.getDeclaredField("selected");
        selectedField.setAccessible(true);
        selectedField.set(og, "SOME_SELECTED_NAME");
        Method publicMethod = CommandLine.class.getMethod("getParsedOptionValue", OptionGroup.class);
        Method privateOverloaded = CommandLine.class.getDeclaredMethod("getParsedOptionValue", OptionGroup.class, Supplier.class);
        privateOverloaded.setAccessible(true);
        // Compare results: public should still delegate to private (with supplier returning null)
        Object publicResult = publicMethod.invoke(cmd, og);
        Object privateResultWithNullSupplier = privateOverloaded.invoke(cmd, og, (Supplier<Object>) () -> null);
        assertEquals(privateResultWithNullSupplier, publicResult, "Public method should delegate to overloaded method even when OptionGroup.selected is set");
        // Also verify that a provided supplier value is returned by the private method (consistency check)
        String supplied = "SUPPLIED_VAL";
        Object privateResultWithSupplier = privateOverloaded.invoke(cmd, og, (Supplier<String>) () -> supplied);
        // Depending on internal behavior this may or may not return the supplied value; ensure consistency compared to direct supplier call
        assertEquals(supplied, privateResultWithSupplier, "Private overloaded method should return the provided supplier value when invoked directly");
    }
}
