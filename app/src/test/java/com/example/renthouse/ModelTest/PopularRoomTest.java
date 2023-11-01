package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.PhoBien;

public class PopularRoomTest {

    private PhoBien phoBien;

    @Before
    public void setUp() {
        phoBien = new PhoBien("image_url", "12345", "Example Name");
    }

    @Test
    public void testGetIdphobien() {
        assertEquals("12345", phoBien.getIdphobien());
    }

    @Test
    public void testSetIdphobien() {
        phoBien.setIdphobien("54321");
        assertEquals("54321", phoBien.getIdphobien());
    }

    @Test
    public void testGetImage() {
        assertEquals("image_url", phoBien.getImage());
    }

    @Test
    public void testSetImage() {
        phoBien.setImage("new_image_url");
        assertEquals("new_image_url", phoBien.getImage());
    }

    @Test
    public void testGetName() {
        assertEquals("Example Name", phoBien.getName());
    }

    @Test
    public void testSetName() {
        phoBien.setName("New Name");
        assertEquals("New Name", phoBien.getName());
    }
}
