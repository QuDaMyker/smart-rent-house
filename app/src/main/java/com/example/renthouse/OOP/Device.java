package com.example.renthouse.OOP;

public class Device {
    AccountClass user;
    String token;

    public Device() {
    }

    public Device(AccountClass user, String token) {
        this.user = user;
        this.token = token;
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
}
