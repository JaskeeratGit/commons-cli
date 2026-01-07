package org.apache.commons.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandLine_getOptionValue_11_0_Test_handlesNullCharacterCorrectly {

    // Subclass to intercept calls to getOptionValue(String)
    static class TestableCommandLine extends CommandLine {

        String captured;

        String toReturn;

        TestableCommandLine(String toReturn) {
            // uses protected no-arg constructor
            super();
            this.toReturn = toReturn;
        }

        @Override
        public String getOptionValue(String optionName) {
            this.captured = optionName;
            return this.toReturn;
        }
    }


    @Test
    void handlesNullCharacterCorrectly() {
        TestableCommandLine t = new TestableCommandLine("null-char-value");
        String res = t.getOptionValue('\0');
        assertEquals("null-char-value", res);
        assertEquals("\0", t.captured);
    }

}
