package com.example.renthouse.Admin.Fragment_PhongTro;

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

import com.example.renthouse.Admin.Adapter.PhongTroAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class AdminPhongTro_FragmentDanhSach extends Fragment {
    FragmentAdminPhongTroDanhSachBinding binding;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Room> phongtrolist;
    private PhongTroAdapter phongTroAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_admin_phong_tro__danh_sach, container, false);

        binding = FragmentAdminPhongTroDanhSachBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.recycleView);

        searchView = view.findViewById(R.id.search_view);

        binding.filterGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.filterNgayDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.filterTinhTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //replaceFragment(new AdminPhongTro_FragmentEmpty());

        phongtrolist = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

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
                    phongTroAdapter = new PhongTroAdapter(getContext(), phongtrolist, new ItemClick() {
                        @Override
                        public void onItemClick(Room room) {

                        }
                    });
                    recyclerView.setAdapter(phongTroAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutPhongTro, fragment);
        fragmentTransaction.commit();
    }
}