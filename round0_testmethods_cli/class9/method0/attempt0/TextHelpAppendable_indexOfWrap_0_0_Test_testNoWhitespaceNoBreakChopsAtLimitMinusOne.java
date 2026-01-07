package org.apache.commons.cli.help;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class TextHelpAppendable_indexOfWrap_0_0_Test_testNoWhitespaceNoBreakChopsAtLimitMinusOne {

    private static Method getIndexOfWrapMethod() throws NoSuchMethodException {
        Method m = TextHelpAppendable.class.getDeclaredMethod("indexOfWrap", CharSequence.class, int.class, int.class);
        m.setAccessible(true);
        return m;
    }

    private static int invokeIndexOfWrap(CharSequence text, int width, int startPos) throws Exception {
        Method m = getIndexOfWrapMethod();
        return (Integer) m.invoke(null, text, width, startPos);
    }






    @Test
    void testNoWhitespaceNoBreakChopsAtLimitMinusOne() throws Exception {
        String text = "abcdefghij";
        // startPos 0, width 4 -> limit = 4, no whitespace or break char in 0..3 -> should return limit - 1 = 3
        int result = invokeIndexOfWrap(text, 4, 0);
        assertEquals(3, result);
    }

}
