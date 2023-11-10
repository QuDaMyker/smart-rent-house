package com.example.renthouse.Chat.Messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessagesListTest {

    @Test
    public void testConstructorAndGettersSetters() {
        // Create test data
        String currentKey = "testCurrentKey";
        String name = "Test Name";
        String email = "test@example.com";
        String lastMessages = "Test Last Messages";
        String profilePic = "test_profile_pic.jpg";
        String otherKey = "testOtherKey";
        boolean unseenMessages = true;
        String sendTime = "2023-11-10 12:00 PM";

        // Create an instance of MessagesList
        MessagesList messagesList = new MessagesList(currentKey, name, email, lastMessages, profilePic, otherKey, unseenMessages, sendTime);

        // Test getters
        assertEquals(currentKey, messagesList.getCurrentKey());
        assertEquals(name, messagesList.getName());
        assertEquals(email, messagesList.getEmail());
        assertEquals(lastMessages, messagesList.getLastMessages());
        assertEquals(profilePic, messagesList.getProfilePic());
        assertEquals(otherKey, messagesList.getOtherKey());
        assertEquals(unseenMessages, messagesList.isUnseenMessages());
        assertEquals(sendTime, messagesList.getSendTime());

        // Test setters
        String newCurrentKey = "newTestCurrentKey";
        messagesList.setCurrentKey(newCurrentKey);
        assertEquals(newCurrentKey, messagesList.getCurrentKey());

        String newName = "New Test Name";
        messagesList.setName(newName);
        assertEquals(newName, messagesList.getName());

        String newEmail = "new_test@example.com";
        messagesList.setEmail(newEmail);
        assertEquals(newEmail, messagesList.getEmail());

        String newLastMessages = "New Test Last Messages";
        messagesList.setLastMessages(newLastMessages);
        assertEquals(newLastMessages, messagesList.getLastMessages());

        String newProfilePic = "new_test_profile_pic.jpg";
        messagesList.setProfilePic(newProfilePic);
        assertEquals(newProfilePic, messagesList.getProfilePic());

        String newOtherKey = "newTestOtherKey";
        messagesList.setOtherKey(newOtherKey);
        assertEquals(newOtherKey, messagesList.getOtherKey());

        boolean newUnseenMessages = false;
        messagesList.setUnseenMessages(newUnseenMessages);
        assertEquals(newUnseenMessages, messagesList.isUnseenMessages());

        String newSendTime = "2023-11-11 1:00 PM";
        messagesList.setSendTime(newSendTime);
        assertEquals(newSendTime, messagesList.getSendTime());
    }

    @Test
    public void testEmptyConstructor() {
        // Test the empty constructor
        MessagesList messagesList = new MessagesList();

        // Ensure that the object is not null
        assertNotNull(messagesList);
    }
}
