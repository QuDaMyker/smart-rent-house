package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.ObjectSearch;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ObjectSearchTest {

    private ObjectSearch objectSearch;

    @Before
    public void setUp() {
        ArrayList<Long> prices = new ArrayList<>();
        prices.add(1000L);
        prices.add(1500L);

        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("WiFi");
        utilities.add("Parking");

        objectSearch = new ObjectSearch(prices, utilities, "Single", 2, "Female", "Price Ascending");
    }

    @Test
    public void testGetPrice() {
        ArrayList<Long> expectedPrices = new ArrayList<>();
        expectedPrices.add(1000L);
        expectedPrices.add(1500L);

        assertEquals(expectedPrices, objectSearch.getPrice());
    }

    @Test
    public void testSetPrice() {
        ArrayList<Long> newPrices = new ArrayList<>();
        newPrices.add(1200L);
        newPrices.add(1800L);

        objectSearch.setPrice(newPrices);
        assertEquals(newPrices, objectSearch.getPrice());
    }

    @Test
    public void testGetUtilities() {
        ArrayList<String> expectedUtilities = new ArrayList<>();
        expectedUtilities.add("WiFi");
        expectedUtilities.add("Parking");

        assertEquals(expectedUtilities, objectSearch.getUtilities());
    }

    @Test
    public void testSetUtilities() {
        ArrayList<String> newUtilities = new ArrayList<>();
        newUtilities.add("Gym");
        newUtilities.add("Swimming Pool");

        objectSearch.setUtilities(newUtilities);
        assertEquals(newUtilities, objectSearch.getUtilities());
    }

    @Test
    public void testGetType() {
        assertEquals("Single", objectSearch.getType());
    }

    @Test
    public void testSetType() {
        objectSearch.setType("Double");
        assertEquals("Double", objectSearch.getType());
    }

    @Test
    public void testGetAmount() {
        assertEquals(2, objectSearch.getAmount());
    }

    @Test
    public void testSetAmount() {
        objectSearch.setAmount(3);
        assertEquals(3, objectSearch.getAmount());
    }

    @Test
    public void testGetGender() {
        assertEquals("Female", objectSearch.getGender());
    }

    @Test
    public void testSetGender() {
        objectSearch.setGender("Male");
        assertEquals("Male", objectSearch.getGender());
    }

    @Test
    public void testGetSort() {
        assertEquals("Price Ascending", objectSearch.getSort());
    }

    @Test
    public void testSetSort() {
        objectSearch.setSort("Price Descending");
        assertEquals("Price Descending", objectSearch.getSort());
    }

    @Test
    public void testClearData() {
        objectSearch.clearData();
        assertEquals(new ArrayList<Long>(), objectSearch.getPrice());
        assertEquals(new ArrayList<String>(), objectSearch.getUtilities());
        assertEquals("", objectSearch.getType());
        assertEquals(-1, objectSearch.getAmount());
        assertEquals("", objectSearch.getGender());
        assertEquals("", objectSearch.getSort());
    }

    @Test
    public void testDefaultConstructor() {
        ObjectSearch objectSearch = new ObjectSearch();
        assertEquals(new ArrayList<Long>(), objectSearch.getPrice());
        assertEquals(new ArrayList<String>(), objectSearch.getUtilities());
        assertEquals("", objectSearch.getType());
        assertEquals(-1, objectSearch.getAmount());
        assertEquals("", objectSearch.getGender());
        assertEquals("", objectSearch.getSort());
        assertEquals("", objectSearch.getPath());
    }

    @Test
    public void testSetPath() {
        objectSearch.setPath("path/to/search");
        assertEquals("path/to/search", objectSearch.getPath());
    }

}
