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

class TextHelpAppendable_indexOfWrap_0_0_Test_testIndexOfWrapReturnsBreakCharIndex {

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
    void testIndexOfWrapReturnsBreakCharIndex() throws Exception {
        // '\n' is part of BREAK_CHAR_SET and should be detected in the first loop
        String text = "abc\ndefgh";
        int result = invokeIndexOfWrap(text, 10, 0);
        // index of '\n'
        assertEquals(3, result);
    }





}
