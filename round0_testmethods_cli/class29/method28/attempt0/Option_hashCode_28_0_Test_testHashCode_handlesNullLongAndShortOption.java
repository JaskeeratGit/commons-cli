package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
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
import java.util.function.Supplier;

class Option_hashCode_28_0_Test_testHashCode_handlesNullLongAndShortOption {




    @Test
    void testHashCode_handlesNullLongAndShortOption() throws Exception {
        // Create a normal Option and then set both longOption and option fields to null reflectively
        Option opt = new Option("z", "someLong", false, "desc");
        // clear final modifier on 'option' field to allow setting it to null
        Field optField = Option.class.getDeclaredField("option");
        optField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(optField, optField.getModifiers() & ~Modifier.FINAL);
        // set the fields to null
        optField.set(opt, null);
        Field longOptField = Option.class.getDeclaredField("longOption");
        longOptField.setAccessible(true);
        longOptField.set(opt, null);
        // expected using Objects.hash(null, null)
        int expected = Objects.hash(null, null);
        assertEquals(expected, opt.hashCode(), "hashCode should handle null longOption and option consistently");
    }
}
