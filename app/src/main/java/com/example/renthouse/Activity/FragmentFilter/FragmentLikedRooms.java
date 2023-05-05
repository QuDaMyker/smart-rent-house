package com.example.renthouse.Activity.FragmentFilter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;
import com.example.renthouse.Test.Room;
import com.example.renthouse.Test.RoomAdapter;

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
        RoomAdapter roomAdapter = new RoomAdapter(getListRoom());
        recyclerView.setAdapter(roomAdapter);
        return view;
    }

    private List<Room> getListRoom() {
       List<Room> room = new ArrayList<>();
       room.add( new Room("HOMESTAY DOOM MẶT TIỀN HÀNG XANH", "Đường Nguyễn Huy Tự", 12000000, R.drawable.p1));
        room.add( new Room("p2", "q2", 12000000, R.drawable.p1));
        room.add( new Room("p3", "q3", 12000000, R.drawable.p1));
        return room;
    }
}