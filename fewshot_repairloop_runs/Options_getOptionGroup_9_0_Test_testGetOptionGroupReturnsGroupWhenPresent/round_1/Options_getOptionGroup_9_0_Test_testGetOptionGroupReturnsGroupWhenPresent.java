package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class Options_getOptionGroup_9_0_Test_testGetOptionGroupReturnsGroupWhenPresent {

    @Test
    public void testGetOptionGroupReturnsGroupWhenPresent() throws Exception {
        Options options = new Options();
        Option opt = new Option("a", "description");
        org.apache.commons.cli.OptionGroup group = mock(org.apache.commons.cli.OptionGroup.class);
        // Put the group into the private optionGroups map via reflection
        Field f = Options.class.getDeclaredField("optionGroups");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, org.apache.commons.cli.OptionGroup> map =
                (Map<String, org.apache.commons.cli.OptionGroup>) f.get(options);
        map.put(opt.getKey(), group);
        org.apache.commons.cli.OptionGroup result = options.getOptionGroup(opt);
        assertSame(group, result, "Expected the stored OptionGroup to be returned for the option key");
    }

}
