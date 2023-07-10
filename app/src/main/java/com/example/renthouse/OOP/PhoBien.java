package com.example.renthouse.OOP;

import java.io.Serializable;
import java.util.List;

public class PhoBien implements Serializable {
    private List<Room> roomList;
    private String image;
    private String name;

    public PhoBien(String image, List<Room> roomList, String name) {
        this.roomList = roomList;
        this.image = image;
        this.name = name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
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
