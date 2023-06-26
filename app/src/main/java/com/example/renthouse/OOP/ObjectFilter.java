package com.example.renthouse.OOP;

import java.util.ArrayList;

public class ObjectFilter {
    public ObjectFilter(String priceString, ArrayList<String> utilitesString, String typeRoom, String amountAndGender, String sortString) {
        this.priceString = priceString;
        this.utilitesString = utilitesString;
        this.typeRoom = typeRoom;
        this.amountAndGender = amountAndGender;
        this.sortString = sortString;
    }

    public ObjectFilter() {
        utilitesString = new ArrayList<String>();
        priceString = "";
        typeRoom = "";
        amountAndGender = "";
        sortString = "";
    }

    private String priceString;
    private ArrayList<String> utilitesString;
    private String typeRoom;
    private String amountAndGender;
    private String sortString;

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public ArrayList<String> getUtilitesString() {
        return utilitesString;
    }

    public void setUtilitesString(ArrayList<String> utilitesString) {
        this.utilitesString = utilitesString;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public String getAmountAndGender() {
        return amountAndGender;
    }

    public void setAmountAndGender(String amountAndGender) {
        this.amountAndGender = amountAndGender;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public void clearData() {
        utilitesString = new ArrayList<String>();
        priceString = "";
        typeRoom = "";
        amountAndGender = "";
        sortString = "";
    }
}
