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

class Option_hasValueSeparator_32_0_Test_setValueSeparator_usingPublicSetter_shouldReportPresent {


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

}
