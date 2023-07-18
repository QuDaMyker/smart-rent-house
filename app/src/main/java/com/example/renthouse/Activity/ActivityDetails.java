package com.example.renthouse.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.RoomAdapter;
import com.example.renthouse.Adapter.UtilitiesAdapter;
import com.example.renthouse.Chat.Dashboard.ActivityChat;
import com.example.renthouse.Chat.OOP.Conversation;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityDetailsBinding;
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

public class ActivityDetails extends BaseActivity {
    private ActivityDetailsBinding binding;
    private static final int REQUEST_CALL = 1;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private  String idAC = null;
    private String moTaP = null;
    private int itemsTI = 6;

    private int minItemRomRcm = 4;
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
    TextView tvChiDuong;
    TextView tvSdt;
    TextView tvGiaDien;
    TextView tvGiaNuoc;
    TextView tvGiaXe;
    TextView tvGiaWifi;
    TextView tvMota;
    TextView tvNgayDang;
    TextView tvXemThemMT;
    List<String> listTI;
    RecyclerView rcvTienIch;
    TextView tvThemTI;
    AccountClass Tg;
    ImageView ivTacGia;
    TextView tvTenTG;
    TextView tvSoP;
    ImageButton btnThemTTtg;
    RecyclerView rcvDeXuatP;
    int visibleRowCount = 2;
    int totalRowCount;
    List<Room> rcmRooms;
    TextView tvThemDX;
    TextView tvGiaP;
    MaterialButton ibChat;
    MaterialButton ibCall;
    MaterialButton btnToDelete;
    MaterialButton btnToEdit;
    String accountPhone;
    private PreferenceManager preferenceManager;
    LinearLayout NormalUserLayout;
    LinearLayout OwnerLayout;

    public interface RoomListListener {
        void onRoomListCreated(List<Room> rooms);
    }
    @Override
    protected void onStart() {
        super.onStart();

        IsLikedRoom();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Rooms");

        NormalUserLayout = findViewById(R.id.NormalUserLayout);
        OwnerLayout = findViewById(R.id.OwnerLayout);

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
        tvThemDX = findViewById(R.id.tvMoreRoom);
        tvGiaP = findViewById(R.id.tvGia);
        ibCall = findViewById(R.id.btnToCall);
        ibChat = findViewById(R.id.btnToChat);
        btnToDelete = findViewById(R.id.btnToDelete);
        btnToEdit = findViewById(R.id.btnToEdit);
        preferenceManager = new PreferenceManager(ActivityDetails.this);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        room = (Room) intent.getSerializableExtra("selectedRoom");
        if (room != null) {
            addSeenRoom(room);
        }
        if (room.getCreatedBy().getEmail().equals(preferenceManager.getString(Constants.KEY_EMAIL))) {
            OwnerLayout.setVisibility(View.VISIBLE);
            NormalUserLayout.setVisibility(View.GONE);
        } else {
            OwnerLayout.setVisibility(View.GONE);
            NormalUserLayout.setVisibility(View.VISIBLE);
        }


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

        if (room.isRented())
        {
            tvTinhTrang.setText("Đã cho thuê");
        }
        else
        {
            if (room.getStatus().equals(Constants.STATUS_DELETED))
            {
                tvTinhTrang.setText("Phòng đã bị xóa");
            }
        }
        tvDienTich.setText(String.valueOf(room.getArea()) + " m2");
        tvCoc.setText(String.valueOf(room.getDeposit()) + " tr");
        tvTenP.setText(room.getTitle());
        tvDiaChi.setText(room.getLocation().LocationToString());

        tvSdt.setText(room.getPhoneNumber());
        accountPhone = room.getPhoneNumber();

        tvGiaDien.setText(String.valueOf(room.getElectricityCost() / 1000) + "K");
        tvGiaNuoc.setText(String.valueOf(room.getWaterCost() / 1000) + "K");
        if (room.isParking())
        {
            tvGiaXe.setText(String.valueOf(room.getParkingFee() / 1000) + "K");
        }
        else {
            tvGiaXe.setText("Không có");
        }

        tvGiaWifi.setText(String.valueOf(room.getInternetCost() / 1000) + "K");

        //Xử lý ô mô tả
        moTaP = room.getDescription();
        int widthTv = fromDptoInt(360);
        int temp = getTextWidth(moTaP);
        if (getTextWidth(moTaP) >  3*widthTv)
        {
            tvXemThemMT.setVisibility(View.VISIBLE);
            tvMota.setText(moTaP);
        }
        else
        {
            tvXemThemMT.setVisibility(View.GONE);
            tvMota.setText(moTaP);
        }

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
        //rcmRooms = getListRcmRoomFromFB();
        getListRcmRoomFromFB(new RoomListListener() {
            @Override
            public void onRoomListCreated(List<Room> rcmRooms) {
                if (rcmRooms.size() != 0) {
                    RoomAdapter roomAdapter = new RoomAdapter(ActivityDetails.this, rcmRooms);
                    roomAdapter.setEnableLikeButton(View.GONE);
                    minItemRomRcm = Math.min(minItemRomRcm, rcmRooms.size());
                    roomAdapter.setLimit(minItemRomRcm);
                    rcvDeXuatP.setAdapter(roomAdapter);
                    if (rcmRooms.size() <=minItemRomRcm)
                    {
                        tvThemDX.setVisibility(View.GONE);
                    }
                    else
                    {
                        tvThemDX.setVisibility(View.VISIBLE);
                    }
                    tvThemDX.setOnClickListener(new View.OnClickListener() {
                        boolean isTheLast = false;
                        int itemShowed = minItemRomRcm;

                        @Override
                        public void onClick(View v) {
                            if (!isTheLast) {
                                itemShowed += minItemRomRcm;
                                itemShowed = (itemShowed <= rcmRooms.size()) ? itemShowed : rcmRooms.size();
                                roomAdapter.setLimit(itemShowed);
                                if (itemShowed == rcmRooms.size()) {
                                    isTheLast = true;
                                    tvThemDX.setText("Thu gọn");
                                }
                            }
                            else {
                                roomAdapter.setLimit(minItemRomRcm);
                                itemShowed = minItemRomRcm;
                                tvThemDX.setText("Xem thêm");
                                isTheLast = false;
                            }
                        }
                    });
                } else {
                    rcvDeXuatP.setVisibility(View.GONE);
                    tvThemDX.setText("Chưa tìm thấy phòng phù hợp");
                    int color = ContextCompat.getColor(ActivityDetails.this, R.color.Secondary_40);
                    tvThemDX.setTextColor(color);
                }
            }
        });

        //sự kiện các nút
        cbLiked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu checkbox được chọn, them id phòng dzo ds Likedroom
                    DatabaseReference likedRef = db.getReference("LikedRooms").child(idAC);
                    String idRoom = room.getId();
                    likedRef.child(idRoom).setValue(idRoom);

                }
                else {
                    DatabaseReference likedRef = db.getReference("LikedRooms").child(idAC);
                    String idRoom = room.getId();
                    likedRef.child(idRoom).removeValue();
                }
            }
        });
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
                    tvMota.setMaxLines(3);
                    tvXemThemMT.setText("Xem thêm");
                }

            }
        });

       /* tvThemDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//chưa làm*/
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ActivityDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityDetails.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", accountPhone, null));
                    startActivity(intent);
                }
            }
        });
        ibChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preferenceManager.getString(Constants.KEY_USER_KEY) != null) {
                    Log.d("key", preferenceManager.getString(Constants.KEY_USER_KEY));
                } else {
                    Log.d("key", "khong co key");
                }

                Intent intentChat = new Intent(ActivityDetails.this, ActivityChat.class);
                intentChat.putExtra("currentKey", preferenceManager.getString(Constants.KEY_USER_KEY));
                intentChat.putExtra("name", room.getCreatedBy().getFullname());
                intentChat.putExtra("email", room.getCreatedBy().getEmail());
                intentChat.putExtra("profile_pic", room.getCreatedBy().getImage());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();


                Query query = reference.child("Accounts").orderByChild("email").equalTo(room.getCreatedBy().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String otherKey = dataSnapshot.getKey();
                            Log.d("keyRoom", otherKey);
                            intentChat.putExtra("otherKey", otherKey);
                            Query query1 = reference.child("Chat").child(preferenceManager.getString(Constants.KEY_USER_KEY)).orderByKey().equalTo(otherKey);
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {

                                        Date data = new Date();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                        SimpleDateFormat simpleTimeFormatConversaton = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                                        reference.child("Chat").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child(otherKey).child("1").child("sender").setValue(preferenceManager.getString(Constants.KEY_USER_KEY));
                                        reference.child("Chat").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child(otherKey).child("1").child("msg").setValue("Xin Chào");
                                        reference.child("Chat").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child(otherKey).child("1").child("send-date").setValue(simpleDateFormat.format(data));
                                        reference.child("Chat").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child(otherKey).child("1").child("send-time").setValue(simpleTimeFormatConversaton.format(data));

                                        Conversation conversation = new Conversation(preferenceManager.getString(Constants.KEY_USER_KEY), otherKey, "Xin Chào", simpleDateFormat.format(data).toString(), simpleTimeFormatConversaton.format(data).toString(), false);

                                        reference.child("Conversations").child(preferenceManager.getString(Constants.KEY_USER_KEY)).child(otherKey).setValue(conversation);

                                        // Set value cho người nhận
                                        reference.child("Chat").child(otherKey).child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("1").child("sender").setValue(preferenceManager.getString(Constants.KEY_USER_KEY));
                                        reference.child("Chat").child(otherKey).child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("1").child("msg").setValue("Xin Chào");
                                        reference.child("Chat").child(otherKey).child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("1").child("send-date").setValue(simpleDateFormat.format(data));
                                        reference.child("Chat").child(otherKey).child(preferenceManager.getString(Constants.KEY_USER_KEY)).child("1").child("send-time").setValue(simpleTimeFormatConversaton.format(data));

                                        reference.child("Conversations").child(otherKey).child(preferenceManager.getString(Constants.KEY_USER_KEY)).setValue(conversation);

                                        Log.d("voday", "Co vo day");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        startActivity(intentChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDetails.this, ActivityPost.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("roomToEdit", room);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        btnToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog confirmDialog = new AlertDialog.Builder(ActivityDetails.this)
                        .setTitle("Xóa phòng trọ")
                        .setMessage("Bạn có chắc chắn muốn xóa phòng trọ này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference notiSchedulesRef = database.getReference("Rooms");
                                notiSchedulesRef.updateChildren(Collections.singletonMap("status", Constants.STATUS_DELETED)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        room.setStatus(Constants.STATUS_DELETED);
                                        Toast.makeText(ActivityDetails.this, "Xóa phòng trọ thành công", Toast.LENGTH_SHORT).show();
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

        binding.lnTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roomToOwner = new Intent(ActivityDetails.this, ActivityOwnerInformation.class);
                roomToOwner.putExtra("roomToOwer", room);
                startActivity(roomToOwner);
            }
        });


        // nếu là phòng do user hiện tại đăng thì không cho hiện nút liên hệ
        if (room.getCreatedBy().getEmail().equals(preferenceManager.getString(Constants.KEY_EMAIL))) {
            binding.lnTacGia.setVisibility(View.GONE);
        }

    }

    private void addSeenRoom(Room room) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accRef = database.getReference("Accounts");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //DatabaseReference roomRef = database.getReference("Rooms");
        Query query = accRef.orderByChild("email").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Lấy được id của user
                    String idUser = dataSnapshot.getKey();

                    // Tạo node SeenRoom và add node idUser vô
                    DatabaseReference refSeen = database.getReference("SeenRoom").child(idUser);
                    refSeen.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> listRoom = new ArrayList<>();
                            if (snapshot.getChildrenCount() == 0) {
                                listRoom.add(room.getId());
                            } else {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    listRoom.add(snapshot1.getValue(String.class));
                                }
                                if (listRoom.contains(room.getId())) {
                                    listRoom.remove(room.getId());
                                }
                                listRoom.add(0, room.getId());
                                if (listRoom.size() > 10) {
                                    listRoom.remove(listRoom.size() - 1);
                                }
                            }
                            refSeen.setValue(listRoom);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void IsLikedRoom () {
        //xét id p này có nằm trong ds thích của user
        if (preferenceManager.getString(Constants.KEY_USER_KEY) != null) {
            Log.d("key", preferenceManager.getString(Constants.KEY_USER_KEY));
        }
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String emaiCur = currentUser.getEmail();

        DatabaseReference refAc = db.getReference("Accounts");
        refAc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailAc = null;
                for (DataSnapshot snapAc : snapshot.getChildren()) {
                    emailAc = snapAc.child("email").getValue(String.class);
                    if (emaiCur.equals(emailAc)) {
                        idAC = snapAc.getKey();
                        DatabaseReference refLiked = db.getReference("LikedRooms").child(idAC);
                        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check lại khúc này
                                boolean temp = false;
                                if (snapshot.hasChild(room.getId())) {
                                    temp = true;
                                    cbLiked.setChecked(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void tinhSoP(AccountClass tg) {
        // Danh edited:  t sửa vầy cho chạy nhanh hơn, đỡ phải vòng lặp ha
        Query query = ref.orderByChild("createdBy/email").equalTo(tg.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvSoP.setText(snapshot.getChildrenCount() + " phòng");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*final AtomicInteger count = new AtomicInteger(0); // Sử dụng AtomicInteger để đảm bảo tính đồng bộ

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
        });*/


    }
    public String getAfterSpace(String input) {
        int spaceIndex = input.indexOf(" ");
        if (spaceIndex != -1 && spaceIndex < input.length() - 1) {
            return input.substring(spaceIndex + 1);
        } else {
            return "";
        }
    }

    //cùng quận thì cho vô rcm
    @NonNull
//    private List<Room> getListRcmRoomFromFB() {
//        List<Room> rcmRooms = new ArrayList<Room>();
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Rooms");
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Room r = dataSnapshot.getValue(Room.class);
//                    boolean check = !r.getId().equals(room.getId());
//                    boolean checkLo = r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode());
//                    if (!r.getId().equals(room.getId()) && (r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode()))) {
//                        rcmRooms.add(r);
//                    }
//                }
//                listener.onRoomListCreated(rcmRooms);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return rcmRooms;
//    }
    private void getListRcmRoomFromFB(RoomListListener listener) {
        List<Room> rcmRooms = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Rooms");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Room r = dataSnapshot.getValue(Room.class);
                    boolean check = !r.getId().equals(room.getId());
                    boolean checkLo = r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode());
                    if (r.getStatus().equals(Constants.STATUS_APPROVED) && !r.getId().equals(room.getId()) && r.getLocation().getDistrict().getCode().equals(room.getLocation().getDistrict().getCode())) {
                        rcmRooms.add(r);
                    }
                }

                // Gọi phương thức onRoomListCreated() để trả về danh sách phòng đã tạo
                listener.onRoomListCreated(rcmRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần thiết
            }
        });
    }
    private int getTextWidth(String text) {
        // Đo chiều rộng của chuỗi được hiển thị trên TextView
        Paint paint = new Paint();
        paint.setTextSize(tvMota.getTextSize());
        return (int) paint.measureText(text);
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
    public int fromDptoInt ( float dpValue)
    {
        float density = getResources().getDisplayMetrics().density; // Lấy tỷ lệ mật độ của màn hình
        int dpToPx = (int) (dpValue * density + 0.5f);
        return dpToPx;
    }

}