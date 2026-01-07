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

class Option_hashCode_28_0_Test_testHashCode_differentWhenShortOptionDiffers {


    @Test
    void testHashCode_differentWhenShortOptionDiffers() throws Exception {
        Option a = new Option("a", "long", false, "desc");
        Option b = new Option("b", "long", false, "desc");
        int ha = a.hashCode();
        int hb = b.hashCode();
        // Very unlikely to collide; assert they differ to test inclusion of short opt in hash
        assertNotEquals(ha, hb, "Different short options should (practically) yield different hashCodes");
    }


}
