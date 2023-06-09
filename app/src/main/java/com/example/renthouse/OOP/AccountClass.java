package com.example.renthouse.OOP;

import java.io.Serializable;

public class AccountClass implements Serializable {
    private String fullname;
    private String email;
    private String phoneNumber;
    private String password;
    private String image;
    private String ngayTaoTaiKhoan;

    public AccountClass() {
    }

    public AccountClass(String fullname, String email, String phoneNumber, String password, String image, String ngayTaoTaiKhoan) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNgayTaoTaiKhoan() {
        return ngayTaoTaiKhoan;
    }

    public void setNgayTaoTaiKhoan(String ngayTaoTaiKhoan) {
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }
}
