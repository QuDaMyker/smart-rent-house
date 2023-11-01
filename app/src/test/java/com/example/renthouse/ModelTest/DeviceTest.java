package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.Device;

public class DeviceTest {

    private Device device;

    @Before
    public void setUp() {
        device = new Device("user123", "token123", true, false, true, false);
    }

    @Test
    public void testConstructor() {
        Device device = new Device();
        assertNotNull(device);
    }

    @Test
    public void testGetUserId() {
        assertEquals("user123", device.getUserId());
    }

    @Test
    public void testSetUserId() {
        device.setUserId("newUser456");
        assertEquals("newUser456", device.getUserId());
    }

    @Test
    public void testGetToken() {
        assertEquals("token123", device.getToken());
    }

    @Test
    public void testSetToken() {
        device.setToken("newToken456");
        assertEquals("newToken456", device.getToken());
    }

    @Test
    public void testIsRoomNoti() {
        assertTrue(device.isRoomNoti());
    }

    @Test
    public void testSetRoomNoti() {
        device.setRoomNoti(false);
        assertFalse(device.isRoomNoti());
    }

    @Test
    public void testIsChatNoti() {
        assertFalse(device.isChatNoti());
    }

    @Test
    public void testSetChatNoti() {
        device.setChatNoti(true);
        assertTrue(device.isChatNoti());
    }

    @Test
    public void testIsLikeNoti() {
        assertTrue(device.isLikeNoti());
    }

    @Test
    public void testSetLikeNoti() {
        device.setLikeNoti(false);
        assertFalse(device.isLikeNoti());
    }

    @Test
    public void testIsScheduleNoti() {
        assertFalse(device.isScheduleNoti());
    }

    @Test
    public void testSetScheduleNoti() {
        device.setScheduleNoti(true);
        assertTrue(device.isScheduleNoti());
    }
}
