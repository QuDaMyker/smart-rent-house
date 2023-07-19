package com.example.renthouse.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminThongTinNguoiDungBinding;
import com.example.renthouse.utilities.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Admin_ActivityThongTinNguoiDung extends AppCompatActivity {
    private ActivityAdminThongTinNguoiDungBinding binding;
    private Intent intent;
    private NguoiDung nguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminThongTinNguoiDungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        init();
        setListeners();

    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnKhoaTaiKhoan.setOnClickListener(v -> {
            AccountClass account = nguoiDung.getAccountClass();
            if (nguoiDung.getAccountClass().getBlocked()) {
                account.setBlocked(false);
                account.setThoiGianKhoa("Khong khoa");
            } else {
                account.setBlocked(true);
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(now);
                account.setThoiGianKhoa(formattedDate);
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();

            reference.child("Accounts").child(nguoiDung.getKey()).setValue(account);
            Toast.makeText(this, "Thao tac thanh cong", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            onBackPressed();
        });
    }

    private void init() {
        intent = getIntent();
        nguoiDung = (NguoiDung) intent.getSerializableExtra(Constants.KEY_NGUOIDUNG);
        if (nguoiDung.getAccountClass().getBlocked()) {
            binding.thongbaokhoa.setVisibility(View.VISIBLE);
            binding.tvThoiGianKhoa.setText(nguoiDung.getAccountClass().getThoiGianKhoa());
            Picasso.get().load(nguoiDung.getAccountClass().getImage()).into(binding.accountPersonalImageProfile);
            binding.tietFullName.setText(nguoiDung.getAccountClass().getFullname());
            binding.tietEmail.setText(nguoiDung.getAccountClass().getEmail());
            binding.tietPhoneNumber.setText(nguoiDung.getAccountClass().getPhoneNumber());
            binding.btnKhoaTaiKhoan.setBackgroundColor(Color.parseColor("#6DC565"));
            binding.btnKhoaTaiKhoan.setText("Mở khóa tài khoản này");
        } else {
            binding.thongbaokhoa.setVisibility(View.INVISIBLE);
            Picasso.get().load(nguoiDung.getAccountClass().getImage()).into(binding.accountPersonalImageProfile);
            binding.tietFullName.setText(nguoiDung.getAccountClass().getFullname());
            binding.tietEmail.setText(nguoiDung.getAccountClass().getEmail());
            binding.tietPhoneNumber.setText(nguoiDung.getAccountClass().getPhoneNumber());
            binding.btnKhoaTaiKhoan.setBackgroundColor(Color.parseColor("#FF425E"));
            binding.btnKhoaTaiKhoan.setText("Khóa tài khoản này");
        }
    }
}