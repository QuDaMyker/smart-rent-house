package com.example.renthouse.FragmentFilter;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.ResultRoomAdapter;
import com.example.renthouse.OOP.ObjectSearch;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
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
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    protected String mParam1;
    protected String mParam2;
    protected RecyclerView recyclerView;
    protected TextView textViewAmountResult;
    protected ResultRoomAdapter resultRoomAdapter;
    protected ObjectSearch objectSearch;
    protected ArrayList<Room> list = new ArrayList<>();
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
    public void shimmerRun() {
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
        list.clear();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Rooms");

        // Lắng nghe sự thay đổi dữ liệu trên đường dẫn "Rooms"
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, bạn có thể truy cập và xử lý dữ liệu tại đây
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);

                    // Kiểm tra điều kiện nè
                    if (isContainsAddress(room)){
                        if (isPriceValid(room) && isContainAmount(room) && isContainsUtilities(room)
                                && isContainTypeRoom(room)) {
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
    public boolean isContainsAddress(Room room) {
        if (room.getLocation().getWard().getPath().equals(objectSearch.getPath())){
            return true;
        }
        String str1 = removeDiacritics(room.getLocation().getDistrict().getPath_with_type());
        String str2 = removeDiacritics(objectSearch.getPath());

        // Chuyển đổi chuỗi str1 và str2 thành chữ thường (lowercase) và xóa dấu
        String str1Modified = str1.toLowerCase();
        String str2Modified = str2.toLowerCase();

        // Kiểm tra xem chuỗi str1Modified có chứa chuỗi str2Modified hay không
        boolean contains = str1Modified.contains(str2Modified);
        return contains;
    }
    public String removeDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{M}", "");
        return str;
    }
    public boolean isPriceValid(Room room) {
        if (objectSearch.getPrice() == null || objectSearch.getPrice().isEmpty()) {
            return true;
        } else {
            if (room.getPrice() <= objectSearch.getPrice().get(1) && room.getPrice() >= objectSearch.getPrice().get(0)) {
                Log.d("True", "True hả");
                return true;
            }
            return false;
        }
    }
    public boolean isContainsUtilities(Room room) {
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
    public boolean isContainTypeRoom(Room room) {
        if (objectSearch.getType() == null || objectSearch.getType().isEmpty()) {
            return true;
        } else {
            if(objectSearch.getType().equals(room.getRoomType())) {
                return true;
            }
            return false;
        }
    }
    public boolean isContainAmount(Room room) {
        if(objectSearch.getAmount() == -1) {
            return true;
        } else {
            if ((objectSearch.getGender().equals(room.getGender()) || room.getGender() == "Tất cả")
                && objectSearch.getAmount() <= room.getCapacity()){
                return true;
            }
            return false;
        }
    }
    public void sortItem(List<Room> roomList) throws ParseException {
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

    public void sortMaxtoMin(List<Room> roomList) {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = i; j < roomList.size(); j++) {
                if (roomList.get(i).getPrice() < roomList.get(i).getPrice()) {
                    Collections.swap(roomList, i, j);
                }
            }
        }
    }

    public void sortMintoMax(List<Room> roomList) {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = i; j < roomList.size(); j++) {
                if (roomList.get(i).getPrice() > roomList.get(i).getPrice()) {
                    Collections.swap(roomList, i, j);
                }
            }
        }
    }
    public void sortDate(List<Room> roomList) throws ParseException {
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