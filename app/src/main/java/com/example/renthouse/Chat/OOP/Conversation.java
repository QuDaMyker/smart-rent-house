package com.example.renthouse.Chat.OOP;

public class Conversation {
    private String sendId;
    private String receiveId;
    private String lastMessage;
    private String sendDate;
    private String sendTime;
    private Boolean seenMsg;

    public Conversation(String sendId, String receiveId, String lastMessage, String sendDate, String sendTime, Boolean seenMsg) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.lastMessage = lastMessage;
        this.sendDate = sendDate;
        this.sendTime = sendTime;
        this.seenMsg = seenMsg;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getSeenMsg() {
        return seenMsg;
    }

    public void setSeenMsg(Boolean seenMsg) {
        this.seenMsg = seenMsg;
    }
}
