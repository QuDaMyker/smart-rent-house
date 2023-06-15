package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetails extends AppCompatActivity {

    private FirebaseDatabase db ;
    private DatabaseReference ref;
    ImageButton btnPreScreen;
    ImageButton btnHeartLike;
    ImageButton btnNextImage;
    ImageButton btnPreImage;
    ImageView ivMainImage;
    RecyclerView rcvImage;
    TextView tvLoaiP;
    TextView tvSoNg;
    List<String> listImage;
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

    List <String> listTI;
    RecyclerView rcvTienIch;
    TextView tvThemTI;

    AccountClass Tg;
    ImageView ivTacGia;
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

        listImage = new ArrayList<String>();
        listTI = new ArrayList<String>();
        Tg = new AccountClass();
        //gán id
        //gán id
        btnPreScreen = findViewById(R.id.btnBack);

        btnNextImage = findViewById(R.id.btnNextImage);
        btnPreImage = findViewById(R.id.btnPreImage);
        ivMainImage = findViewById(R.id.imagePhong);
        //rcvImage = findViewById(R.id.);
        tvLoaiP = findViewById(R.id.tvLoaiPhong);
        tvSoNg = findViewById(R.id.tvSoNg);
        tvTinhTrang = findViewById(R.id.tvTT);
        tvDienTich = findViewById(R.id.tvDT);
        tvCoc = findViewById(R.id.tvCoc);
        tvTenP = findViewById(R.id.tvTen);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvSdt = findViewById(R.id.tvSdt);
        tvGiaDien = findViewById(R.id.tvDien);
        tvGiaNuoc = findViewById(R.id.tvNuoc);
        tvGiaXe = findViewById(R.id.tvXe);
        tvGiaWifi = findViewById(R.id.tvWifi);
        tvMota = findViewById(R.id.tvMotaTT);
        tvXemThemMT = findViewById(R.id.tvShowMore);
        tvNgayDang = findViewById(R.id.tvNgay);
        //rcvTienIch = findViewById(R.id.rcvTienIch);
        tvThemTI = findViewById(R.id.tvMoreTI);
        ivTacGia = findViewById(R.id.imageTacGia);
        tvTenTG = findViewById(R.id.tvTenTg);
        tvSoP = findViewById(R.id.tvSoPhong);
        btnThemTTtg = findViewById(R.id.btnMoreTg);
        rcvDeXuatP = findViewById(R.id.rcvDeXuatPhong);
        tvThemDX = findViewById(R.id.tvMoreRoom);
        tvGiaP  = findViewById(R.id.tvGia);
        ibCall = findViewById(R.id.btnToCall);
        ibChat = findViewById(R.id.btnToChat);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
        {
            return;
        }
        Room room = (Room) bundle.get ("selectedRoom");



        //load thông tin p được chọn
        //gán các thông tin

        tvLoaiP.setText(room.getRoomType());
        tvSoNg.setText(String.valueOf(room.getCapacity()) + " Nam/Nữ");
        tvTinhTrang.setText("Chưa fix");
        tvDienTich.setText(String.valueOf(room.getArea()) + " m2");
        tvCoc.setText(String.valueOf(room.getDeposit()) + " tr");
        tvTenP.setText(room.getTitle());
        tvDiaChi.setText("chưa fix");
        tvSdt.setText(room.getPhoneNumber());
        tvGiaDien.setText(String.valueOf(room.getElectricityCost()/1000) + "K");
        tvGiaNuoc.setText(String.valueOf(room.getWaterCost()/1000) + "K");
        tvGiaXe.setText(String.valueOf(room.getParkingFee()/1000) +"K");
        tvGiaWifi.setText(String.valueOf(room.getInternetCost()/1000) +"K");
        tvMota.setText(room.getDescription());
        //tvNgayDang.setText(R.id.tvNgayDang);

        Tg = room.getCreatedBy();
        String sourceImageAccount = Tg.getImage();
        Picasso.get()
                .load(sourceImageAccount)
                .into(ivTacGia);

        tvTenTG.setText(Tg.getFullname());

        tvSoP.setText("Chưa fix");

        tvGiaP.setText(String.valueOf(room.getPrice()));

        //sự kiện các nút
        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
//        btnNextImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        tvXemThemMT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });


    }
}