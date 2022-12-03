package com.nategrigg.JavaLearnings;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUuid
{
    private static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";
    static final String NON_NIL = "00000000-0000-0000-0000-000000000001";


    @Test
    void testZeroUuid()
    {
        var fromLongs = new UUID(0, 0);
        var fromString = UUID.fromString(NIL_UUID);
        Assertions.assertEquals(fromLongs, fromString);
    }

    @Test
    void testNonZeroUuid()
    {
        var fromLongs = new UUID(0, 1);
        var fromString = UUID.fromString(NON_NIL);

        Assertions.assertEquals(fromLongs, fromString);
    }
}
