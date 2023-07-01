package com.example.renthouse.Interface;

import com.example.renthouse.OOP.Room;

import java.util.List;

public interface DataFetchCallback {
    void onDataFetched(List<Room> roomList);
}
