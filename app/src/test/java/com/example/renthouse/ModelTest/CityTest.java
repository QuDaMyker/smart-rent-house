package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.City;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CityTest {

    private City city;

    @Before
    public void setUp() {
        city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
    }

    @Test
    public void testConstructor() {
        City city = new City();
        assertNotNull(city);
    }

    @Test
    public void testGetName() {
        assertEquals("Ho Chi Minh", city.getName());
    }

    @Test
    public void testSetName() {
        city.setName("Hanoi");
        assertEquals("Hanoi", city.getName());
    }

    @Test
    public void testGetSlug() {
        assertEquals("ho-chi-minh", city.getSlug());
    }

    @Test
    public void testSetSlug() {
        city.setSlug("hanoi");
        assertEquals("hanoi", city.getSlug());
    }

    @Test
    public void testGetType() {
        assertEquals("thanh-pho", city.getType());
    }

    @Test
    public void testSetType() {
        city.setType("tinh");
        assertEquals("tinh", city.getType());
    }

    @Test
    public void testGetNameWithType() {
        assertEquals("Thành phố Hồ Chí Minh", city.getName_with_type());
    }

    @Test
    public void testSetNameWithType() {
        city.setName_with_type("Hanoi");
        assertEquals("Hanoi", city.getName_with_type());
    }

    @Test
    public void testGetCode() {
        assertEquals("79", city.getCode());
    }

    @Test
    public void testSetCode() {
        city.setCode("01");
        assertEquals("01", city.getCode());
    }
}
