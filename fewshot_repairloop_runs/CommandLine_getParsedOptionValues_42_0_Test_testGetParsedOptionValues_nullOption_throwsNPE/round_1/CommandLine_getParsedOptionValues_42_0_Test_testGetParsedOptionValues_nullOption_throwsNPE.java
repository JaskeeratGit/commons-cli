package org.apache.commons.cli;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class CommandLine_getParsedOptionValues_42_0_Test_testGetParsedOptionValues_nullOption_throwsNPE {

    @Test
    public void testGetParsedOptionValues_nullOption_throwsNPE() {
        CommandLine cmd = new CommandLine();
        assertThrows(NullPointerException.class, () -> {
            cmd.getParsedOptionValues((Option) null);
        });
    }

}
