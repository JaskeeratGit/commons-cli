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

class Option_hasValueSeparator_32_0_Test_defaultValueSeparator_shouldBeAbsent {

    @Test
    void defaultValueSeparator_shouldBeAbsent() throws Exception {
        Option option = new Option("o", "description");
        // by default valueSeparator is '\u0000' (0) so hasValueSeparator() should be false
        assertFalse(option.hasValueSeparator(), "Expected no value separator by default");
    }


}
