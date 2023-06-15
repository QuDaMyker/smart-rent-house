package com.example.renthouse.OOP;

import android.icu.text.CaseMap;

public class Posted {
    private String key;
    private String title;
    private int price;
    private LocationTemp location;


    public Posted(String key, String title, int price, LocationTemp location) {
        this.key = key;
        this.title = title;
        this.price = price;
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocationTemp getLocation() {
        return location;
    }

    public void setLocation(LocationTemp location) {
        this.location = location;
    }
}
