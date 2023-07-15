package com.example.renthouse.Admin.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Activity.ActivityFullImage;
import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Adapter.UtilitiesAdapter;
import com.example.renthouse.Chat.Dashboard.ActivityChat;
import com.example.renthouse.Chat.OOP.Conversation;
import com.example.renthouse.FCM.SendNotificationTask;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin_ActivityThongTinPhong extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private String moTaP = null;
    private int itemsTI = 6;

    private int minItemRomRcm = 4;
    private Room room;
    private ImageButton btnPreScreen;
    private ImageButton btnNextImage;
    private ImageButton btnPreImage;
    private int curImage = 0;
    private TextView stt;
    private List<String> srcImages;
    private ImageView ivImages;
    private TextView tvLoaiP;
    private TextView tvSoNg;
    private List<String> listImages;
    private TextView tvTinhTrang;
    private TextView tvDienTich;
    private TextView tvCoc;
    private TextView tvTenP;
    private TextView tvDiaChi;
    private TextView tvChiDuong;
    private TextView tvSdt;
    private TextView tvGiaDien;
    private TextView tvGiaNuoc;
    private TextView tvGiaXe;
    private TextView tvGiaWifi;
    private TextView tvMota;
    private TextView tvNgayDang;
    private TextView tvXemThemMT;
    private List<String> listTI;
    private RecyclerView rcvTienIch;
    private TextView tvThemTI;
    private AccountClass Tg;
    private ImageView ivTacGia;
    private TextView tvTenTG;
    private TextView tvSoP;
    private ImageButton btnThemTTtg;
    private RecyclerView rcvDeXuatP;
    private int visibleRowCount = 2;
    int totalRowCount;
    private List<Room> rcmRooms;
    private TextView tvThemDX;
    private TextView tvGiaP;

    private String accountPhone;
    private PreferenceManager preferenceManager;
    private LinearLayout lnDuyet;
    private LinearLayout lnXoa;
    private Button btnDelete;
    private Button btnAcp;
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_thong_tin_phong);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Rooms");

        listImages = new ArrayList<String>();
        listTI = new ArrayList<String>();
        Tg = new AccountClass();

        //gán id
        btnPreScreen = findViewById(R.id.btnBack);
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
        tvChiDuong = findViewById(R.id.tvChiDuong);
        tvSdt = findViewById(R.id.tvSdt);
        tvGiaDien = findViewById(R.id.tvDien);
        tvGiaNuoc = findViewById(R.id.tvNuoc);
        tvGiaXe = findViewById(R.id.tvXe);
        tvGiaWifi = findViewById(R.id.tvWifi);
        tvMota = findViewById(R.id.tvMotaTT);
        tvXemThemMT = findViewById(R.id.tvShowMoreMT);
        tvNgayDang = findViewById(R.id.tvNgay);
        rcvTienIch = findViewById(R.id.rcvTienIch);
        tvThemTI = findViewById(R.id.tvMoreTI);
        ivTacGia = findViewById(R.id.imageTacGia);
        tvTenTG = findViewById(R.id.tvTenTg);
        tvSoP = findViewById(R.id.tvSoPhong);
        btnThemTTtg = findViewById(R.id.btnMoreTg);
        rcvDeXuatP = findViewById(R.id.rcvDeXuatPhong);
        //tvThemDX = findViewById(R.id.tvMoreRoom);
        tvGiaP = findViewById(R.id.tvGia);

        lnDuyet = findViewById(R.id.lnDuyet);
        lnXoa = findViewById(R.id.lnDelete);

        btnDelete = findViewById(R.id.btnDelete);
        btnAcp = findViewById(R.id.btnAcp);
        btnCancel = findViewById(R.id.btnCancel);

        preferenceManager = new PreferenceManager(Admin_ActivityThongTinPhong.this);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        room = (Room) intent.getSerializableExtra("selectedRoom");

        LoadLinearLayoutOption();

        /*Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        room = (Room) bundle.get("selectedRoom");*/

        //load thông tin p được chọn
        //gán các thông tin

        srcImages = room.getImages();
        String src = srcImages.get(curImage);
        stt.setText("1/" + String.valueOf(srcImages.size()));
        Picasso.get().load(src).into(ivImages);

        if (curImage == 0) {
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

        tvGiaDien.setText(String.valueOf(room.getElectricityCost() / 1000) + "K");
        tvGiaNuoc.setText(String.valueOf(room.getWaterCost() / 1000) + "K");
        tvGiaXe.setText(String.valueOf(room.getParkingFee() / 1000) + "K");
        tvGiaWifi.setText(String.valueOf(room.getInternetCost() / 1000) + "K");
        tvMota.setText(room.getDescription());

        String ngayDang = room.getDateTime();

        tvNgayDang.setText(getAfterSpace(ngayDang));

        listTI = room.getUtilities();
        if (listTI != null)
        {
            UtilitiesAdapter utilitiesAdapter = new UtilitiesAdapter(listTI, this);
            utilitiesAdapter.setLimit(Math.min(itemsTI, listTI.size()));

            GridLayoutManager grid1 = new GridLayoutManager(this, 3);
            rcvTienIch.setLayoutManager(grid1);
            rcvTienIch.setAdapter(utilitiesAdapter);
            if (itemsTI >= listTI.size())
            {
                tvThemTI.setVisibility(View.GONE);
            }
            tvThemTI.setOnClickListener(new View.OnClickListener() {
                boolean isExpand = false;
                @Override
                public void onClick(View v) {
                    isExpand = !isExpand;
                    if (isExpand) {
                        utilitiesAdapter.setLimit(listTI.size());
                        tvThemTI.setText("Thu gọn");
                    } else {
                        utilitiesAdapter.setLimit(Math.min(itemsTI, listTI.size()));
                        utilitiesAdapter.notifyDataSetChanged();
                        tvThemTI.setText("Xem thêm");
                    }
                }
            });
        }
        else
        {
            rcvTienIch.setVisibility(View.GONE);
            tvThemTI.setText("Phòng chưa được cập nhập các tiện ích");
            int color = ContextCompat.getColor(this, R.color.Secondary_40);
            tvThemTI.setTextColor(color);
        }


        Tg = room.getCreatedBy();
        String sourceImageAccount = Tg.getImage();
        Picasso.get().load(sourceImageAccount).into(ivTacGia);

        tvTenTG.setText(Tg.getFullname());

        tinhSoP(Tg);

        tvGiaP.setText(String.valueOf(room.getPrice()) + " đ");

        GridLayoutManager grid = new GridLayoutManager(this, 2);

        rcvDeXuatP.setLayoutManager(grid);

        rcmRooms = new ArrayList<>();
        RoomAdapter roomAdapter = new RoomAdapter(this, rcmRooms);

        rcvDeXuatP.setAdapter(roomAdapter);

        getListRoomFromFB();
        visibleRowCount = 2;
        totalRowCount = rcmRooms.size();

        //sự kiện các nút
        tvChiDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = tvDiaChi.getText().toString();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Ứng dụng Google Maps không được cài đặt trên thiết bị của bạn.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                bundle.putSerializable("listImages", (ArrayList<String>) srcImages);
                bundle.putInt("curImage", curImage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnPreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage--;
                stt.setText(String.valueOf(curImage + 1) + "/" + srcImages.size());
                String temp = srcImages.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != srcImages.size() - 1) {
                    btnNextImage.setVisibility(View.VISIBLE);
                }
                if (curImage == 0) {
                    btnPreImage.setVisibility(View.GONE);
                } else {
                    btnPreImage.setVisibility(View.VISIBLE);
                }
            }
        });
        btnNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curImage++;
                stt.setText(String.valueOf(curImage + 1) + "/" + srcImages.size());
                String temp = srcImages.get(curImage);
                Picasso.get().load(temp).into(ivImages);
                if (curImage != 0) {
                    btnPreImage.setVisibility(View.VISIBLE);
                }
                if (curImage == srcImages.size() - 1) {
                    btnNextImage.setVisibility(View.GONE);
                } else {
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
                isExpand = !isExpand;
                if (isExpand) {
                    tvMota.setMaxLines(Integer.MAX_VALUE);
                    tvXemThemMT.setText("Thu gọn");
                } else {
                    tvMota.setMaxLines(2);
                    tvXemThemMT.setText("Xem thêm");
                }

            }
        });
       /* tvThemDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//chưa làm*/

        //nhấn nút ACP thì đổi trạng thái phobgf thành Approved
        btnAcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                room.setStatus(Constants.STATUS_APPROVED);
                DatabaseReference refRoom =  ref.child(room.getId());
                refRoom.child("status").setValue(Constants.STATUS_APPROVED);

                String pathObject = String.valueOf(room.getId());
                pushPopularRoom(room, pathObject);

                Toast.makeText(Admin_ActivityThongTinPhong.this, "Bạn đã duyệt phòng thành công", Toast.LENGTH_SHORT).show();

                Notification notification = new Notification("Có phòng trọ mới vừa được đăng trên Rent House", "Hãy kiểm tra ngay để không bỏ lỡ cơ hội tuyệt vời này!", "room");
                notification.setAttachedRoomKey(room.getId());
                SendNotificationTask task = new SendNotificationTask(Admin_ActivityThongTinPhong.this, notification);

                task.execute();
                LoadLinearLayoutOption();
            }
        });

        //nhấn nút HỦY thì xóa thì cập nhập lại status
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room.setStatus(Constants.STATUS_DELETED);
                DatabaseReference refRoom =  ref.child(room.getId());
                refRoom.child("status").setValue(Constants.STATUS_DELETED);

                Toast.makeText(Admin_ActivityThongTinPhong.this, "Bạn đã hủy yêu cầu duyệt của phòng này", Toast.LENGTH_SHORT).show();
                btnAcp.setEnabled(false);
                btnCancel.setEnabled(false);

            }
        });
        //nhấn nút xáo thì xóa phòng khỏi danh sách
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog confirmDialog = new AlertDialog.Builder(Admin_ActivityThongTinPhong.this)
                        .setTitle("Xóa phòng trọ")
                        .setMessage("Bạn có chắc chắn muốn xóa phòng trọ này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference notiSchedulesRef = database.getReference("Rooms").child(room.getId());
                                notiSchedulesRef.updateChildren(Collections.singletonMap("status", Constants.STATUS_DELETED)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        room.setStatus(Constants.STATUS_DELETED);
                                        Toast.makeText(Admin_ActivityThongTinPhong.this, "Xóa phòng trọ thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                confirmDialog.show();
            }
        });
    }

    private void pushPopularRoom(Room room, String idRoom) {
        String roomcode = room.getLocation().getDistrict().getCode();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PopularRoom").child(roomcode);

        String name = room.getLocation().getDistrict().getName_with_type();
        reference.child("Name").setValue(name);

        DatabaseReference images_ref = FirebaseDatabase.getInstance().getReference("ImagePhoBien");
        images_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> images = new ArrayList<>();
                for(DataSnapshot Snapshot : snapshot.getChildren()){
                    images.add(Snapshot.getKey());
                }
                Random random = new Random();
                int randomIndex = random.nextInt(images.size());
                String image = images.get(randomIndex);

                reference.child("Image").setValue(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadLinearLayoutOption() {
        if (room.getStatus().equals(Constants.STATUS_PENDING)) {
            lnDuyet.setVisibility(View.VISIBLE);
            lnXoa.setVisibility(View.GONE);
        }
        else{
            if (room.getStatus().equals(Constants.STATUS_APPROVED)) {
                lnDuyet.setVisibility(View.GONE);
                lnXoa.setVisibility(View.VISIBLE);
            }
        }
    }

    public String getAfterSpace(String input) {
        int spaceIndex = input.indexOf(" ");
        if (spaceIndex != -1 && spaceIndex < input.length() - 1) {
            return input.substring(spaceIndex + 1);
        } else {
            return "";
        }
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
                tvSoP.setText(String.valueOf(count) + " phòng");
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Room r = dataSnapshot.getValue(Room.class);
                    if (!r.getId().equals(room.getId()) && (r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode()))) {
                        rcmRooms.add(r);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}