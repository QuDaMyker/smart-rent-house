package com.example.renthouse.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Adapter.UtilitiesAdapter;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ActivityDetails extends AppCompatActivity {
    private static final  int REQUEST_CALL = 1;

    private FirebaseDatabase db ;
    private DatabaseReference ref;
    Room room;

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
    int visibleRowCount = 2 ;
    int totalRowCount ;

    List<Room> rcmRooms;
    TextView tvThemDX;
    TextView tvGiaP;
    ImageButton ibChat;
    ImageButton ibCall;
    String accountPhone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Rooms");

        listImages = new ArrayList<String>();
        listTI = new ArrayList<String>();
        Tg = new AccountClass();

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
        tvXemThemMT = findViewById(R.id.tvShowMoreMT);
        tvNgayDang = findViewById(R.id.tvNgay);
        rcvTienIch = findViewById(R.id.rcvTienIch);
        //tvThemTI = findViewById(R.id.tvMoreTI);
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
        room = (Room) bundle.get ("selectedRoom");

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
        accountPhone = room.getPhoneNumber();

        tvGiaDien.setText(String.valueOf(room.getElectricityCost()/1000) + "K");
        tvGiaNuoc.setText(String.valueOf(room.getWaterCost()/1000) + "K");
        tvGiaXe.setText(String.valueOf(room.getParkingFee()/1000) +"K");
        tvGiaWifi.setText(String.valueOf(room.getInternetCost()/1000) +"K");
        tvMota.setText(room.getDescription());
        //tvNgayDang.setText(R.id.tvNgayDang);

        listTI = room.getUtilities();
        UtilitiesAdapter utilitiesAdapter = new UtilitiesAdapter(listTI, this);

        GridLayoutManager grid1 = new GridLayoutManager(this, 3);
        rcvTienIch.setLayoutManager(grid1);
        rcvTienIch.setAdapter(utilitiesAdapter);


        Tg = room.getCreatedBy();
        String sourceImageAccount = Tg.getImage();
        Picasso.get()
                .load(sourceImageAccount)
                .into(ivTacGia);

        tvTenTG.setText(Tg.getFullname());

        tinhSoP(Tg);

        tvGiaP.setText(String.valueOf(room.getPrice()) +" đ");

        GridLayoutManager grid = new GridLayoutManager(this, 2);

        rcvDeXuatP.setLayoutManager(grid);

        rcmRooms = new ArrayList<>();
        RoomAdapter roomAdapter = new RoomAdapter(this, rcmRooms);

        rcvDeXuatP.setAdapter(roomAdapter);

        getListRoomFromFB();
        visibleRowCount = 2;
        totalRowCount = rcmRooms.size();

        //sự kiện các nút
        btnPreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityFullImage.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listImages", (ArrayList<String>)srcImages);
                bundle.putInt("curImage", curImage);
                intent.putExtras(bundle);
                startActivity(intent);
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

            }
        });//chua làm
        tvXemThemMT.setOnClickListener(new View.OnClickListener() {

            boolean isExpand = false;
            @Override
            public void onClick(View v) {
                isExpand  = !isExpand;
                if (isExpand)
                {
                    tvMota.setMaxLines(Integer.MAX_VALUE);
                    tvXemThemMT.setText("Thu gọn");
                }
                else {
                    tvMota.setMaxLines(2);
                    tvXemThemMT.setText("Xem thêm");
                }

            }
        });
        tvThemDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//chưa làm
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ActivityDetails.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ActivityDetails.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }
                else
                {
                    Intent intent = new Intent( Intent.ACTION_DIAL, Uri.fromParts("tel", accountPhone, null));
                    startActivity(intent);
                }
            }
        });
        ibChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });//chưa làm
    }

    private void tinhSoP(AccountClass tg) {
        final AtomicInteger count = new AtomicInteger(0); // Sử dụng AtomicInteger để đảm bảo tính đồng bộ

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Room r = dataSnapshot.getValue(Room.class);
                    if (r.getCreatedBy().getEmail().equals(tg.getEmail())) {
                        count.incrementAndGet(); // Tăng giá trị của count
                    }
                }
                tvSoP.setText(String.valueOf(count) +" phòng");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //cùng quận thì cho vô rcm
    private void getListRoomFromFB() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Rooms");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Room r = dataSnapshot.getValue(Room.class);
                    if (!r.getId().equals(room.getId()) && (r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode())))
                    {
                        rcmRooms.add(r);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", accountPhone, null));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ứng dụng không được phép gọi", Toast.LENGTH_SHORT).show();
            }
        }
    }
}