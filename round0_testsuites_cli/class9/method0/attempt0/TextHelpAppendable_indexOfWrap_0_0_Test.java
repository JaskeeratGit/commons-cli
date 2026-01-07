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

class TextHelpAppendable_indexOfWrap_0_0_Test {

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
    void testIndexOfWrapThrowsOnInvalidWidth() throws Exception {
        Method m = getIndexOfWrapMethod();
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> m.invoke(null, "abc", 0, 0));
        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof IllegalArgumentException);
        assertEquals("Width must be greater than 0", ex.getCause().getMessage());
    }

    @Test
    void testIndexOfWrapReturnsBreakCharIndex() throws Exception {
        // '\n' is part of BREAK_CHAR_SET and should be detected in the first loop
        String text = "abc\ndefgh";
        int result = invokeIndexOfWrap(text, 10, 0);
        // index of '\n'
        assertEquals(3, result);
    }

    @Test
    void testIndexOfWrapReturnsTextLengthWhenWidthGoesBeyondEnd() throws Exception {
        String text = "abcdef";
        // 2 + 10 >= text.length() -> should return text.length()
        int result = invokeIndexOfWrap(text, 10, 2);
        assertEquals(text.length(), result);
    }

    @Test
    void testIndexOfWrapFindsWhitespaceBeforeLimit() throws Exception {
        // Space is not in BREAK_CHAR_SET but isWhitespace returns true, should find index of space
        String text = "abc defgh";
        // startPos 0, width 5 -> limit = min(0+5, len-1)=5. Space at index 3 should be returned.
        int result = invokeIndexOfWrap(text, 5, 0);
        assertEquals(3, result);
    }

    @Test
    void testWhitespaceAtStartPosDoesNotReturnStartButChopsAtLimitMinusOne() throws Exception {
        // If whitespace is at startPos, the function should not return startPos (pos > startPos required)
        String text = " abcdef";
        // startPos 0, width 4 -> limit = 4, whitespace at pos 0 -> should return limit - 1 = 3
        int result = invokeIndexOfWrap(text, 4, 0);
        assertEquals(3, result);
    }

    @Test
    void testNoWhitespaceNoBreakChopsAtLimitMinusOne() throws Exception {
        String text = "abcdefghij";
        // startPos 0, width 4 -> limit = 4, no whitespace or break char in 0..3 -> should return limit - 1 = 3
        int result = invokeIndexOfWrap(text, 4, 0);
        assertEquals(3, result);
    }

    @Test
    void testStartAtLastIndexReturnsTextLength() throws Exception {
        // startPos at last index should result in immediate return of text.length()
        String text = "xyz";
        int startPos = text.length() - 1;
        int result = invokeIndexOfWrap(text, 5, startPos);
        assertEquals(text.length(), result);
    }
}
