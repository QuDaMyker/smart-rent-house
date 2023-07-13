package com.example.renthouse.OOP;

public class Region {
    private String districtNameWithType;
    private String cityNameWithType;
    private int soLuong;

    public Region(String districtNameWithType, String cityNameWithType, int soLuong) {
        this.districtNameWithType = districtNameWithType;
        this.cityNameWithType = cityNameWithType;
        this.soLuong = soLuong;
    }

    public Region() {
    }

    public String getDistrictNameWithType() {
        return districtNameWithType;
    }

    public void setDistrictNameWithType(String districtNameWithType) {
        this.districtNameWithType = districtNameWithType;
    }

    public String getCityNameWithType() {
        return cityNameWithType;
    }

    public void setCityNameWithType(String cityNameWithType) {
        this.cityNameWithType = cityNameWithType;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
