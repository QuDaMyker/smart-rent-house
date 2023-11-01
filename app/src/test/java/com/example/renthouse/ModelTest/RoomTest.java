package com.example.renthouse.ModelTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.ObjectFilter;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.OOP.Ward;
import java.util.ArrayList;
import java.util.List;

public class RoomTest {

    private Room room;

    @Before
    public void setUp() {
        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Internet");
        utilities.add("Electricity");

        City city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
        District district = new District("Quan 1", "Quan", "quan-1", "Quận 1", "1", "quan-1", "001", "TPHCM");
        Ward ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");
        LocationTemp location = new LocationTemp("Nguyen Van B", "123", city, district, ward);
        AccountClass account = new AccountClass("Le Bao Nhu", "nhu@example.com", "0987654321", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
        room = new Room("1", "Nhà trọ đẹp", "Thuê đi", "homestay", 2, "All", 25, 1000000, 1000000, 0,0,0,false, 0, location, utilities, account, "0987654321", "2023-10-29", false, "pending");
    }

    @Test
    public void testConstructor() {
        Room room = new Room();
        assertNotNull(room);
    }

    @Test
    public void testGetId() {
        assertEquals("1", room.getId());
    }

    @Test
    public void testSetId() {
        room.setId("2");
        assertEquals("2", room.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Nhà trọ đẹp", room.getTitle());
    }

    @Test
    public void testSetTitle() {
        room.setTitle("Nhà trọ xấu");
        assertEquals("Nhà trọ xấu", room.getTitle());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Thuê đi", room.getDescription());
    }

    @Test
    public void testSetDescription() {
        room.setDescription("Đừng thuê");
        assertEquals("Đừng thuê", room.getDescription());
    }

    @Test
    public void testGetRoomType() {
        assertEquals("homestay", room.getRoomType());
    }

    @Test
    public void testSetRoomType() {
        room.setRoomType("apartment");
        assertEquals("apartment", room.getRoomType());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, room.getCapacity());
    }

    @Test
    public void testSetCapacity() {
        room.setCapacity(3);
        assertEquals(3, room.getCapacity());
    }

    @Test
    public void testGetGender() {
        assertEquals("All", room.getGender());
    }

    @Test
    public void testSetGender() {
        room.setGender("Female");
        assertEquals("Female", room.getGender());
    }

    @Test
    public void testGetArea() {
        assertEquals(25.0, room.getArea(), 0.001);
    }

    @Test
    public void testSetArea() {
        room.setArea(30);
        assertEquals(30, room.getArea(), 0.001);
    }

    @Test
    public void testGetPrice() {
        assertEquals(1000000, room.getPrice());
    }

    @Test
    public void testSetPrice() {
        room.setPrice(1200000);
        assertEquals(1200000, room.getPrice());
    }

    @Test
    public void testGetDeposit() {
        assertEquals(1000000, room.getDeposit());
    }

    @Test
    public void testSetDeposit() {
        room.setDeposit(1500000);
        assertEquals(1500000, room.getDeposit());
    }

    @Test
    public void testGetElectricityCost() {
        assertEquals(0, room.getElectricityCost());
    }

    @Test
    public void testSetElectricityCost() {
        room.setElectricityCost(200000);
        assertEquals(200000, room.getElectricityCost());
    }

    @Test
    public void testGetWaterCost() {
        assertEquals(0, room.getWaterCost());
    }

    @Test
    public void testSetWaterCost() {
        room.setWaterCost(50000);
        assertEquals(50000, room.getWaterCost());
    }

    @Test
    public void testGetInternetCost() {
        assertEquals(0, room.getInternetCost());
    }

    @Test
    public void testSetInternetCost() {
        room.setInternetCost(100000);
        assertEquals(100000, room.getInternetCost());
    }

    @Test
    public void testIsParking() {
        assertFalse(room.isParking());
    }

    @Test
    public void testSetParking() {
        room.setParking(true);
        assertTrue(room.isParking());
    }

    @Test
    public void testGetParkingFee() {
        assertEquals(0, room.getParkingFee());
    }

    @Test
    public void testSetParkingFee() {
        room.setParkingFee(50000);
        assertEquals(50000, room.getParkingFee());
    }

    @Test
    public void testGetLocation() {
        assertNotNull(room.getLocation());
    }

    @Test
    public void testSetLocation() {
        City city = new City("Hanoi", "hanoi", "thanh-pho", "Hà Nội", "79");
        District district = new District("Quan Hoan Kiem", "Quan", "quan-hoan-kiem", "Quận Hoàn Kiếm", "1", "quan-hoan-kiem", "001", "HN");
        Ward ward = new Ward("Hoan Kiem", "Phuong", "hoan-kiem", "Phường Hoàn Kiếm", "1", "hoan-kiem", "001", "QHK");
        LocationTemp newLocation = new LocationTemp("Pho Co", "456", city, district, ward);
        room.setLocation(newLocation);
        assertEquals(newLocation, room.getLocation());
    }

    @Test
    public void testGetImages() {
        assertNull(room.getImages());
    }

    @Test
    public void testSetImages() {
        List<String> images = new ArrayList<>();
        images.add("image1.jpg");
        images.add("image2.jpg");
        room.setImages(images);
        assertEquals(images, room.getImages());
    }

    @Test
    public void testGetUtilities() {
        assertNotNull(room.getUtilities());
        assertEquals(2, room.getUtilities().size());
        assertTrue(room.getUtilities().contains("Internet"));
        assertTrue(room.getUtilities().contains("Electricity"));
    }

    @Test
    public void testSetUtilities() {
        List<String> newUtilities = new ArrayList<>();
        newUtilities.add("Water");
        newUtilities.add("Gas");
        room.setUtilities(newUtilities);
        assertEquals(newUtilities, room.getUtilities());
    }

    @Test
    public void testGetCreatedBy() {
        assertNotNull(room.getCreatedBy());
    }

    @Test
    public void testSetCreatedBy() {
        AccountClass newCreatedBy = new AccountClass("John Doe", "john@example.com", "123456789", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
        room.setCreatedBy(newCreatedBy);
        assertEquals(newCreatedBy, room.getCreatedBy());
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals("0987654321", room.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber() {
        room.setPhoneNumber("0123456789");
        assertEquals("0123456789", room.getPhoneNumber());
    }

    @Test
    public void testGetDateTime() {
        assertEquals("2023-10-29", room.getDateTime());
    }

    @Test
    public void testSetDateTime() {
        room.setDateTime("2023-11-01");
        assertEquals("2023-11-01", room.getDateTime());
    }

    @Test
    public void testIsRented() {
        assertFalse(room.isRented());
    }

    @Test
    public void testSetRented() {
        room.setRented(true);
        assertTrue(room.isRented());
    }

    @Test
    public void testGetStatus() {
        assertEquals("pending", room.getStatus());
    }

    @Test
    public void testSetStatus() {
        room.setStatus("approved");
        assertEquals("approved", room.getStatus());
    }
}
