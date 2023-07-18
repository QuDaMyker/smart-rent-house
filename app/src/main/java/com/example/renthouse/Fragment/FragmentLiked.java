package com.example.renthouse.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentLikedBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentLiked extends Fragment {
    FirebaseDatabase db;
    boolean isEmpty = true;
    private ImageView ivFaceNothing;
    private TextView tvNothing;
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> rooms;
    private List<String> idRoomsLiked;
    private String idAC;
    private PreferenceManager preferenceManager;

    private FragmentLikedBinding binding;
    private DialogListener dialogListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;

        } catch ( ClassCastException e)
        {
            throw new ClassCastException(context.toString() +" must implement DialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLikedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        ivFaceNothing = view.findViewById(R.id.face_nothing);
        tvNothing = view.findViewById(R.id.textNothing);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_LikedRooms);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(grid);
        idRoomsLiked = new ArrayList<String>();
        rooms = new ArrayList<>();
        roomAdapter = new RoomAdapter(this.getContext(), rooms);
        recyclerView.setAdapter(roomAdapter);
        preferenceManager = new PreferenceManager(getContext());
        return view;
    }
   @Override
    public void onResume() {
        super.onResume();
        checkListLikedRooms();
    }

    private void checkListLikedRooms() {
        db = FirebaseDatabase.getInstance();
        DatabaseReference refLiked = db.getReference("LikedRooms");
        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(preferenceManager.getString(Constants.KEY_USER_KEY))) {
                    isEmpty = false;
                    recyclerView.setVisibility(View.VISIBLE);
                    ivFaceNothing.setVisibility(View.GONE);
                    tvNothing.setVisibility(View.GONE);
                    getListLikedRoomFromFB();


                } else {
                    isEmpty = true;
                    recyclerView.setVisibility(View.GONE);
                    ivFaceNothing.setVisibility(View.VISIBLE);
                    tvNothing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getListLikedRoomFromFB() {
        dialogListener.showDialog();
        rooms.clear();
        idRoomsLiked.clear();
        db = FirebaseDatabase.getInstance();

        DatabaseReference refLiked = db.getReference("LikedRooms");

        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userIDSnapshot : snapshot.getChildren()) {
                    if (userIDSnapshot.getKey().equals(preferenceManager.getString(Constants.KEY_USER_KEY))) {
                        for (DataSnapshot roomSnapshot : userIDSnapshot.getChildren()) {
                            String idRoom = roomSnapshot.getKey();
                            idRoomsLiked.add(idRoom);
                        }
                        DatabaseReference refRoom = db.getReference("Rooms");
                        refRoom.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (String roomId : idRoomsLiked) {
                                    if (snapshot.hasChild(roomId)) {
                                        DataSnapshot roomSnapShot = snapshot.child(roomId);
                                        Room room = roomSnapShot.getValue(Room.class);
                                        if (!room.getStatus().equals(Constants.STATUS_DELETED)) {
                                            rooms.add(room);
                                        }
                                    }
                                }
                                roomAdapter.setLimit(rooms.size());
                                roomAdapter.notifyDataSetChanged();
                                dialogListener.dismissDialog();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogListener.dismissDialog();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (roomAdapter != null) {
            roomAdapter.release();
        }
    }
}