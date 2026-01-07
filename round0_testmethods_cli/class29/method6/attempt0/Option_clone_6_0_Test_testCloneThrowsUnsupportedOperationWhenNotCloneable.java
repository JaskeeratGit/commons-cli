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

public class Option_clone_6_0_Test_testCloneThrowsUnsupportedOperationWhenNotCloneable {

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
    void testCloneThrowsUnsupportedOperationWhenNotCloneable() {
        // Create a plain Option instance (does not implement Cloneable in the provided signature).
        org.apache.commons.cli.Option plain = new org.apache.commons.cli.Option("x", "desc");
        // Expect UnsupportedOperationException because super.clone() will throw CloneNotSupportedException
        assertThrows(UnsupportedOperationException.class, plain::clone);
    }
}
