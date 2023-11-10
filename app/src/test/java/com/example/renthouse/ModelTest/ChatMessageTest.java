package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.ChatMessage;

public class ChatMessageTest {

    private ChatMessage chatMessage;

    @Before
    public void setUp() {
        chatMessage = new ChatMessage("sender123", "receiver456", "Hello!", "2023-10-29 14:30:00");
    }

    @Test
    public void testGetSendId() {
        assertEquals("sender123", chatMessage.getSendId());
    }

    @Test
    public void testSetSendId() {
        chatMessage.setSendId("newSender789");
        assertEquals("newSender789", chatMessage.getSendId());
    }

    @Test
    public void testGetReceiveId() {
        assertEquals("receiver456", chatMessage.getReceiveId());
    }

    @Test
    public void testSetReceiveId() {
        chatMessage.setReceiveId("newReceiver789");
        assertEquals("newReceiver789", chatMessage.getReceiveId());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Hello!", chatMessage.getMessage());
    }

    @Test
    public void testSetMessage() {
        chatMessage.setMessage("Hi there!");
        assertEquals("Hi there!", chatMessage.getMessage());
    }

    @Test
    public void testGetDateTime() {
        assertEquals("2023-10-29 14:30:00", chatMessage.getDateTime());
    }

    @Test
    public void testSetDateTime() {
        chatMessage.setDateTime("2023-11-01 15:45:00");
        assertEquals("2023-11-01 15:45:00", chatMessage.getDateTime());
    }
}