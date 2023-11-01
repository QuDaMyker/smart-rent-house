package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.Region;

public class RegionTest {

    private Region region;

    @Before
    public void setUp() {
        region = new Region("District A, Type", "City A, Type", 10);
    }

    @Test
    public void testGetDistrictNameWithType() {
        assertEquals("District A, Type", region.getDistrictNameWithType());
    }

    @Test
    public void testSetDistrictNameWithType() {
        region.setDistrictNameWithType("New District, Type");
        assertEquals("New District, Type", region.getDistrictNameWithType());
    }

    @Test
    public void testGetCityNameWithType() {
        assertEquals("City A, Type", region.getCityNameWithType());
    }

    @Test
    public void testSetCityNameWithType() {
        region.setCityNameWithType("New City, Type");
        assertEquals("New City, Type", region.getCityNameWithType());
    }

    @Test
    public void testGetSoLuong() {
        assertEquals(10, region.getSoLuong());
    }

    @Test
    public void testSetSoLuong() {
        region.setSoLuong(20);
        assertEquals(20, region.getSoLuong());
    }

    @Test
    public void testConstructor() {
        Region newRegion = new Region("District B, Type", "City B, Type", 15);

        assertEquals("District B, Type", newRegion.getDistrictNameWithType());
        assertEquals("City B, Type", newRegion.getCityNameWithType());
        assertEquals(15, newRegion.getSoLuong());
    }

    @Test
    public void testDefaultConstructor() {
        Region defaultRegion = new Region();

        assertNull(defaultRegion.getDistrictNameWithType());
        assertNull(defaultRegion.getCityNameWithType());
        assertEquals(0, defaultRegion.getSoLuong());
    }

}
