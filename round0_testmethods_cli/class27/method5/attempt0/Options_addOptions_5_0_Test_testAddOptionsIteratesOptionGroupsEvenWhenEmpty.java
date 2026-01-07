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

class Options_addOptions_5_0_Test_testAddOptionsIteratesOptionGroupsEvenWhenEmpty {



    @Test
    void testAddOptionsIteratesOptionGroupsEvenWhenEmpty() {
        // This test ensures the optionGroups.forEach(...) line is executed without error
        Options base = new Options();
        Options incoming = new Options();
        // incoming has no option groups; just ensure addOptions runs normally
        Option x = new Option("x", false, "x");
        incoming.addOption(x);
        // Should not throw
        Options returned = base.addOptions(incoming);
        assertSame(base, returned);
        assertTrue(base.hasOption("x"));
        // Also assert getOptionGroups on incoming returns a collection (possibly empty)
        Collection<OptionGroup> groups = incoming.getOptionGroups();
        assertNotNull(groups);
    }
}
