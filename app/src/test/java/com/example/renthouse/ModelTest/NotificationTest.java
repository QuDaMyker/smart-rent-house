package com.example.renthouse.ModelTest;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.Notification;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificationTest {

    private Notification notification;

    @Before
    public void setUp() {
        notification = new Notification("New Message", "You have a new message", "chat");
    }

    @Test
    public void testConstructor() {
        Notification notification = new Notification();
        assertNotNull(notification);
    }

    @Test
    public void testGetTitle() {
        assertEquals("New Message", notification.getTitle());
    }

    @Test
    public void testSetTitle() {
        notification.setTitle("New Notification");
        assertEquals("New Notification", notification.getTitle());
    }

    @Test
    public void testGetBody() {
        assertEquals("You have a new message", notification.getBody());
    }

    @Test
    public void testSetBody() {
        notification.setBody("A new notification has arrived");
        assertEquals("A new notification has arrived", notification.getBody());
    }

    @Test
    public void testGetDateTime() {
        assertNull(notification.getDateTime());
    }

    @Test
    public void testSetDateTime() {
        notification.setDateTime("2023-10-30 14:30:00");
        assertEquals("2023-10-30 14:30:00", notification.getDateTime());
    }

    @Test
    public void testIsRead() {
        assertFalse(notification.isRead());
    }

    @Test
    public void testSetRead() {
        notification.setRead(true);
        assertTrue(notification.isRead());
    }

    @Test
    public void testGetType() {
        assertEquals("chat", notification.getType());
    }

    @Test
    public void testSetType() {
        notification.setType("system");
        assertEquals("system", notification.getType());
    }

    @Test
    public void testGetAttachedRoomKey() {
        assertNull(notification.getAttachedRoomKey());
    }

    @Test
    public void testSetAttachedRoomKey() {
        notification.setAttachedRoomKey("room_123");
        assertEquals("room_123", notification.getAttachedRoomKey());
    }

    @Test
    public void testGetAttachedMessageKey() {
        assertNull(notification.getAttachedMessageKey());
    }

    @Test
    public void testSetAttachedMessageKey() {
        notification.setAttachedMessageKey("message_456");
        assertEquals("message_456", notification.getAttachedMessageKey());
    }
}