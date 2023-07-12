package com.example.renthouse.Admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Admin.Activity.Admin_ActivityBaoCaoNguoiDung;
import com.example.renthouse.Admin.Activity.Admin_ActivityStatistics;
import com.example.renthouse.OOP.Region;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.databinding.FragmentAdminHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;


public class Admin_FragmentHome extends Fragment {
    private FragmentAdminHomeBinding binding;
    private Set<String> emailUserSet;
    private int amountOfRentRoom = 0;
    private List<String> dateInWeek;
    private List<Region> regionList;
    private List<Integer> amountRoom;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(getLayoutInflater());
        //View view = inflater.inflate(R.layout.fragment_admin__home, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        emailUserSet = new HashSet<>();
        dateInWeek = new ArrayList<>();
        regionList = new ArrayList<>();
        amountRoom = new ArrayList<>();
        addDateInSameWeek();
        setListener();
        loadData();
        return view;
    }
    private void setListener() {
        binding.linearBaoCaoNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_ActivityBaoCaoNguoiDung.class);
                startActivity(intent);
            }
        });
        binding.linearThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_ActivityStatistics.class);
                startActivity(intent);
            }
        });
    }
    private void loadData() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Accounts");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Tính tổng số người dùng
                binding.tvTongSoNguoiDung.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Rooms");
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Tính số phòng trọ
                binding.tvTongSoPhongTro.setText(String.valueOf(snapshot.getChildrenCount()));

                // Tính số chủ nhà
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Room room = snapshot1.getValue(Room.class);
                    emailUserSet.add(room.getCreatedBy().getEmail());
                    if (room.isRented()) {
                        amountOfRentRoom++;
                    }
                }
                binding.tvSoChuNha.setText(String.valueOf(emailUserSet.size()));

                // Tính số phòng được thuê
                binding.tvSoPhongDuocThue.setText(String.valueOf(amountOfRentRoom));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getRegion() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("PopularRegion");
        reference1.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Region region = dataSnapshot.getValue(Region.class);
                    regionList.add(region);
                }
                filterTop();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
    }
    private void filterTop() {
        for (int i = 0; i < regionList.size() - 1;i++) {
            for (int j = i + 1; j< regionList.size(); j++) {
                if (regionList.get(i).getSoLuong() < regionList.get(j).getSoLuong()) {
                    Collections.swap(regionList, i, j);
                }
            }
        }
    }
    private void addDateInSameWeek() {
        LocalDate ngayHienTai = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayHienTai = LocalDate.now();
        }

        // Tạo định dạng cho ngày
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        // Tìm ngày đầu tiên trong tuần
        LocalDate ngayDauTuan = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayDauTuan = ngayHienTai.with(DayOfWeek.MONDAY);
        }

        // Liệt kê các ngày cùng một tuần và định dạng thành chuỗi
        for (int i = 0; i < 7; i++) {
            LocalDate ngay = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ngay = ngayDauTuan.plusDays(i);
            }
            String ngayFormatted = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ngayFormatted = ngay.format(formatter);
            }
            dateInWeek.add(ngayFormatted);
        }
    }
}