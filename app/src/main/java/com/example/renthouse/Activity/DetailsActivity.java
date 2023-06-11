package com.example.renthouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    ImageButton btnPreScreen;
    ImageButton btnHeartLike;
    ImageButton btnNextImage;
    ImageButton btnPreImage;
    ImageView ivMainImage;
    RecyclerView rcvImage;
    TextView tvLoaiP;
    TextView tvSoNg;
    List<ImageView> listImage;
    TextView tvTinhTrang;
    TextView tvDienTich;
    TextView tvCoc;
    TextView tvTenP;
    TextView tvDiaChi;
    TextView tvSdt;
    TextView tvGiaDien;
    TextView tvGiaNuoc;
    TextView tvGiaXe;
    TextView tvGiaWifi;
    TextView tvMota;
    TextView tvNgayDang;
    TextView tvXemThemMT;
    RecyclerView rcvTienIch;
    TextView tvThemTI;
    ImageView igTacGia;
    TextView tvTenTG;
    TextView tvSoP;
    ImageButton btnThemTTtg;
    RecyclerView rcvDeXuatP;
    TextView tvThemDX;
    TextView tvGiaP;
    ImageButton ibChat;
    ImageButton ibCall;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //gán id
        btnPreScreen = findViewById(R.id.btnBack);
        btnHeartLike = findViewById(R.id.btnLike);
        btnNextImage = findViewById(R.id.btnNextImage);
        btnPreImage = findViewById(R.id.btnPreImage);
        ivMainImage = findViewById(R.id.imagePhong);
        rcvImage = findViewById(R.id.rcvImageP);
        tvLoaiP = findViewById(R.id.tvLoaiPhong);
        tvSoNg = findViewById(R.id.tvSoNg);
        tvTinhTrang = findViewById(R.id.tvTT);
        tvDienTich = findViewById(R.id.tvDT);
        tvCoc = findViewById(R.id.tvCoc);
        tvTenP = findViewById(R.id.tvTen);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvSdt = findViewById(R.id.tvSdt);
        tvGiaDien = findViewById(R.id.tvGiaDien);
        tvGiaNuoc = findViewById(R.id.tvGiaNuoc);
        tvGiaXe = findViewById(R.id.tvGiaXe);
        tvGiaWifi = findViewById(R.id.tvGiaWifi);
        tvMota = findViewById(R.id.tvMotaTT);
        tvXemThemMT = findViewById(R.id.tvShowMore);
        tvNgayDang = findViewById(R.id.tvNgayDang);
        rcvTienIch = findViewById(R.id.rcvTienIch);
        tvThemTI = findViewById(R.id.tvMoreTI);
        igTacGia = findViewById(R.id.imageTacGia);
        tvTenTG = findViewById(R.id.tvTenTg);
        tvSoP = findViewById(R.id.tvSoPhong);
        btnThemTTtg = findViewById(R.id.btnMoreTg);
        rcvDeXuatP = findViewById(R.id.rcvDeXuatPhong);
        tvThemDX = findViewById(R.id.tvMoreRoom);
        tvGiaP  = findViewById(R.id.tvGia);
        ibCall = findViewById(R.id.btnToCall);
        ibChat = findViewById(R.id.btnToChat);

        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnHeartLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnPreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvXemThemMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvThemDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvThemTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnThemTTtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, ActivityAccount.class);

                startActivity(intent);

            }
        });

        ibChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, ActivityChat.class);
                //luu id ng dung
//                Bundle bundle = new Bundle();
//                bundle.putString("userId", userId); // userId là id của người dùng bạn muốn chat
//                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}