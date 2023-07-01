package com.example.renthouse.Admin.OOP;

import com.example.renthouse.OOP.AccountClass;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String key;
    private AccountClass accountClass;
    private int soLuongPhong;

    public NguoiDung() {
    }

    public NguoiDung(String key, AccountClass accountClass, int soLuongPhong) {
        this.key = key;
        this.accountClass = accountClass;
        this.soLuongPhong = soLuongPhong;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AccountClass getAccountClass() {
        return accountClass;
    }

    public void setAccountClass(AccountClass accountClass) {
        this.accountClass = accountClass;
    }

    public int getSoLuongPhong() {
        return soLuongPhong;
    }

    public void setSoLuongPhong(int soLuongPhong) {
        this.soLuongPhong = soLuongPhong;
    }
}
