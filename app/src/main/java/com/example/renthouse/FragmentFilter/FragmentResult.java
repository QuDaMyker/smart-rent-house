package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renthouse.Adapter.ResultRoomAdapter;
import com.example.renthouse.OOP.ObjectSearch;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private RecyclerView recyclerView;
    private TextView textViewAmountResult;
    private ResultRoomAdapter resultRoomAdapter;
    private ObjectSearch objectSearch;
    private ArrayList<Room> list = new ArrayList<>();
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
        textViewAmountResult = view.findViewById(R.id.textViewAmountResult);
        recyclerView = view.findViewById(R.id.recycleViewResultRoom);
        resultRoomAdapter = new ResultRoomAdapter(list, getContext());
        textViewAmountResult.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(resultRoomAdapter);



        loadData();



        return view;
    }
    private void shimmerRun() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                resultRoomAdapter.isShimmerEnabled = false;
                resultRoomAdapter.notifyDataSetChanged();
                int soLuong = resultRoomAdapter.getItemCount();
                textViewAmountResult.setText(String.valueOf(soLuong) + " KẾT QUẢ");
                textViewAmountResult.setVisibility(View.VISIBLE);
            }
        }, 4000);
    }
    public void loadData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Rooms");

        // Lắng nghe sự thay đổi dữ liệu trên đường dẫn "Rooms"
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, bạn có thể truy cập và xử lý dữ liệu tại đây
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    // Kiểm tra điều kiện nè
                    if (objectSearch.getPath().equals(room.getLocation().getWard().getPath())){
                        if (isPriceValid(room) || isContainAmount(room) || isContainsUtilities(room)
                                || isContainTypeRoom(room)) {
                            list.add(room);
                        }
                    }
                    try {
                        sortItem(list);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                shimmerRun();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Lỗi nè", "lỗi");
            }
        });
    }
    private boolean isPriceValid(Room room) {
        if (objectSearch.getPrice() == null || objectSearch.getPrice().isEmpty()) {
            return true;
        } else {
            if (room.getPrice() <= objectSearch.getPrice().get(1) && room.getPrice() >= objectSearch.getPrice().get(0)) {
                return true;
            }
            return false;
        }
    }
    private boolean isContainsUtilities(Room room) {
        if (objectSearch.getUtilities() == null || objectSearch.getUtilities().isEmpty()) {
            return true;
        } else {
            // Lọc như này để chọn được nhiều option hơn
            for (String utility : objectSearch.getUtilities()) {
                if (room.getUtilities().contains(utility)) {
                    return true;
                }
            }
            return false;
        }
    }
    private boolean isContainTypeRoom(Room room) {
        if (objectSearch.getType() == null || objectSearch.getType().isEmpty()) {
            return true;
        } else {
            if(objectSearch.getType().equals(room.getRoomType())) {
                return true;
            }
            return false;
        }
    }
    private boolean isContainAmount(Room room) {
        if(objectSearch.getAmount() == -1) {
            return true;
        } else {
            if (objectSearch.getGender().equals(room.getGender()) && objectSearch.getAmount() <= room.getCapacity()){
                return true;
            }
            return false;
        }
    }
    private void sortItem(List<Room> roomList) throws ParseException {
        switch (objectSearch.getSort()) {
            case "Liên quan nhất":
                break;
            case "Mới nhất":
                sortDate(roomList);
                break;
            case "Giá thấp đến cao":
                sortMintoMax(roomList);
                break;
            case "Giá cao xuống thấp":
                sortMaxtoMin(roomList);
                break;
            default:
                break;
        }
    }

    private void sortMaxtoMin(List<Room> roomList) {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = i; j < roomList.size(); j++) {
                if (roomList.get(i).getPrice() < roomList.get(i).getPrice()) {
                    Collections.swap(roomList, i, j);
                }
            }
        }
    }

    private void sortMintoMax(List<Room> roomList) {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = i; j < roomList.size(); j++) {
                if (roomList.get(i).getPrice() > roomList.get(i).getPrice()) {
                    Collections.swap(roomList, i, j);
                }
            }
        }
    }
    private void sortDate(List<Room> roomList) throws ParseException {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = i; j < roomList.size(); j++) {
                String dateTime1 = roomList.get(i).getDateTime();
                String dateTime2 = roomList.get(i).getDateTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                Date dateObj1 = sdf.parse(dateTime1);
                Date dateObj2 = sdf.parse(dateTime2);

                if (dateObj1.before(dateObj2)) {
                    Collections.swap(roomList, i, j);
                }
            }
        }
    }

    public void setObjectSearch(ObjectSearch objectSearch) {
        this.objectSearch = objectSearch;
    }
}