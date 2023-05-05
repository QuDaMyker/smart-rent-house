package com.example.renthouse.Test;

public class Room {
    private String name;
    private String address;
    private int price;
    private int sourceImage;

    public Room(String name, String address, int price, int sourceImage) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.sourceImage = sourceImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(int sourceImage) {
        this.sourceImage = sourceImage;
    }
}







