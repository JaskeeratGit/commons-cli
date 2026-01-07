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

class Option_hashCode_28_0_Test_testHashCode_sameForEqualOptions {

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



}
