package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

class Options_addOptionGroup_4_0_Test {

    /**
     * Helper to inject an Option into an OptionGroup's private optionMap via reflection.
     */
    private static void putOptionIntoGroup(final OptionGroup group, final Option opt) throws Exception {
        final Field optionMapField = OptionGroup.class.getDeclaredField("optionMap");
        optionMapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        final Map<String, Option> optionMap = (Map<String, Option>) optionMapField.get(group);
        optionMap.put(opt.getKey(), opt);
    }

    /**
     * Helper to read the private 'required' flag from Option via reflection.
     */
    private static boolean isOptionRequired(final Option opt) throws Exception {
        final Field requiredField = Option.class.getDeclaredField("required");
        requiredField.setAccessible(true);
        return (boolean) requiredField.get(opt);
    }

    /**
     * Helper to read the private optionGroups map from Options via reflection.
     */
    private static Map<String, OptionGroup> getOptionsOptionGroupsMap(final Options options) throws Exception {
        final Field optionGroupsField = Options.class.getDeclaredField("optionGroups");
        optionGroupsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        final Map<String, OptionGroup> map = (Map<String, OptionGroup>) optionGroupsField.get(options);
        return map;
    }

    @Test
    void addOptionGroup_removesExistingRequiredKeyAndRegistersGroup_whenGroupNotRequired() throws Exception {
        Options options = new Options();
        // create an option and mark it required, so it's added as a required key in Options
        Option opt = new Option("a", "alpha");
        opt.setRequired(true);
        options.addOption(opt);
        // precondition: required options contains the key "a"
        List<?> requiredBefore = options.getRequiredOptions();
        assertTrue(requiredBefore.contains("a"));
        // prepare an OptionGroup containing the same option, but group is NOT required
        OptionGroup group = new OptionGroup();
        group.setRequired(false);
        putOptionIntoGroup(group, opt);
        // call focal method
        options.addOptionGroup(group);
        // the option should still be available in options.getOptions()
        Collection<Option> opts = options.getOptions();
        assertTrue(opts.contains(opt));
        // the options' required list should no longer contain the string key "a"
        List<?> requiredAfter = options.getRequiredOptions();
        assertFalse(requiredAfter.contains("a"));
        // because we added the group, the internal optionGroups map should map key "a" to our group
        Map<String, OptionGroup> optionGroupsMap = getOptionsOptionGroupsMap(options);
        assertSame(group, optionGroupsMap.get(opt.getKey()));
        // the group should be present in getOptionGroups() collection (values of the map)
        Collection<OptionGroup> groups = options.getOptionGroups();
        assertTrue(groups.contains(group));
        // ensure that the option's required flag was set to false by addOptionGroup
        assertFalse(isOptionRequired(opt));
    }

    @Test
    void addOptionGroup_addsGroupToRequiredOptions_whenGroupIsRequired_and_removesKeyString() throws Exception {
        Options options = new Options();
        // create an option and mark it required, so it's added as a required key in Options
        Option opt = new Option("b", "beta");
        opt.setRequired(true);
        options.addOption(opt);
        // precondition: required options contains the key "b"
        List<?> requiredBefore = options.getRequiredOptions();
        assertTrue(requiredBefore.contains("b"));
        // prepare an OptionGroup containing the same option, and mark the group required
        OptionGroup group = new OptionGroup();
        group.setRequired(true);
        putOptionIntoGroup(group, opt);
        // call focal method
        options.addOptionGroup(group);
        // the option should be available in options.getOptions()
        Collection<Option> opts = options.getOptions();
        assertTrue(opts.contains(opt));
        // The string key "b" should have been removed from required options
        List<?> requiredAfter = options.getRequiredOptions();
        assertFalse(requiredAfter.contains("b"));
        // Since the group is required, the required options list should contain the OptionGroup instance
        assertTrue(requiredAfter.contains(group));
        // check internal optionGroups mapping has been updated
        Map<String, OptionGroup> optionGroupsMap = getOptionsOptionGroupsMap(options);
        assertSame(group, optionGroupsMap.get(opt.getKey()));
        // ensure that the option's required flag was set to false by addOptionGroup
        assertFalse(isOptionRequired(opt));
    }

    @Test
    void addOptionGroup_withEmptyGroup_doesNotModifyOptions() throws Exception {
        Options options = new Options();
        // create an empty group (no options injected)
        OptionGroup emptyGroup = new OptionGroup();
        emptyGroup.setRequired(false);
        // snapshot before
        Collection<Option> beforeOptions = options.getOptions();
        Collection<OptionGroup> beforeGroups = options.getOptionGroups();
        List<?> beforeRequired = options.getRequiredOptions();
        // call focal method with empty group
        options.addOptionGroup(emptyGroup);
        // nothing should change since group has no options
        assertEquals(beforeOptions, options.getOptions());
        assertEquals(beforeGroups, options.getOptionGroups());
        assertEquals(beforeRequired, options.getRequiredOptions());
    }
}
