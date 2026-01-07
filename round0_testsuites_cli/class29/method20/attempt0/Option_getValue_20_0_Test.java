package org.apache.commons.cli;

import java.lang.reflect.Field;
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

class Option_getValue_20_0_Test {

    @Test
    void getValue_whenNoValues_returnsNull_forAnyIndex() {
        Option opt = new Option("a", "description");
        // When no values are present, getValue should return null regardless of index.
        assertNull(opt.getValue(0));
        assertNull(opt.getValue(100));
        assertNull(opt.getValue(-1));
    }

    @Test
    void getValue_withValues_returnsCorrectValues() throws Exception {
        Option opt = new Option("a", "description");
        // Use reflection to access the private 'values' list and populate it.
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> values = (List<String>) valuesField.get(opt);
        values.add("first");
        values.add("second");
        assertEquals("first", opt.getValue(0));
        assertEquals("second", opt.getValue(1));
    }

    @Test
    void getValue_withValues_indexOutOfBounds_throwsIndexOutOfBoundsException() throws Exception {
        Option opt = new Option("a", "description");
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> values = (List<String>) valuesField.get(opt);
        values.add("only");
        assertThrows(IndexOutOfBoundsException.class, () -> opt.getValue(1));
        assertThrows(IndexOutOfBoundsException.class, () -> opt.getValue(-2));
    }

    @Test
    void invokePrivate_hasNoValues_viaReflection_matchesState() throws Exception {
        Option opt = new Option("a", "description");
        // Access private hasNoValues method via reflection
        Method hasNoValues = Option.class.getDeclaredMethod("hasNoValues");
        hasNoValues.setAccessible(true);
        // Initially empty -> true
        boolean initiallyEmpty = (Boolean) hasNoValues.invoke(opt);
        assertTrue(initiallyEmpty);
        // Add a value and check again -> false
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> values = (List<String>) valuesField.get(opt);
        values.add("added");
        boolean afterAddEmpty = (Boolean) hasNoValues.invoke(opt);
        assertFalse(afterAddEmpty);
    }
}
