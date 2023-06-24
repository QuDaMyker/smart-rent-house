package com.example.renthouse.OOP;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ObjectSearch {
    private ArrayList<Integer> utilities; // Utilities dùng để lấy của firebase
    private ArrayList<Long> price; // Giá dùng để lấy của firebase;
    private int type; // Loại dùng để lấy của firebase
    private int amount; // Số lượng
    private int gender; // Giới tính lấy firebase
    private int sort; // Sắp xếp lấy firebase

    public ObjectSearch() {
        this.price = new ArrayList<>();
        this.utilities = new ArrayList<>();
        this.type = -1;
        this.amount = -1;
        this.gender = -1;
        this.sort = -1;
    }

    public ObjectSearch(ArrayList<Long> price, ArrayList<Integer> utilities, int type, int amount, int gender, int sort) {
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

    public ArrayList<Integer> getUtilities() {
        return utilities;
    }

    public void setUtilities(ArrayList<Integer> utilities) {
        this.utilities = utilities;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void clearData() {
        this.price = new ArrayList<>();
        this.utilities = new ArrayList<>();
        this.type = -1;
        this.amount = -1;
        this.gender = -1;
        this.sort = -1;
    }
}
