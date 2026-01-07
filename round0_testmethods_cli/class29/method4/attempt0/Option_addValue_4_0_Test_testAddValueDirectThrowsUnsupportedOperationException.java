package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.apache.commons.cli.Util.EMPTY_STRING_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

public class Option_addValue_4_0_Test_testAddValueDirectThrowsUnsupportedOperationException {

    private static final String EXPECTED_MESSAGE = "The addValue method is not intended for client use. Subclasses should use the processValue method instead.";

    @Test
    public void testAddValueDirectThrowsUnsupportedOperationException() {
        Option opt = new Option("a", "description");
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> opt.addValue("value1"));
        assertEquals(EXPECTED_MESSAGE, ex.getMessage());
    }


}
