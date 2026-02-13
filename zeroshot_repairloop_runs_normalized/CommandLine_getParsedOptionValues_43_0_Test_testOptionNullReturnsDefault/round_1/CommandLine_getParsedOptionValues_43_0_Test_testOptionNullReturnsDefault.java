package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValues_43_0_Test_testOptionNullReturnsDefault {

    @Test
    public void testOptionNullReturnsDefault() throws Exception {
        final CommandLine cmd = new CommandLine();
        Supplier<String[]> def = () -> new String[] { "default" };
        String[] result = cmd.getParsedOptionValues((Option) null, def);
        assertArrayEquals(new String[] { "default" }, result);
    }



}
