package com.example.renthouse.OOP;

import java.io.Serializable;
import java.util.List;

public class PhoBien implements Serializable {
    private String idphobien;
    private String image;
    private String name;

    public PhoBien(String image, String idphobien, String name) {
        this.idphobien = idphobien;
        this.image = image;
        this.name = name;
    }

    public String getIdphobien() {
        return idphobien;
    }

    public void setIdphobien(String idphobien) {
        this.idphobien = idphobien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
