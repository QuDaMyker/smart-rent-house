package com.example.renthouse.Admin.Fragment_NguoiDung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinNguoiDung;
import com.example.renthouse.Admin.Adapter.NguoiDungAdapter;
import com.example.renthouse.Admin.Adapter.NguoiDungBiChanAdapter;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Admin.listeners.ItemNguoiDungListener;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.databinding.FragmentAdminNguoiDungNguoiDungBiChanBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminNguoiDung_Fragment_NguoiDungBiChan extends Fragment implements ItemNguoiDungListener {
    private FragmentAdminNguoiDungNguoiDungBiChanBinding binding;
    private List<NguoiDung> nguoiDungs;
    private PreferenceManager preferenceManager;
    private NguoiDungBiChanAdapter nguoiDungBiChanAdapter;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminNguoiDungNguoiDungBiChanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        database = FirebaseDatabase.getInstance();

        preferenceManager = new PreferenceManager(getContext());

        nguoiDungs = new ArrayList<>();
        nguoiDungBiChanAdapter = new NguoiDungBiChanAdapter(getContext(), nguoiDungs, this);
        binding.recycleView.setAdapter(nguoiDungBiChanAdapter);
        database = FirebaseDatabase.getInstance();

        //init();
        loadData();
        return view;
    }

    private void loadData() {
        progressDialog.show();
        DatabaseReference reference = database.getReference();
        Query query = reference.child("Accounts").orderByChild("blocked").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NguoiDung> tempNguoiDungs = new ArrayList<>(); // Danh sách tạm thời để lưu trữ các NguoiDung
                int dataSnapshotCount = (int) dataSnapshot.getChildrenCount(); // Số lượng dữ liệu trong DataSnapshot
                AtomicInteger count = new AtomicInteger(0); // Biến đếm để kiểm tra khi nào dữ liệu đã được tải xong

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    AccountClass account = snapshot.getValue(AccountClass.class);
                    Query query1 = reference.child("Rooms").orderByChild("createdBy/email").equalTo(account.getEmail());
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int countRoom = (int) snapshot.getChildrenCount();
                            tempNguoiDungs.add(new NguoiDung(key, account, countRoom));


                            // Kiểm tra khi nào dữ liệu đã được tải xong
                            if (count.incrementAndGet() == dataSnapshotCount) {
                                nguoiDungs.addAll(tempNguoiDungs);
                                Log.d("count", "Count temp = " + tempNguoiDungs.size() + "Countnguoidungs = " + nguoiDungs.size());
                                nguoiDungBiChanAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Load Du Lieu Thanh Cong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
                progressDialog.dismiss();
            }
        });
    }

    private void init() {
        nguoiDungs = new ArrayList<>();
        nguoiDungBiChanAdapter = new NguoiDungBiChanAdapter(getContext(), nguoiDungs, this);
        binding.recycleView.setAdapter(nguoiDungBiChanAdapter);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onItemNguoiDungClick(NguoiDung nguoiDung) {
        Intent intent = new Intent(getActivity(), Admin_ActivityThongTinNguoiDung.class);
        intent.putExtra(Constants.KEY_NGUOIDUNG, nguoiDung);
        startActivity(intent);
    }
}