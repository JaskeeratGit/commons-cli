package org.apache.commons.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandLine_getOptionValue_14_0_Test_testGetOptionValue_returnsNullWhenOptionIsNull {

    @Test
    public void testGetOptionValue_returnsNullWhenOptionIsNull() {
        // create a real CommandLine instance using the package-protected no-arg constructor
        CommandLine cmd = new CommandLine();
        // spy the instance so we can stub getOptionValues
        CommandLine spy = spy(cmd);
        // when getOptionValues is called with null, return null
        doReturn(null).when(spy).getOptionValues((Option) null);
        // passing null should return null
        assertNull(spy.getOptionValue(null));
    }

}
