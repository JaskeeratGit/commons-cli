package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
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

class OptionGroup_addOption_0_0_Test_testAddOptionReturnsThisAndStores {

    @Test
    void testAddOptionReturnsThisAndStores() throws Exception {
        OptionGroup group = new OptionGroup();
        Option opt = new Option("a", "alpha", false, "desc");
        OptionGroup returned = group.addOption(opt);
        // Should return the same instance
        assertSame(group, returned);
        Map<String, Option> optionMap = getOptionMap(group);
        assertNotNull(optionMap);
        assertEquals(1, optionMap.size());
        assertTrue(optionMap.containsKey("a"));
        assertSame(opt, optionMap.get("a"));
    }



    @SuppressWarnings("unchecked")
    private Map<String, Option> getOptionMap(OptionGroup group) throws Exception {
        Field field = OptionGroup.class.getDeclaredField("optionMap");
        field.setAccessible(true);
        return (Map<String, Option>) field.get(group);
    }

    /**
     * Utility to set a private (possibly final) field value via reflection.
     */
    private void setFinalField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        // remove final modifier if present
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(target, value);
    }
}
