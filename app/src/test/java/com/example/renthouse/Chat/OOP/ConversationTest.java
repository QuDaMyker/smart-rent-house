package com.example.renthouse.Chat.OOP;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConversationTest {
    private Conversation conversation;

    @Before
    public void setup() {
        conversation = new Conversation("senderId", "receiveId", "Hello", "01/01/2023", "12:06:00", true);
    }

    @Test
    public void getSendId() {
        assertEquals("senderId", conversation.getSendId());
    }

    @Test
    public void setSendId() {
        conversation.setSendId("setSenderId");
        assertEquals("setSenderId", conversation.getSendId());
    }

    @Test
    public void getReceiveId() {
        assertEquals("receiveId", conversation.getReceiveId());
    }

    @Test
    public void setReceiveId() {
        conversation.setReceiveId("setReceiveId");
        assertEquals("setReceiveId", conversation.getReceiveId());
    }

    @Test
    public void getLastMessage() {
        assertEquals("Hello", conversation.getLastMessage());
    }

    @Test
    public void setLastMessage() {
        conversation.setLastMessage("Hi");
        assertEquals("Hi", conversation.getLastMessage());
    }

    @Test
    public void getSendDate() {
        assertEquals("01/01/2023", conversation.getSendDate());
    }

    @Test
    public void setSendDate() {
        conversation.setSendDate("02/02/2023");
        assertEquals("02/02/2023", conversation.getSendDate());
    }

    @Test
    public void getSendTime() {
        assertEquals("12:06:00", conversation.getSendTime());
    }

    @Test
    public void setSendTime() {
        conversation.setSendTime("13:07:00");
        assertEquals("13:07:00", conversation.getSendTime());
    }

    @Test
    public void getSeenMsg() {
        assertTrue(conversation.getSeenMsg());
    }

    @Test
    public void setSeenMsg() {
        conversation.setSeenMsg(false);
        assertFalse(conversation.getSeenMsg());
    }
}
