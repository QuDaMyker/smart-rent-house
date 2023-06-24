package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Adapter.ResultRoomAdapter;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResult extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recycleViewResultRoom;
    private ResultRoomAdapter resultRoomAdapter;
    public FragmentResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentResult.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentResult newInstance(String param1, String param2) {
        FragmentResult fragment = new FragmentResult();
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
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        recycleViewResultRoom = view.findViewById(R.id.recycleViewResultRoom);
        resultRoomAdapter = new ResultRoomAdapter(getContext());
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleViewResultRoom.setLayoutManager(linearLayoutManager);
        resultRoomAdapter.setData(getListRoom());
        recycleViewResultRoom.setAdapter(resultRoomAdapter);
        Log.d("Dữ liệu", String.valueOf(resultRoomAdapter.getItemCount()));
        return view;
    }
    public ArrayList<Room> getListRoom() {

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room());
        roomList.add(new Room());
        roomList.add(new Room());
        roomList.add(new Room());
        roomList.add(new Room());
        roomList.add(new Room());
        roomList.add(new Room());
        return (ArrayList<Room>) roomList;
    }
}