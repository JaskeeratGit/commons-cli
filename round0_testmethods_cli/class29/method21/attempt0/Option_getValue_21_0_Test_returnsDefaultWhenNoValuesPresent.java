package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

class Option_getValue_21_0_Test_returnsDefaultWhenNoValuesPresent {

    // Helper to replace the private 'values' field via reflection
    private void setOptionValues(Option opt, String... vals) throws Exception {
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        List<String> newValues = new ArrayList<>();
        for (String v : vals) {
            newValues.add(v);
        }
        valuesField.set(opt, newValues);
    }

    @Test
    void returnsDefaultWhenNoValuesPresent() throws Exception {
        Option option = new Option("a", "some description");
        // no values set -> expect default returned
        assertEquals("default", option.getValue("default"));
        // also verify null default yields null when no values
        assertNull(option.getValue(null));
    }



}
