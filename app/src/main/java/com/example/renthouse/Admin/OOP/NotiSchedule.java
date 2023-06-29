package com.example.renthouse.Admin.OOP;

public class NotiSchedule {
    private String title;
    private String content;
    private String receiver;
    private String date;
    private String time;
    private String loop;

    public NotiSchedule() {
    }

    public NotiSchedule(String title, String content, String receiver, String date, String time, String loop) {
        this.title = title;
        this.content = content;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.loop = loop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoop() {
        return loop;
    }

    public void setLoop(String loop) {
        this.loop = loop;
    }
}
