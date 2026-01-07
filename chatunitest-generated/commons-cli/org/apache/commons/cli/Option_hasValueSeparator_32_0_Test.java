package org.apache.commons.cli;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

class Option_hasValueSeparator_32_0_Test {

    @Test
    void defaultValueSeparator_shouldBeAbsent() throws Exception {
        Option option = new Option("o", "description");
        // by default valueSeparator is '\u0000' (0) so hasValueSeparator() should be false
        assertFalse(option.hasValueSeparator(), "Expected no value separator by default");
    }

    @Test
    void setValueSeparator_usingPublicSetter_shouldReportPresent() throws Exception {
        Option option = new Option("o", "description");
        // use public setter to set a visible separator
        option.setValueSeparator(':');
        assertTrue(option.hasValueSeparator(), "Expected value separator to be present after setter");
        // sanity: different non-zero char also yields true
        option.setValueSeparator((char) 1);
        assertTrue(option.hasValueSeparator(), "Expected non-zero char value separator to be present");
    }

    @Test
    void mutateValueSeparator_viaReflection_shouldAffectHasValueSeparator() throws Exception {
        Option option = new Option("o", "description");
        Field valueSeparatorField = Option.class.getDeclaredField("valueSeparator");
        valueSeparatorField.setAccessible(true);
        // set to zero char via reflection -> hasValueSeparator() should be false
        valueSeparatorField.setChar(option, (char) 0);
        assertFalse(option.hasValueSeparator(), "Expected false when separator is 0 via reflection");
        // set to a positive char via reflection -> hasValueSeparator() should be true
        valueSeparatorField.setChar(option, (char) 'a');
        assertTrue(option.hasValueSeparator(), "Expected true when separator is set to 'a' via reflection");
        // set to the smallest positive char (1) to verify boundary behavior
        valueSeparatorField.setChar(option, (char) 1);
        assertTrue(option.hasValueSeparator(), "Expected true when separator is set to char 1 via reflection");
    }
}
