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

class Option_hashCode_28_0_Test {

    @Test
    void testHashCode_sameForEqualOptions() throws Exception {
        Option o1 = new Option("o", "long", false, "desc");
        Option o2 = new Option("o", "long", false, "other desc");
        int h1 = o1.hashCode();
        int h2 = o2.hashCode();
        assertEquals(h1, h2, "Options with same short and long option should have same hashCode");
        // also assert deterministic
        assertEquals(h1, o1.hashCode());
        assertEquals(h2, o2.hashCode());
    }

    @Test
    void testHashCode_differentWhenShortOptionDiffers() throws Exception {
        Option a = new Option("a", "long", false, "desc");
        Option b = new Option("b", "long", false, "desc");
        int ha = a.hashCode();
        int hb = b.hashCode();
        // Very unlikely to collide; assert they differ to test inclusion of short opt in hash
        assertNotEquals(ha, hb, "Different short options should (practically) yield different hashCodes");
    }

    @Test
    void testHashCode_ignoresOtherFields() throws Exception {
        Option base = new Option("x", "commonLong", false, "desc");
        Option modified = new Option("x", "commonLong", false, "desc");
        int before = base.hashCode();
        // Mutate many other fields on modified instance that should not affect hashCode
        modified.setDescription("changed");
        modified.setRequired(true);
        modified.setOptionalArg(true);
        modified.setArgs(5);
        modified.setValueSeparator(';');
        modified.setType(Integer.class);
        // add values via the public API or reflectively if necessary
        Field valuesField = Option.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        java.util.List<String> vals = (java.util.List<String>) valuesField.get(modified);
        vals.add("v1");
        vals.add("v2");
        // Ensure hashCode remains the same because hash is based only on longOption and option
        assertEquals(before, modified.hashCode(), "Changing non-hashed fields should not affect hashCode");
    }

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
