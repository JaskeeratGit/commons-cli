package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;

public class Options_getOptionGroup_9_0_Test_testGetOptionGroupReturnsGroupWhenPresent {

    @Test
    public void testGetOptionGroupReturnsGroupWhenPresent() throws Exception {
        Options options = new Options();
        Option opt = new Option("a", "description");

        // Use the real OptionGroup from org.apache.commons.cli to match Options' map value type
        org.apache.commons.cli.OptionGroup group = new org.apache.commons.cli.OptionGroup();

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
