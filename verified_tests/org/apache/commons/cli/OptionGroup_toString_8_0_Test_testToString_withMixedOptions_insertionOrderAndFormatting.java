package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class OptionGroup_toString_8_0_Test_testToString_withMixedOptions_insertionOrderAndFormatting {


    @Test
    public void testToString_withMixedOptions_insertionOrderAndFormatting() throws Exception {
        OptionGroup group = new OptionGroup();
        // create options
        // short opt with description
        Option o1 = new Option("a", false, "alpha");
        // initially has short opt "b" and long opt "longB"
        Option o2 = new Option("b", "longB", false, null);
        // short opt "c" with no description
        Option o3 = new Option("c", (String) null);
        // Force o2 to behave as long-only option by nulling its 'option' field via reflection
        setFinalField(Option.class, o2, "option", null);
        // insert into OptionGroup.optionMap using reflection to access the private map
        Field optionMapField = OptionGroup.class.getDeclaredField("optionMap");
        optionMapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Option> optionMap = (Map<String, Option>) optionMapField.get(group);
        // Use keys that mirror usual behavior (option key typically uses opt or longOpt)
        optionMap.put(o1.getOpt() != null ? o1.getOpt() : o1.getLongOpt(), o1);
        optionMap.put(o2.getOpt() != null ? o2.getOpt() : o2.getLongOpt(), o2);
        optionMap.put(o3.getOpt() != null ? o3.getOpt() : o3.getLongOpt(), o3);
        // Expected: first shows short opt with description, second shows long opt without description,
        // third shows short opt without description. Items separated by ", " and wrapped with [ ]
        String expected = "[-a alpha, --longB, -c]";
        assertEquals(expected, group.toString());
    }

    // Helper to set (possibly final) private fields via reflection
    private static void setFinalField(Class<?> declaringClass, Object target, String fieldName, Object value) throws Exception {
        Field field = declaringClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        // remove final modifier if present
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int mods = field.getModifiers();
        if ((mods & Modifier.FINAL) != 0) {
            modifiersField.setInt(field, mods & ~Modifier.FINAL);
        }
        field.set(target, value);
    }
}
