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

class Option_hasValueSeparator_32_0_Test_mutateValueSeparator_viaReflection_shouldAffectHasValueSeparator {



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
