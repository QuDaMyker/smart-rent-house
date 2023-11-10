package com.example.renthouse.JSONReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import com.example.renthouse.Test.Location;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class JSONReaderLocationTest {
    private JSONReaderLocation jsonReaderLocation;
    @Mock
    private Context mockContext;
    @Mock
    private List<Location> mockLocationList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockLocationList = new ArrayList<>();
        jsonReaderLocation = new JSONReaderLocation(mockContext);
    }
    @Test
    public void getLocationList() {
        List<Location> location = new ArrayList<>();
        location.add(new Location("Đặng Văn Bi, Thủ Đức"));
        assertEquals(location, jsonReaderLocation.getLocationList() );
    }

    @Test
    public void setLocationList() {
        List<Location> location = new ArrayList<>();
        location.add(new Location("Đặng Văn Bi, Thủ Đức"));
        jsonReaderLocation.setLocationList(location);
        assertEquals(location, jsonReaderLocation.getLocationList());
    }

    @Test
    public void getContext() {
        assertEquals(mockContext, jsonReaderLocation.getContext());
    }

    @Test
    public void setContext() {
        jsonReaderLocation.setContext(mockContext);
        assertEquals(mockContext, jsonReaderLocation.getContext());
    }

    @Test
    public void readDatabaseLocation() {
        String address = "TP Hồ Chí Minh";
        jsonReaderLocation.readDatabaseLocation(address);
        int size = jsonReaderLocation.getLocationList().size();
        assertTrue(size > 0);
    }

    @Test
    public void testConstructorAndGettersSetters() {
        // Create test data
        String testAddress = "Test Address";

        // Create an instance of Location
        Location location = new Location(testAddress);

        // Test getters
        assertEquals(testAddress, location.getAddress());

        // Test setters
        String newAddress = "New Test Address";
        location.setAddress(newAddress);
        assertEquals(newAddress, location.getAddress());
    }
}