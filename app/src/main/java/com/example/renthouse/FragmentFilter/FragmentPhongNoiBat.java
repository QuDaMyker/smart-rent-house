package com.example.renthouse.FragmentFilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Adapter.OutstandingRoomAdapter;
import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Adapter.RoomLatestAdapter;
import com.example.renthouse.Fragment.FragmentFilter;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLikedRooms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPhongNoiBat extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView outstanding_recyclerView;
    private OutstandingRoomAdapter outstandingRoomAdapter;
    private List<Room> outstandingList;
    private List<String> idRoomsLiked;
    private String idAC;
    private PreferenceManager preferenceManager;
    private FirebaseDatabase db;
    private ProgressBar prgLoading;

    public FragmentPhongNoiBat() {


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
        preferenceManager = new PreferenceManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_phongnoibat, container, false);
        // Inflate the layout for this fragment
        outstanding_recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_phongnoibat);

        outstandingList = new ArrayList<>();
        outstandingRoomAdapter = new OutstandingRoomAdapter(getContext(), outstandingList, new ItemClick() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(getContext(), ActivityDetails.class);
                intent.putExtra("selectedRoom", room);
                startActivity(intent);
            }
        });
        outstanding_recyclerView.setAdapter(outstandingRoomAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        outstanding_recyclerView.setLayoutManager(gridLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        Query query = reference.child("Rooms");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> temroom = new ArrayList<>();
                for (DataSnapshot Snapshot : snapshot.getChildren()){
                    Room room = Snapshot.getValue((Room.class));
                    if (room.getStatus().equals("approved")) {
                        temroom.add(room);
                    }
                }
                outstandingList.addAll(temroom);
                outstandingRoomAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;

        //return inflater.inflate(R.layout.fragment_liked_empty, container, false);
    }
}
