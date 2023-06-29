package com.example.renthouse.OOP;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ObjectSearch {
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private ArrayList<String> utilities; // Utilities dùng để lấy của firebase
    private ArrayList<Long> price; // Giá dùng để lấy của firebase;
    private String type; // Loại dùng để lấy của firebase
    private int amount; // Số lượng
    private String gender; // Giới tính lấy firebase
    private String sort; // Sắp xếp lấy firebase

    public ObjectSearch() {
        this.price = new ArrayList<>();
        this.utilities = new ArrayList<>();
        this.type = "";
        this.amount = -1;
        this.gender = "";
        this.sort = "";
        path = "";
    }

    public ObjectSearch(ArrayList<Long> price, ArrayList<String> utilities, String type, int amount, String gender, String sort) {
        this.price = price;
        this.utilities = utilities;
        this.type = type;
        this.amount = amount;
        this.gender = gender;
        this.sort = sort;
    }

    public ArrayList<Long> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Long> price) {
        this.price = price;
    }

    public ArrayList<String> getUtilities() {
        return utilities;
    }

    public void setUtilities(ArrayList<String> utilities) {
        this.utilities = utilities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void clearData() {
        this.price = new ArrayList<>();
        this.utilities = new ArrayList<>();
        this.type = "";
        this.amount = -1;
        this.gender = "";
        this.sort = "";
    }
}
