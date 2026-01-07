package org.apache.commons.cli;

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

public class Option_clone_6_0_Test_testCloneCreatesIndependentValuesList {

    /**
     * Subclass of Option that implements Cloneable so that super.clone() succeeds.
     * Uses a simple public constructor delegating to existing Option(String, String).
     */
    static class TestOption extends org.apache.commons.cli.Option implements Cloneable {

        public TestOption() {
            super("t", "test");
        }
    }

    @Test
    void testCloneCreatesIndependentValuesList() throws Exception {
        // Create instance of subclass that implements Cloneable so Option.clone() path succeeds
        TestOption original = new TestOption();
        // Use reflection to access the private 'values' field and populate it
        Field valuesField = org.apache.commons.cli.Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> originalValues = (List<String>) valuesField.get(original);
        // Ensure it's a mutable list and add entries
        originalValues.add("one");
        originalValues.add("two");
        // Invoke clone() (inherited from Option). This should create a new Option instance
        Object clonedObj = original.clone();
        assertNotNull(clonedObj, "clone() returned null");
        assertTrue(clonedObj instanceof org.apache.commons.cli.Option, "clone() did not return an Option instance");
        org.apache.commons.cli.Option cloned = (org.apache.commons.cli.Option) clonedObj;
        // Verify that the 'values' field of the clone is a separate List instance with same contents
        @SuppressWarnings("unchecked")
        List<String> clonedValues = (List<String>) valuesField.get(cloned);
        assertNotNull(clonedValues, "cloned values list is null");
        assertNotSame(originalValues, clonedValues, "values list should be a different instance after clone");
        assertEquals(originalValues, clonedValues, "values content should be equal immediately after clone");
        // Mutate original and ensure clone is unaffected
        originalValues.add("three");
        assertFalse(clonedValues.contains("three"), "cloned values list should not reflect additions to the original after clone");
        // Mutate clone and ensure original is unaffected
        clonedValues.remove("one");
        assertTrue(originalValues.contains("one"), "original values list should not reflect removals from the clone after clone");
    }

}
