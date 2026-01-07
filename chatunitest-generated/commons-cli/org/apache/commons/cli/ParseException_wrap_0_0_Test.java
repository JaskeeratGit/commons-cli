package org.apache.commons.cli;

import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class ParseException_wrap_0_0_Test {

    @Test
    public void testWrap_throwsUnsupportedOperationException_sameInstance() {
        UnsupportedOperationException uoe = new UnsupportedOperationException("unsupported");
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> ParseException.wrap(uoe));
        assertSame(uoe, thrown, "wrap should rethrow the same UnsupportedOperationException instance");
    }

    @Test
    public void testWrap_throwsUnsupportedOperationException_forSubclass_sameInstance() {
        class MyUOE extends UnsupportedOperationException {

            MyUOE(String msg) {
                super(msg);
            }
        }
        MyUOE my = new MyUOE("subclass");
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> ParseException.wrap(my));
        assertSame(my, thrown, "wrap should rethrow the same subclass instance of UnsupportedOperationException");
    }

    @Test
    public void testWrap_withParseException_returnsSameInstance() {
        ParseException pe = new ParseException("parse error");
        ParseException result = ParseException.wrap(pe);
        assertSame(pe, result, "wrap should return the same ParseException instance");
    }

    @Test
    public void testWrap_withOtherThrowable_returnsNewParseExceptionWrapping() {
        IllegalArgumentException cause = new IllegalArgumentException("bad arg");
        ParseException result = ParseException.wrap(cause);
        assertNotNull(result, "wrap should not return null for non-null throwable");
        assertNotSame(cause, result, "wrap should not return the original throwable when it's not a ParseException");
        assertSame(cause, result.getCause(), "the returned ParseException should wrap the original throwable as its cause");
    }

    @Test
    public void testWrap_withNull_returnsParseExceptionWithNullCause() {
        ParseException result = ParseException.wrap(null);
        assertNotNull(result, "wrap(null) should return a ParseException instance");
        assertNull(result.getCause(), "the ParseException returned from wrap(null) should have a null cause");
    }
}
