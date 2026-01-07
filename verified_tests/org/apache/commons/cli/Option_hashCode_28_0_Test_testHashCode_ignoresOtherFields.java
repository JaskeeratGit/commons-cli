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

class Option_hashCode_28_0_Test_testHashCode_ignoresOtherFields {



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

}
