package com.example.renthouse.Admin.Fragment_PhongTro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinPhong;
import com.example.renthouse.Admin.Adapter.PhongTroAdapter;
import com.example.renthouse.Admin.Adapter.PhongTroChoDuyetAdapter;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminPhongTroChoDuyetBinding;
import com.example.renthouse.databinding.FragmentAdminPhongTroDanhSachBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPhongTro_FragmentChoDuyet extends Fragment {
    FragmentAdminPhongTroChoDuyetBinding binding;
    private RecyclerView recyclerView;
    private TextView empty_tv;
    private List<Room> phongchoduyetlist;
    private PhongTroChoDuyetAdapter phongTroChoDuyetAdapter;
    private DialogListener dialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminPhongTroChoDuyetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        recyclerView = view.findViewById(R.id.recycleView);
        empty_tv = view.findViewById(R.id.textView_empty);

        phongchoduyetlist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        phongchoduyetlist.clear();
        loadData();
        super.onResume();
    }

    private void loadData() {
        dialogListener.showDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rooms");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> temphongtro = new ArrayList<>();
                for (DataSnapshot Snapshot : snapshot.getChildren()) {
                    String status = Snapshot.child("status").getValue(String.class);
                    if (status.equals("pending")) {
                        Room phongtro = Snapshot.getValue(Room.class);
                        temphongtro.add(phongtro);
                    }
                }
                phongchoduyetlist.addAll(temphongtro);

                if (phongchoduyetlist.isEmpty()) {
                    binding.animationView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                } else {
                    binding.animationView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    phongTroChoDuyetAdapter = new PhongTroChoDuyetAdapter(getContext(), phongchoduyetlist, new ItemClick() {
                        @Override
                        public void onItemClick(Room room) {
                            Intent intent = new Intent(getContext(), Admin_ActivityThongTinPhong.class);
                            intent.putExtra("selectedRoom", room);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(phongTroChoDuyetAdapter);
                }
                dialogListener.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();
            }
        });
    }
}