package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class Options_getOptionGroup_9_0_Test_testGetOptionGroupReturnsGroupWhenPresent {

    // Minimal OptionGroup for testing purposes
    static class OptionGroup {

        private final String id;

        OptionGroup(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }
    }

    @Test
    public void testGetOptionGroupReturnsGroupWhenPresent() throws Exception {
        Options options = new Options();
        Option opt = new Option("a", "description");
        OptionGroup group = new OptionGroup("group1");
        // Put the group into the private optionGroups map via reflection
        Field f = Options.class.getDeclaredField("optionGroups");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, OptionGroup> map = (Map<String, OptionGroup>) f.get(options);
        map.put(opt.getKey(), group);
        OptionGroup result = options.getOptionGroup(opt);
        assertSame(group, result, "Expected the stored OptionGroup to be returned for the option key");
    }


}
