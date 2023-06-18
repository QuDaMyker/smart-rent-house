package com.example.renthouse.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
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
    Toolbar bar;
    ImageButton btnPreScreen;
    CheckBox cbLiked;
    ImageButton btnNextImage;
    ImageButton btnPreImage;
    int curImage = 0;
    TextView stt;
    List<String> srcImages;
    ImageView ivImages;
    TextView tvLoaiP;
    TextView tvSoNg;
    List<String> listImages;
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

        listImages = new ArrayList<String>();
        listTI = new ArrayList<String>();
        Tg = new AccountClass();

        //gán id
        //gán id
        btnPreScreen = findViewById(R.id.btnBack);
        cbLiked = findViewById(R.id.cbLike);
        btnNextImage = findViewById(R.id.btnNextImage);
        btnPreImage = findViewById(R.id.btnPreImage);
        ivImages = findViewById(R.id.ivImages);
        stt = findViewById(R.id.tvIndex);
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

        srcImages = room.getImages();
        String src = srcImages.get(curImage);
        stt.setText("1/" + String.valueOf(srcImages.size()));
        Picasso.get().load(src).into(ivImages);

        if (curImage ==0 )
        {
            btnPreImage.setVisibility(View.GONE);
        }

        tvLoaiP.setText(room.getRoomType());
        tvSoNg.setText(String.valueOf(room.getCapacity()) + " Nam/Nữ");
        tvTinhTrang.setText("Chưa fix");
        tvDienTich.setText(String.valueOf(room.getArea()) + " m2");
        tvCoc.setText(String.valueOf(room.getDeposit()) + " tr");
        tvTenP.setText(room.getTitle());
        tvDiaChi.setText(room.getLocation().LocationToString());
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

        tvGiaP.setText(String.valueOf(room.getPrice()) +" đ");

        //sự kiện các nút
        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage--;
                stt.setText(String.valueOf(curImage+1) +"/" + srcImages.size());
                String temp = srcImages.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != srcImages.size()-1)
                {
                    btnNextImage.setVisibility(View.VISIBLE);
                }
                if (curImage == 0)
                {
                    btnPreImage.setVisibility(View.GONE);
                }
                else {
                    btnPreImage.setVisibility(View.VISIBLE);
                }
            }
        });
        btnNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage++;
                stt.setText(String.valueOf(curImage+1) +"/" + srcImages.size());
                String temp = srcImages.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != 0){
                    btnPreImage.setVisibility(View.VISIBLE);
                }
                if (curImage == srcImages.size() -1) {
                    btnNextImage.setVisibility(View.GONE);
                }
                else {
                    btnNextImage.setVisibility(View.VISIBLE);
                }
            }
        });
        btnThemTTtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityMain.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Account", Tg);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
//        tvXemThemMT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });


    }
}