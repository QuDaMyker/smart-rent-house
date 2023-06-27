package com.example.renthouse.OOP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private String id;
    private String title;
    private String description;
    private String roomType;
    private int capacity;
    private String gender;
    private float area;
    private int price;
    private int deposit;
    private int electricityCost;
    private int waterCost;
    private int internetCost;
    private boolean parking;
    private int parkingFee;
    private LocationTemp location;
    private List<String> images;
    private List<String> utilities;
    private AccountClass createdBy;
    private String phoneNumber;
    private String dateTime;
    private boolean isRented;

    public Room() {

    }

    public Room(String id, String title, String description, String roomType, int capacity, String gender, float area, int price, int deposit, int electricityCost, int waterCost, int internetCost, boolean parking, int parkingFee, LocationTemp location, List<String> utilities, AccountClass createdBy, String phoneNumber, String dateTime, boolean isRented) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.roomType = roomType;
        this.capacity = capacity;
        this.gender = gender;
        this.area = area;
        this.price = price;
        this.deposit = deposit;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.internetCost = internetCost;
        this.parking = parking;
        this.parkingFee = parkingFee;
        this.location = location;
        this.utilities = utilities;
        this.createdBy = createdBy;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.isRented = isRented;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(int electricityCost) {
        this.electricityCost = electricityCost;
    }

    public int getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(int waterCost) {
        this.waterCost = waterCost;
    }

    public int getInternetCost() {
        return internetCost;
    }

    public void setInternetCost(int internetCost) {
        this.internetCost = internetCost;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public int getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(int parkingFee) {
        this.parkingFee = parkingFee;
    }

    public LocationTemp getLocation() {
        return location;
    }

    public void setLocation(LocationTemp location) {
        this.location = location;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<String> utilities) {
        this.utilities = utilities;
    }

    public AccountClass getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AccountClass createdBy) {
        this.createdBy = createdBy;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}
