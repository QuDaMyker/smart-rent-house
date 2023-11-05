package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.Ward;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WardTest {

    private Ward ward;

    @Before
    public void setUp() {
        ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");
    }

    @Test
    public void testConstructor() {
        Ward ward = new Ward();
        assertNotNull(ward);
    }

    @Test
    public void testGetName() {
        assertEquals("Phu Nhuan", ward.getName());
    }

    @Test
    public void testSetName() {
        ward.setName("Binh Thanh");
        assertEquals("Binh Thanh", ward.getName());
    }

    @Test
    public void testGetType() {
        assertEquals("Phuong", ward.getType());
    }

    @Test
    public void testSetType() {
        ward.setType("Quan");
        assertEquals("Quan", ward.getType());
    }

    @Test
    public void testGetSlug() {
        assertEquals("phu-nhuan", ward.getSlug());
    }

    @Test
    public void testSetSlug() {
        ward.setSlug("binh-thanh");
        assertEquals("binh-thanh", ward.getSlug());
    }

    @Test
    public void testGetNameWithType() {
        assertEquals("Phường Phu Nhuan", ward.getName_with_type());
    }

    @Test
    public void testSetNameWithType() {
        ward.setName_with_type("Phường Binh Thanh");
        assertEquals("Phường Binh Thanh", ward.getName_with_type());
    }

    @Test
    public void testGetPath() {
        assertEquals("1", ward.getPath());
    }

    @Test
    public void testSetPath() {
        ward.setPath("2");
        assertEquals("2", ward.getPath());
    }

    @Test
    public void testGetPathWithType() {
        assertEquals("phu-nhuan", ward.getPath_with_type());
    }

    @Test
    public void testSetPathWithType() {
        ward.setPath_with_type("binh-thanh");
        assertEquals("binh-thanh", ward.getPath_with_type());
    }

    @Test
    public void testGetCode() {
        assertEquals("001", ward.getCode());
    }

    @Test
    public void testSetCode() {
        ward.setCode("002");
        assertEquals("002", ward.getCode());
    }

    @Test
    public void testGetParentCode() {
        assertEquals("Q1", ward.getParent_code());
    }

    @Test
    public void testSetParentCode() {
        ward.setParent_code("Q2");
        assertEquals("Q2", ward.getParent_code());
    }
}
