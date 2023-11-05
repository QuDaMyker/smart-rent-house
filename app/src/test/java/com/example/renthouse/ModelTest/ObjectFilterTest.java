package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.ObjectFilter;

public class ObjectFilterTest {

    private ObjectFilter objectFilter;

    @Before
    public void setUp() {
        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Internet");
        utilities.add("Electricity");
        objectFilter = new ObjectFilter("1000", utilities, "Single", "Female", "Price");
    }

    @Test
    public void testConstructor() {
        ObjectFilter objectFilter = new ObjectFilter();
        assertNotNull(objectFilter);
    }

    @Test
    public void testGetPriceString() {
        assertEquals("1000", objectFilter.getPriceString());
    }

    @Test
    public void testSetPriceString() {
        objectFilter.setPriceString("2000");
        assertEquals("2000", objectFilter.getPriceString());
    }

    @Test
    public void testGetUtilitesString() {
        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Internet");
        utilities.add("Electricity");
        assertEquals(utilities, objectFilter.getUtilitesString());
    }

    @Test
    public void testSetUtilitesString() {
        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Water");
        utilities.add("Gas");
        objectFilter.setUtilitesString(utilities);
        assertEquals(utilities, objectFilter.getUtilitesString());
    }

    @Test
    public void testGetTypeRoom() {
        assertEquals("Single", objectFilter.getTypeRoom());
    }

    @Test
    public void testSetTypeRoom() {
        objectFilter.setTypeRoom("Double");
        assertEquals("Double", objectFilter.getTypeRoom());
    }

    @Test
    public void testGetAmountAndGender() {
        assertEquals("Female", objectFilter.getAmountAndGender());
    }

    @Test
    public void testSetAmountAndGender() {
        objectFilter.setAmountAndGender("Male");
        assertEquals("Male", objectFilter.getAmountAndGender());
    }

    @Test
    public void testGetSortString() {
        assertEquals("Price", objectFilter.getSortString());
    }

    @Test
    public void testSetSortString() {
        objectFilter.setSortString("Distance");
        assertEquals("Distance", objectFilter.getSortString());
    }

    @Test
    public void testClearData() {
        objectFilter.clearData();
        assertEquals("", objectFilter.getPriceString());
        assertEquals(new ArrayList<String>(), objectFilter.getUtilitesString());
        assertEquals("", objectFilter.getTypeRoom());
        assertEquals("", objectFilter.getAmountAndGender());
        assertEquals("", objectFilter.getSortString());
    }
}
