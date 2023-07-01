package com.example.renthouse.OOP;

import java.io.Serializable;

public class AccountClass implements Serializable {
    private String fullname;
    private String email;
    private String phoneNumber;
    private String password;
    private String image;
    private String ngayTaoTaiKhoan;
    private Boolean isBlocked;
    private String thoiGianKhoa;

    public AccountClass (){};

    public AccountClass(String fullname, String email, String phoneNumber, String password, String image, String ngayTaoTaiKhoan, Boolean isBlocked, String thoiGianKhoa) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
        this.isBlocked = isBlocked;
        this.thoiGianKhoa = thoiGianKhoa;
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

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getThoiGianKhoa() {
        return thoiGianKhoa;
    }

    public void setThoiGianKhoa(String thoiGianKhoa) {
        this.thoiGianKhoa = thoiGianKhoa;
    }
}
