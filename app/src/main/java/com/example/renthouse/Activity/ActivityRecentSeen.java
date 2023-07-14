package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private ProgressDialog mProgressDialog;
    private TextView textViewEmptyRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_seen);
        textViewEmptyRoom = findViewById(R.id.textViewEmptyRoom);
        roomLatestList = new ArrayList<>();

//        mProgressDialog = new ProgressDialog(getApplicationContext());
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setMessage("Loading...");




        //mCheckInforInServer();
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
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            if (mProgressDialog == null) {
//                mProgressDialog = new ProgressDialog(ActivityRecentSeen.this);
//                mProgressDialog.setMessage(getString(R.string.loading));
//                mProgressDialog.setIndeterminate(true);
//                mProgressDialog.show();
//            }
//            else {
//                if(roomLatestList.isEmpty()) {
//                    textViewEmptyRoom.setVisibility(View.VISIBLE);
//                }
//                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(staggeredGridLayoutManager);
//                roomLatestAdapter = new RoomLatestAdapter(this);
//                roomLatestAdapter.setDuLieu(roomLatestList);
//                recyclerView.setAdapter(roomLatestAdapter);
//            }
//        }
//    }
    public void setDuLieu(){
        //mProgressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accRef = database.getReference("Accounts");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //DatabaseReference roomRef = database.getReference("Rooms");
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
                                mProgressDialog.dismiss();
                                textViewEmptyRoom.setVisibility(View.VISIBLE);
                                return;
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

    private void addLatestRoom(List<String> keyRoomList) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Rooms");
        // Lắng nghe sự thay đổi dữ liệu trên đường dẫn "Rooms"
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