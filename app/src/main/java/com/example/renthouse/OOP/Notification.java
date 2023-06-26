package com.example.renthouse.OOP;

import com.example.renthouse.Chat.Messages.MessagesList;

public class Notification {
    private String title;
    private String body;
    private Room attachedRoom;
    private MessagesList attachedMessage;
    private String dateTime;
    private String type;

    private boolean read;

    public Notification() {
    }

    public Notification(String title, String body, String type) {
        this.title = title;
        this.body = body;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Room getAttachedRoom() {
        return attachedRoom;
    }

    public void setAttachedRoom(Room attachedRoom) {
        this.attachedRoom = attachedRoom;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessagesList getAttachedMessage() {
        return attachedMessage;
    }

    public void setAttachedMessage(MessagesList attachedMessage) {
        this.attachedMessage = attachedMessage;
    }
}
