package com.example.renthouse.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.renthouse.Admin.Adapter.BaoCaoNguoiDungAdapter;
import com.example.renthouse.OOP.Reports;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminBaoCaoNguoiDungBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_ActivityBaoCaoNguoiDung extends AppCompatActivity {
    private ActivityAdminBaoCaoNguoiDungBinding binding;
    private FirebaseDatabase database;
    private List<Reports> reportsList;
    private BaoCaoNguoiDungAdapter baoCaoNguoiDungAdapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBaoCaoNguoiDungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(Admin_ActivityBaoCaoNguoiDung.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        init();
        setListeners();
        loadData();
    }
    private void init() {
        database = FirebaseDatabase.getInstance();
        reportsList = new ArrayList<>();
        baoCaoNguoiDungAdapter = new BaoCaoNguoiDungAdapter(getApplicationContext(), reportsList);
        binding.recycleView.setAdapter(baoCaoNguoiDungAdapter);
    }
    private void setListeners() {
        binding.btnBack.setOnClickListener(v-> onBackPressed());
    }
    private void loadData() {
        progressDialog.show();
        DatabaseReference reference = database.getReference();
        reference.child("Reports").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reports> tempReports = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reports reports = snapshot.getValue(Reports.class);
                    tempReports.add(reports);
                }
                reportsList.addAll(tempReports);
                Log.d("count", reportsList.size()+"");
                baoCaoNguoiDungAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
}