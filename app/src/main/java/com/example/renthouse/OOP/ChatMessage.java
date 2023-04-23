package com.example.renthouse.OOP;

public class ChatMessage {
    private String sendId;
    private String receiveId;
    private String message;
    private String dateTime;

    public ChatMessage(String sendId, String receiveId, String message, String dateTime) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
