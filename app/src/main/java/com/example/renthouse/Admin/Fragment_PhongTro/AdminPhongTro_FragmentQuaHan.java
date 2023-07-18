package com.example.renthouse.Admin.Fragment_PhongTro;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.renthouse.Admin.Adapter.PhongQuaHanAdapter;
import com.example.renthouse.Admin.Adapter.PhongTroChoDuyetAdapter;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminPhongTroChoDuyetBinding;
import com.example.renthouse.databinding.FragmentAdminPhongTroQuaHanBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminPhongTro_FragmentQuaHan extends Fragment {
    FragmentAdminPhongTroQuaHanBinding binding;
    private RecyclerView recyclerView;
    private TextView empty_tv;
    private List<Room> phongquahanlist;
    private PhongQuaHanAdapter phongQuaHanAdapter;
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
        binding = FragmentAdminPhongTroQuaHanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.recycleView);
        empty_tv = view.findViewById(R.id.textView_empty);

        phongquahanlist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        phongquahanlist.clear();
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
                    Room phongtro = Snapshot.getValue(Room.class);
                    int typeroom = 5;
                    try {
                        typeroom = checkRoomStatus(phongtro);
                    } catch (Exception e) {
                    }
                    if (typeroom > 1) {
                        if (typeroom == 2) {
                            String idPhongtro = String.valueOf(phongtro.getId());
                            reference.child(idPhongtro).child("status").setValue("deleted");
                            phongtro.setStatus("deleted");
                        }
                        temphongtro.add(phongtro);
                    }
                }
                phongquahanlist.addAll(temphongtro);

                if (phongquahanlist.isEmpty()) {
                    binding.animationView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                } else {
                    binding.animationView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    phongQuaHanAdapter = new PhongQuaHanAdapter(getContext(), phongquahanlist);
                    recyclerView.setAdapter(phongQuaHanAdapter);
                }
                dialogListener.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();
            }
        });
    }

    private int checkRoomStatus(Room room) throws ParseException {
        if (room.getStatus().equals("deleted")) {
            return 3;
        }
        if (room.getStatus().equals("approved")) {
            String dateString = room.getDateTime();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            long diffInHours = 0;

            try {
                Date time = format.parse(dateString);
                Date currentTime = new Date();
                long diffInMilliseconds = currentTime.getTime() - time.getTime();
                diffInHours = diffInMilliseconds / (24 * 60 * 60 * 1000);
            } catch (Exception e) {
            }

            if (diffInHours >= 30) {
                return 2;
            }
        }
        return 1;
    }
}