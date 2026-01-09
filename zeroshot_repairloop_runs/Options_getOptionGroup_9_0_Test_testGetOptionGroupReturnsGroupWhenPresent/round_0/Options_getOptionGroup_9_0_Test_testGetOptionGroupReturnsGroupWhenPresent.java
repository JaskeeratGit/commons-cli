package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class Options_getOptionGroup_9_0_Test_testGetOptionGroupReturnsGroupWhenPresent {

    @Test
    public void testGetOptionGroupReturnsGroupWhenPresent() throws Exception {
        Options options = new Options();
        Option opt = new Option("a", "description");
        OptionGroup group = new OptionGroup(); // use real OptionGroup class

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
