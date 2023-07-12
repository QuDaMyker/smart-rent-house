package com.example.renthouse.Admin.Fragment_PhongTro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinPhong;
import com.example.renthouse.Admin.Adapter.PhongTroAdapter;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminPhongTroDanhSachBinding;
import com.example.renthouse.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdminPhongTro_FragmentDanhSach extends Fragment {
    FragmentAdminPhongTroDanhSachBinding binding;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Room> phongtrolist;
    private List<Room> phongTroSearchList;
    private PhongTroAdapter phongTroAdapter;
    private boolean giaTang = false;
    private boolean ngayDangTang = false;
    private int tinhTrang = 0;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminPhongTroDanhSachBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.search_view);

        phongtrolist = new ArrayList<>();
        phongTroSearchList = new ArrayList<>();

        //replaceFragment(new AdminPhongTro_FragmentEmpty());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        phongTroAdapter = new PhongTroAdapter(getContext(), phongTroSearchList, new ItemClick() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(getActivity(), Admin_ActivityThongTinPhong.class);
                intent.putExtra("selectedRoom", room);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(phongTroAdapter);

        binding.filterGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giaTang = !giaTang;
                ngayDangTang = false;
                binding.filterGia.setBackgroundResource(R.drawable.bg_radius_solid_primary_60);
                binding.filterNgayDang.setBackgroundResource(R.drawable.bg_radius_primary_40);
                binding.filterTinhTrang.setBackgroundResource(R.drawable.bg_radius_primary_40);
                if (giaTang) {
                    binding.imageGia.setImageResource(R.drawable.ic_arrow_downward);
                    Collections.sort(phongTroSearchList, (obj1, obj2) -> obj2.getPrice() - obj1.getPrice());
                    phongTroAdapter.notifyDataSetChanged();
                } else {
                    binding.imageGia.setImageResource(R.drawable.ic_arrow_upward);
                    Collections.sort(phongTroSearchList, (obj1, obj2) -> obj1.getPrice() - obj2.getPrice());
                    phongTroAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.filterNgayDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngayDangTang = !ngayDangTang;
                giaTang = false;
                binding.filterNgayDang.setBackgroundResource(R.drawable.bg_radius_solid_primary_60);
                binding.filterGia.setBackgroundResource(R.drawable.bg_radius_primary_40);
                binding.filterTinhTrang.setBackgroundResource(R.drawable.bg_radius_primary_40);
                if (ngayDangTang){
                    binding.imageNgayDang.setImageResource(R.drawable.ic_arrow_downward);
                    Collections.sort(phongTroSearchList, new Comparator<Room>() {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");                        @Override
                        public int compare(Room obj1, Room obj2) {
                            try {
                                Date date1 = dateFormat.parse(obj1.getDateTime());
                                Date date2 = dateFormat.parse(obj2.getDateTime());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                    phongTroAdapter.notifyDataSetChanged();
                }
                else {
                    binding.imageNgayDang.setImageResource(R.drawable.ic_arrow_upward);
                    Collections.sort(phongTroSearchList, new Comparator<Room>() {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");                        @Override
                        public int compare(Room obj1, Room obj2) {
                            try {
                                Date date1 = dateFormat.parse(obj1.getDateTime());
                                Date date2 = dateFormat.parse(obj2.getDateTime());
                                return date2.compareTo(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                    phongTroAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.filterTinhTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 0: Tất cả
                // 1: Còn phòng
                // 2: Đã thuê
                tinhTrang = (++tinhTrang) % 3;
                binding.filterTinhTrang.setBackgroundResource(R.drawable.bg_radius_solid_primary_60);
                binding.filterNgayDang.setBackgroundResource(R.drawable.bg_radius_primary_40);
                binding.filterGia.setBackgroundResource(R.drawable.bg_radius_primary_40);
                phongTroSearchList.clear();
                switch (tinhTrang){
                    case 0:{
                        binding.textViewTinhtrang.setText("Tất cả");
                        phongTroSearchList.addAll(phongtrolist);
                        phongTroAdapter.notifyDataSetChanged();
                        break;
                    }
                    case 1: {
                        binding.textViewTinhtrang.setText("Còn phòng");
                        for (Room obj : phongtrolist){
                            if (!obj.isRented()){
                                phongTroSearchList.add(obj);
                            }
                        }
                        phongTroAdapter.notifyDataSetChanged();
                        break;
                    }
                    case 2: {
                        binding.textViewTinhtrang.setText("Đã thuê");
                        for (Room obj : phongtrolist){
                            if (obj.isRented()){
                                phongTroSearchList.add(obj);
                            }
                        }
                        phongTroAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                phongTroSearchList.clear();
                for (Room obj : phongtrolist) {
                    String diachi=obj.getLocation().getWard().getPath_with_type();
                    if (diachi.toLowerCase().contains(s.toLowerCase())) {
                        phongTroSearchList.add(obj);
                    }
                }
                phongTroAdapter.notifyDataSetChanged();
                return true;
            }
        });

        loadData();
        return view;
    }

    private void loadData(){
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rooms");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> temphongtro = new ArrayList<>();
                for (DataSnapshot Snapshot: snapshot.getChildren()){
                    String status = Snapshot.child("status").getValue(String.class);
                    if (status.equals("approved")){
                        Room phongtro = Snapshot.getValue(Room.class);
                        temphongtro.add(phongtro);
                    }
                }
                phongtrolist.addAll(temphongtro);

                if (phongtrolist.isEmpty()) {
                    replaceFragment(new AdminPhongTro_FragmentEmpty());
                }
                else {
                    phongTroSearchList.clear();
                    phongTroSearchList.addAll(phongtrolist);
                    phongTroAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutPhongTro, fragment);
        fragmentTransaction.commit();
    }

}