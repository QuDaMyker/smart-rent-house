package com.example.renthouse.Activity;

public class Room {
    private String name;
    private String address;
    private Integer price;
    private String sourceImage;

    public Room(String name, String address, Integer price, String sourceImage) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.sourceImage = sourceImage;
    }

    public Integer getPrice() {
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

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }
}







