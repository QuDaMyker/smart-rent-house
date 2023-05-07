package com.example.renthouse.OOP;

public class Room {
    private int id;
    private String title;
    private String description;
    private String roomType;
    private int capacity;
    private float area;
    private int price;
    private int deposit;
    private int electricityCost;
    private int waterCost;
    private int internetCost;
    private boolean parking;
    private int parkingFee;
    private LocationTemp location;
    private String[] images;
    private String[] videos;
    private String[] utilities;
    private AccountClass createdBy;
    private String phoneNumber;

    public Room(int id, String title, String description, String roomType, int capacity, float area, int price, int deposit, int electricityCost, int waterCost, int internetCost, boolean parking, int parkingFee, LocationTemp location, String[] images, String[] videos, String[] utilities, AccountClass createdBy, String phoneNumber) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.roomType = roomType;
        this.capacity = capacity;
        this.area = area;
        this.price = price;
        this.deposit = deposit;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.internetCost = internetCost;
        this.parking = parking;
        this.parkingFee = parkingFee;
        this.location = location;
        this.images = images;
        this.videos = videos;
        this.utilities = utilities;
        this.createdBy = createdBy;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public String[] getUtilities() {
        return utilities;
    }

    public void setUtilities(String[] utilities) {
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
}
