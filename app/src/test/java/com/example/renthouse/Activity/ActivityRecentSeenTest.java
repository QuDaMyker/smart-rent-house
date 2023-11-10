package com.example.renthouse.Activity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Bundle;

import com.example.renthouse.OOP.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecentSeenTest extends TestCase {
    @Mock
    private DatabaseReference databaseReference;
    @Mock
    private DataSnapshot dataSnapshot;
    @Mock
    private ValueEventListener valueEventListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    public ActivityRecentSeenTest() {

    }

    @Test
    public void onCreate() {
        ActivityRecentSeen activityRecentSeen = mock(ActivityRecentSeen.class);
        doCallRealMethod().when(activityRecentSeen).onCreate(null);
        doNothing().when(activityRecentSeen).setDuLieu();
        Bundle savedInstanceState = mock(Bundle.class);
        activityRecentSeen.onCreate(savedInstanceState);
        verify(activityRecentSeen, times(1)).setDuLieu();
    }

    @Test
    public void setDuLieu() {

    }

    @Test
    public void addLatestRoom() {
        // Chuẩn bị một danh sách keyRoomList
        List<String> keyRoomList = new ArrayList<>();
        keyRoomList.add("key1");
        keyRoomList.add("key2");

        // Tạo đối tượng của class chứa phương thức addLatestRoom
        ActivityRecentSeen activityRecentSeen = new ActivityRecentSeen();
        when(FirebaseDatabase.getInstance().getReference("Rooms")).thenReturn(databaseReference);

        // Chuẩn bị dữ liệu giả cho onDataChange
        DataSnapshot room1DataSnapshot = mock(DataSnapshot.class);
        when(room1DataSnapshot.getValue(Room.class)).thenReturn(new Room());
        DataSnapshot room2DataSnapshot = mock(DataSnapshot.class);
        when(room2DataSnapshot.getValue(Room.class)).thenReturn(new Room());

        List<DataSnapshot> roomsDataSnapshots = new ArrayList<>();
        roomsDataSnapshots.add(room1DataSnapshot);
        roomsDataSnapshots.add(room2DataSnapshot);

        when(dataSnapshot.getChildren()).thenReturn(roomsDataSnapshots);

        // Gọi hàm addLatestRoom
        activityRecentSeen.addLatestRoom(keyRoomList);

        // Bắt đầu kiểm tra
        verify(databaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Gọi onDataChange
        valueEventListener.onDataChange(dataSnapshot);

    }
}