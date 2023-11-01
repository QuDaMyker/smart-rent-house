package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.University;

public class UniversityTest {

    private University university;

    @Before
    public void setUp() {
        university = new University("Example University", "123 Main Street");
    }

    @Test
    public void testGetName() {
        assertEquals("Example University", university.getName());
    }

    @Test
    public void testSetName() {
        university.setName("New University");
        assertEquals("New University", university.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("123 Main Street", university.getAddress());
    }

    @Test
    public void testSetAddress() {
        university.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", university.getAddress());
    }
}
