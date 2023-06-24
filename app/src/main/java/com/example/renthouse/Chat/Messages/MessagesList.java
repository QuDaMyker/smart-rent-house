package com.example.renthouse.Chat.Messages;

public class MessagesList {
    private String currentKey;
    private String name, email, lastMessages, profilePic, otherKey;
    private int unseenMessages;

    public MessagesList() {
    }

    public MessagesList(String currentKey, String name, String email, String lastMessages, String profilePic, String otherKey, int unseenMessages) {
        this.currentKey = currentKey;
        this.name = name;
        this.email = email;
        this.lastMessages = lastMessages;
        this.profilePic = profilePic;
        this.otherKey = otherKey;
        this.unseenMessages = unseenMessages;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastMessages() {
        return lastMessages;
    }

    public void setLastMessages(String lastMessages) {
        this.lastMessages = lastMessages;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getOtherKey() {
        return otherKey;
    }

    public void setOtherKey(String otherKey) {
        this.otherKey = otherKey;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }
}
