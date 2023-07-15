package com.example.renthouse.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.renthouse.Admin.Adapter.BaoCaoNguoiDungAdapter;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.OOP.Reports;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminBaoCaoNguoiDungBinding;
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

        init();
        loadData();
        setListeners();

    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        reportsList = new ArrayList<>();
        baoCaoNguoiDungAdapter = new BaoCaoNguoiDungAdapter(getApplicationContext(), reportsList);
        binding.recycleView.setAdapter(baoCaoNguoiDungAdapter);

        progressDialog = new ProgressDialog(Admin_ActivityBaoCaoNguoiDung.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Thực hiện cập nhật dữ liệu ở đây
                loadData();
                // Sau khi hoàn thành cập nhật, gọi phương thức setRefreshing(false) để kết thúc hiệu ứng làm mới
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
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
                reportsList.clear();
                Collections.sort(tempReports, new Comparator<Reports>() {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    @Override
                    public int compare(Reports obj1, Reports obj2) {
                        try {
                            Date date1 = dateFormat.parse(obj2.getNgayBaoCao());
                            Date date2 = dateFormat.parse(obj1.getNgayBaoCao());
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                reportsList.addAll(tempReports);
                Log.d("count", reportsList.size() + "");
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