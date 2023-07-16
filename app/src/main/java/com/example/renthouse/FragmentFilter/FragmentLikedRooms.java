package com.example.renthouse.FragmentFilter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> idRoomsLiked;
    private String idAC;
    private PreferenceManager preferenceManager;
    private FirebaseDatabase db;

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

        preferenceManager = new PreferenceManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_rooms, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_LikedRooms);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(grid);
        idRoomsLiked = new ArrayList<String>();
        rooms = new ArrayList<>();
        roomAdapter = new RoomAdapter(this.getContext(), rooms);
        recyclerView.setAdapter(roomAdapter);


        return view;
    }

    private void getListLikedRoomFromFB() {
        dialogListener.showDialog();
        rooms.clear();
        idRoomsLiked.clear();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();

        String emailCur = currentUser.getEmail();

        String email = preferenceManager.getString(Constants.KEY_USER_KEY);

        DatabaseReference refAC = db.getReference("Accounts");
        //lấy id ng dùng hiện tại
        refAC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailAC = null;
                for (DataSnapshot snapshotAc : snapshot.getChildren()) {
                    emailAC = snapshotAc.child("email").getValue(String.class);
                    if (emailCur.equals(emailAC)) {
                        idAC = snapshotAc.getKey();
                        //lấy danh sách id phòng user đã thích
                        DatabaseReference refLiked = db.getReference("LikedRooms");

                        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot userIDSnapshot : snapshot.getChildren()) {
                                    if (userIDSnapshot.getKey().equals(idAC)) {
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
                                                        rooms.add(room);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                dialogListener.dismissDialog();

            }
        });
    }

    private void getListRoomFromFB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refLiked = db.getReference("Rooms");
        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room temp = roomSnapshot.getValue(Room.class);
                    rooms.add(temp);
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }//hàm này để test thôi

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (roomAdapter != null) {
            roomAdapter.release();
        }
    }
    public void onResume() {
        super.onResume();
        getListLikedRoomFromFB();
    }
}



