package com.example.renthouse.Activity;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActivityDetailsUnitTest {
    @Test
    public void testGetAfterSpace_1() {
        String validValue = "valid Value";
        assertEquals("Value", ActivityDetails.getAfterSpace(validValue));
    }

    @Test
    public void testGetAfterSpace_2() {
        String invalidValue = "";
        assertEquals("", ActivityDetails.getAfterSpace(invalidValue));
    }

}
