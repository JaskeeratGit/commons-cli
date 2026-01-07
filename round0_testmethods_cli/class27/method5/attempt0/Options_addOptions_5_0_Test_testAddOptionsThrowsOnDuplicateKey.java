package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

class Options_addOptions_5_0_Test_testAddOptionsThrowsOnDuplicateKey {


    @Test
    void testAddOptionsThrowsOnDuplicateKey() {
        Options base = new Options();
        Options incoming = new Options();
        // both have option "dup"
        Option first = new Option("dup", false, "first");
        base.addOption(first);
        Option dup = new Option("dup", false, "duplicate");
        incoming.addOption(dup);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> base.addOptions(incoming));
        assertEquals("Duplicate key: " + dup.getKey(), ex.getMessage());
    }

}
