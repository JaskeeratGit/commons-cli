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

class OptionGroup_addOption_0_0_Test_testAddOptionDuplicateReplacesPrevious {



    @Test
    void testAddOptionDuplicateReplacesPrevious() throws Exception {
        OptionGroup group = new OptionGroup();
        Option first = new Option("c", "gamma", false, "first");
        Option second = new Option("c", "gamma2", false, "second");
        group.addOption(first);
        Map<String, Option> optionMapAfterFirst = getOptionMap(group);
        assertEquals(1, optionMapAfterFirst.size());
        assertSame(first, optionMapAfterFirst.get("c"));
        group.addOption(second);
        Map<String, Option> optionMapAfterSecond = getOptionMap(group);
        // size should remain 1 and the value should be replaced by 'second'
        assertEquals(1, optionMapAfterSecond.size());
        assertSame(second, optionMapAfterSecond.get("c"));
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
