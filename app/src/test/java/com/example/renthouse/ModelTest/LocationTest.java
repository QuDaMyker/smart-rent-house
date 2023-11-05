package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Ward;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {

    private LocationTemp location;

    @Before
    public void setUp() {
        City city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
        District district = new District("Quan 1", "Quan", "quan-1", "Quận 1", "1", "quan-1", "001", "TPHCM");
        Ward ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");
        location = new LocationTemp("Nguyen Van B", "123", city, district, ward);
    }

    @Test
    public void testConstructor() {
        LocationTemp location = new LocationTemp();
        assertNotNull(location);
    }

    @Test
    public void testGetStreet() {
        assertEquals("Nguyen Van B", location.getStreet());
    }

    @Test
    public void testSetStreet() {
        location.setStreet("Le Van Sy");
        assertEquals("Le Van Sy", location.getStreet());
    }

    @Test
    public void testGetAddress() {
        assertEquals("123", location.getAddress());
    }

    @Test
    public void testSetAddress() {
        location.setAddress("456");
        assertEquals("456", location.getAddress());
    }

    @Test
    public void testGetCity() {
        City city = location.getCity();
        assertNotNull(city);
        assertEquals("Ho Chi Minh", city.getName());
    }

    @Test
    public void testSetCity() {
        City newCity = new City("Hanoi", "ha-noi", "thanh-pho", "Thành phố Hà Nội", "01");
        location.setCity(newCity);
        assertEquals("Hanoi", location.getCity().getName());
    }

    @Test
    public void testGetDistrict() {
        District district = location.getDistrict();
        assertNotNull(district);
        assertEquals("Quan 1", district.getName());
    }

    @Test
    public void testSetDistrict() {
        District newDistrict = new District("Quan 2", "Quan", "quan-2", "Quận 2", "2", "quan-2", "002", "TPHCM");
        location.setDistrict(newDistrict);
        assertEquals("Quan 2", location.getDistrict().getName());
    }

    @Test
    public void testGetWard() {
        Ward ward = location.getWard();
        assertNotNull(ward);
        assertEquals("Phu Nhuan", ward.getName());
    }

    @Test
    public void testSetWard() {
        Ward newWard = new Ward("Binh Thanh", "Phuong", "binh-thanh", "Phường Binh Thanh", "2", "binh-thanh", "002", "Q2");
        location.setWard(newWard);
        assertEquals("Binh Thanh", location.getWard().getName());
    }

    @Test
    public void testLocationToString() {
        String expected = "123 Đường Nguyen Van B, Phường Phu Nhuan, Quận 1, Thành phố Hồ Chí Minh, Vietnam";
        String result = location.LocationToString();
        assertEquals(expected, result);
    }
}
