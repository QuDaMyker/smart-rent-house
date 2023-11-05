package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DistrictTest {

    private District district;

    @Before
    public void setUp() {
        district = new District("Long Xuyên","thanh-pho","long-xuyen","Thành phố Long Xuyên","Long Xuyên, An Giang","Thành phố Long Xuyên, Tỉnh An Giang","883","89");
    }

    @Test
    public void testConstructor() {
        District district = new District();
        assertNotNull(district);
    }

    @Test
    public void testGetName() {
        assertEquals("Long Xuyên", district.getName());
    }

    @Test
    public void testSetName() {
        district.setName("Quận 1");
        assertEquals("Quận 1", district.getName());
    }

    @Test
    public void testGetType() {
        assertEquals("thanh-pho", district.getType());
    }

    @Test
    public void testSetType() {
        district.setType("quan");
        assertEquals("quan", district.getType());
    }

    @Test
    public void testGetSlug() {
        assertEquals("long-xuyen", district.getSlug());
    }

    @Test
    public void testSetSlug() {
        district.setSlug("quan-1");
        assertEquals("quan-1", district.getSlug());
    }

    @Test
    public void testGetNameWithType() {
        assertEquals("Thành phố Long Xuyên", district.getName_with_type());
    }

    @Test
    public void testSetNameWithType() {
        district.setName_with_type("Quận 1");
        assertEquals("Quận 1", district.getName_with_type());
    }

    @Test
    public void testGetPath() {
        assertEquals("Long Xuyên, An Giang", district.getPath());
    }

    @Test
    public void testSetPath() {
        district.setPath("1, Hồ Chí Minh");
        assertEquals("1, Hồ Chí Minh", district.getPath());
    }

    @Test
    public void testGetPathWithType() {
        assertEquals("Thành phố Long Xuyên, Tỉnh An Giang", district.getPath_with_type());
    }

    @Test
    public void testSetPathWithType() {
        district.setPath_with_type("Quận 1, Thành phố Hồ Chí Minh");
        assertEquals("Quận 1, Thành phố Hồ Chí Minh", district.getPath_with_type());
    }

    @Test
    public void testGetCode() {
        assertEquals("883", district.getCode());
    }

    @Test
    public void testSetCode() {
        district.setCode("760");
        assertEquals("760", district.getCode());
    }

    @Test
    public void testGetParentCode() {
        assertEquals("89", district.getParent_code());
    }

    @Test
    public void testSetParentCode() {
        district.setParent_code("79");
        assertEquals("79", district.getParent_code());
    }
}
