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

class Option_getValue_20_0_Test_getValue_whenNoValues_returnsNull_forAnyIndex {

    @Test
    void getValue_whenNoValues_returnsNull_forAnyIndex() {
        Option opt = new Option("a", "description");
        // When no values are present, getValue should return null regardless of index.
        assertNull(opt.getValue(0));
        assertNull(opt.getValue(100));
        assertNull(opt.getValue(-1));
    }



}
