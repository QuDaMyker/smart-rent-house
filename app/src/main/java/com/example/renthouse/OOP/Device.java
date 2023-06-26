package com.example.renthouse.OOP;

public class Device {
    AccountClass user;
    String token;
    boolean roomNoti;
    boolean chatNoti;
    boolean likeNoti;
    boolean scheduleNoti;

    public Device() {
    }

    public Device(AccountClass user, String token, boolean roomNoti, boolean chatNoti, boolean likeNoti, boolean scheduleNoti) {
        this.user = user;
        this.token = token;
        this.roomNoti = roomNoti;
        this.chatNoti = chatNoti;
        this.likeNoti = likeNoti;
        this.scheduleNoti = scheduleNoti;
    }

    public AccountClass getUser() {
        return user;
    }

    public void setUser(AccountClass user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRoomNoti() {
        return roomNoti;
    }

    public void setRoomNoti(boolean roomNoti) {
        this.roomNoti = roomNoti;
    }

    public boolean isChatNoti() {
        return chatNoti;
    }

    public void setChatNoti(boolean chatNoti) {
        this.chatNoti = chatNoti;
    }

    public boolean isLikeNoti() {
        return likeNoti;
    }

    public void setLikeNoti(boolean likeNoti) {
        this.likeNoti = likeNoti;
    }

    public boolean isScheduleNoti() {
        return scheduleNoti;
    }

    public void setScheduleNoti(boolean scheduleNoti) {
        this.scheduleNoti = scheduleNoti;
    }
}
