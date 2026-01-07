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

class TextHelpAppendable_indexOfWrap_0_0_Test_testIndexOfWrapFindsWhitespaceBeforeLimit {

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
    void testIndexOfWrapFindsWhitespaceBeforeLimit() throws Exception {
        // Space is not in BREAK_CHAR_SET but isWhitespace returns true, should find index of space
        String text = "abc defgh";
        // startPos 0, width 5 -> limit = min(0+5, len-1)=5. Space at index 3 should be returned.
        int result = invokeIndexOfWrap(text, 5, 0);
        assertEquals(3, result);
    }



}
