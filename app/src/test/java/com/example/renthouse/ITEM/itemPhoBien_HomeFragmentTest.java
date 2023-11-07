package com.example.renthouse.ITEM;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class itemPhoBien_HomeFragmentTest {
    private itemPhoBien_HomeFragment object;
    @Before
    public void setup() {
        object = new itemPhoBien_HomeFragment("img.png", "VN");
    }
    @Test
    public void getImage() {
        assertEquals("img.png", object.getImage());
    }

    @Test
    public void setImage() {
        object.setImage("jpg.jpg");
        assertEquals("jpg.jpg", object.getImage());
    }

    @Test
    public void getLocation() {
        assertEquals("VN", object.getLocation());
    }

    @Test
    public void setLocation() {
        object.setLocation("HaNoi");
        assertEquals("HaNoi", object.getLocation());
    }
}