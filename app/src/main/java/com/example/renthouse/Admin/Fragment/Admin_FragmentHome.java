package com.example.renthouse.Admin.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Activity.ActivityLogIn;
import com.example.renthouse.Adapter.HotRegionAdapter;
import com.example.renthouse.Admin.Activity.Admin_ActivityBaoCaoNguoiDung;
import com.example.renthouse.Admin.Activity.Admin_ActivityStatistics;
import com.example.renthouse.OOP.Region;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminHomeBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.renthouse.Activity.ActivitySplash;
import com.example.renthouse.Admin.Activity.Admin_ActivityBaoCaoNguoiDung;
import com.example.renthouse.Admin.Activity.Admin_ActivityScheduledNotification;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminHomeBinding;
import com.example.renthouse.utilities.PreferenceManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

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
    private List<Region> regionListTop3;
    private HotRegionAdapter adapter;
    private ProgressDialog mProgressDialog;
    private List<Integer> accessAmountList;
    public Admin_FragmentHome() {

    }

    private PreferenceManager preferenceManager;

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
        accessAmountList = new ArrayList<>();
        regionListTop3 = new ArrayList<>();


        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new HotRegionAdapter(regionListTop3);
        binding.recycleView.hasFixedSize();
        binding.recycleView.setAdapter(adapter);
        init();
        setListener();


        addDateInSameWeek();
        setListener();
        loadData();
        getRegion();
        //setUpBarChart();
        return view;
    }


    private void setUpBarChart() {
        BarDataSet barDataSet1 = new BarDataSet(barEntriesAccess(), "Số lượt truy cập");
        barDataSet1.setColor(Color.parseColor("#FFD601"));

        BarData data = new BarData(barDataSet1);
        binding.barChart.setData(data);
        XAxis xAxis = binding.barChart.getXAxis();
        data.setBarWidth(0.15f);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++){
            days[i] = dateInWeek.get(i).substring(0, 5);
            days[i].replace("-", "/");
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);

        binding.barChart.setDragEnabled(true);
        xAxis.setAxisMinimum(-0.2f);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMaximum(days.length - 0.2f);

        float barSpace = 0.2f;
        float groupSpace = 0.25f;
        binding.barChart.groupBars(0, groupSpace, barSpace);


        binding.barChart.setDrawGridBackground(false);
        binding.barChart.getAxisRight().setTextColor(getResources().getColor(R.color.white));
        binding.barChart.getXAxis().removeAllLimitLines();
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.invalidate();
    }

    private List<BarEntry> barEntriesAccess() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        final boolean[] flag = {false};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Access");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String date = snapshot.getKey();
                    if (dateInWeek.contains(date)) {
                        Integer amount = snapshot1.getValue(Integer.class);
                        int position = dateInWeek.indexOf(date);
                        accessAmountList.set(position, amount);
                    }
                }
                for (int i = 0; i < dateInWeek.size(); i++) {
                    barEntries.add(new BarEntry(i + 1, accessAmountList.get(i)));
                }
                flag[0] = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        while (!flag[0]){

        }
        return barEntries;
    private void init() {
        preferenceManager = new PreferenceManager(getContext());
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
        binding.imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(requireContext(), ActivityLogIn.class));
            }
        });

        binding.linearDatLichThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Admin_ActivityScheduledNotification.class);
                startActivity(intent);
            }
        });

        binding.imageLogout.setOnClickListener(v-> logOut());

    }

    private void logOut() {
        preferenceManager.clear();
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        googleSignInClient.signOut();
        getActivity().finish();
        startActivity(new Intent(requireContext(), ActivitySplash.class));
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
        for (int i = 0; i < 3;i++) {
            Log.d("Top", String.valueOf(regionList.get(i).getDistrictNameWithType()));
            regionListTop3.add(regionList.get(i));
        }
        adapter.notifyDataSetChanged();
    }
    private void addDateInSameWeek() {
        LocalDate ngayHienTai = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayHienTai = LocalDate.now();
        }

        // Tạo định dạng cho ngày
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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