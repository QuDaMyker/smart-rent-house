package com.example.renthouse.FragmentFilter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Activity.ActivityMain;
import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLikedRooms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLikedRooms extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> rooms;

    FirebaseDatabase db;
    DatabaseReference ref ;


    public FragmentLikedRooms() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLikedRooms.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLikedRooms newInstance(String param1, String param2) {
        FragmentLikedRooms fragment = new FragmentLikedRooms();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_rooms, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_LikedRooms);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(grid);

        rooms = new ArrayList<>();
        roomAdapter = new RoomAdapter(this.getContext(), rooms);

        recyclerView.setAdapter(roomAdapter);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Rooms");

        getListRoomFromFB();

        return view;
    }

        private void getListRoomFromFB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Rooms");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Room r = dataSnapshot.getValue(Room.class);
                    rooms.add(r);
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (roomAdapter != null)
        {
            roomAdapter.release();
        }
    }
}