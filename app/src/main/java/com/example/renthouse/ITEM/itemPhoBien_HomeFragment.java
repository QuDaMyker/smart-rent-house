package com.example.renthouse.ITEM;

public class itemPhoBien_HomeFragment {
    private String image;
    private String location;

    public itemPhoBien_HomeFragment(String image, String location) {
        this.image = image;
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
