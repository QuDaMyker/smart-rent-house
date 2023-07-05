package com.example.renthouse.OOP;

import java.io.Serializable;

public class PhoBien implements Serializable {
    private String image;
    private String title;

    public PhoBien(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
