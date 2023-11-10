package com.example.renthouse.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.renthouse.Adapter.RoomLatestAdapter;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecentSeen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomLatestAdapter roomLatestAdapter;
    private List<Room> roomLatestList;
    private ImageButton btnBack;
    private TextView textViewEmptyRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_seen);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        textViewEmptyRoom = findViewById(R.id.textViewEmptyRoom);
        roomLatestList = new ArrayList<>();
        btnBack = findViewById(R.id.btn_Back);
        recyclerView = findViewById(R.id.recycleViewRecentSeen);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        roomLatestAdapter = new RoomLatestAdapter(this);
        roomLatestAdapter.setDuLieu(roomLatestList);
        recyclerView.setAdapter(roomLatestAdapter);

        setDuLieu();
    }
    public void setDuLieu(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accRef = database.getReference("Accounts");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Query query = accRef.orderByChild("email").equalTo(user.getEmail());
        List<String> keyRoomList = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Lấy được id của user
                    String idUser = dataSnapshot.getKey();

                    // Tạo node SeenRoom và add node idUser vô
                    DatabaseReference refSeen = database.getReference("SeenRoom").child(idUser);
                    refSeen.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() == 0) {
                                textViewEmptyRoom.setVisibility(View.VISIBLE);
                            } else {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    keyRoomList.add(snapshot1.getValue(String.class));
                                }
                                addLatestRoom(keyRoomList);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void addLatestRoom(List<String> keyRoomList) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Rooms");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, bạn có thể truy cập và xử lý dữ liệu tại đây
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    // Kiểm tra điều kiện nè
                    if (keyRoomList.contains(room.getId())) {
                        roomLatestList.add(room);
                    }
                }
                roomLatestAdapter.notifyDataSetChanged();
                //mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Lỗi nè", "lỗi");
            }
        });
    }

}