package com.example.renthouse.Chat.Dashboard;

public class ChatList {
    private boolean dangDangNhap = false;
    private String email, name, message, date, time;

    public ChatList(boolean dangDangNhap, String email, String name, String message, String date, String time) {
        this.dangDangNhap = dangDangNhap;
        this.email = email;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public boolean isDangDangNhap() {
        return dangDangNhap;
    }

    public void setDangDangNhap(boolean dangDangNhap) {
        this.dangDangNhap = dangDangNhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
