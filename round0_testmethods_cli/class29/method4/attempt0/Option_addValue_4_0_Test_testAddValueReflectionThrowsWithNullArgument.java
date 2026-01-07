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

public class Option_addValue_4_0_Test_testAddValueReflectionThrowsWithNullArgument {

    private static final String EXPECTED_MESSAGE = "The addValue method is not intended for client use. Subclasses should use the processValue method instead.";


    @Test
    public void testAddValueReflectionThrowsWithNullArgument() throws Exception {
        Option opt = new Option("b", "desc2");
        Method addValueMethod = Option.class.getDeclaredMethod("addValue", String.class);
        addValueMethod.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> addValueMethod.invoke(opt, (String) null));
        Throwable cause = ite.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals(EXPECTED_MESSAGE, cause.getMessage());
    }

}
