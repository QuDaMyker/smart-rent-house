package com.example.renthouse.Activity;

import static com.example.renthouse.utilities.Constants.STATUS_PENDING;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.renthouse.FCM.SendNotificationTask;
import com.example.renthouse.FragmentPost.FragmentConfirm;
import com.example.renthouse.FragmentPost.FragmentInformation;
import com.example.renthouse.FragmentPost.FragmentLocation;
import com.example.renthouse.FragmentPost.FragmentUtilities;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Region;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.PolicyNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class ActivityPost extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    StepView stepView;
    ScrollView scrollView;
    int position;
    MaterialButton nextBtn;
    MaterialButton prevBtn;
    FragmentConfirm fragmentConfirm;
    FragmentInformation fragmentInformation;
    FragmentLocation fragmentLocation;
    FragmentUtilities fragmentUtilities;
    StorageReference storageReference;
    AccountClass user;
    public Room roomToEdit;

    ImageButton btnBack;

    public interface OnUploadImageCompleteListener {
        void onUploadImageComplete();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        roomToEdit = null;
        try {
            roomToEdit = (Room) getIntent().getExtras().get("roomToEdit");
        }
        catch (Exception e){

        }

        fragmentConfirm = new FragmentConfirm();
        fragmentInformation = new FragmentInformation();
        fragmentLocation = new FragmentLocation();
        fragmentUtilities = new FragmentUtilities();

        stepView = (StepView) findViewById(R.id.step_view);
        stepView.done(false);
        nextBtn = (MaterialButton) findViewById(R.id.nextBtn);
        prevBtn = (MaterialButton) findViewById(R.id.prevBtn);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        position = 0;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0: {
                        if(fragmentInformation.validateData() == false){
                            break;
                        }
                        prevBtn.setVisibility(View.VISIBLE);
                        replaceFragmentContent(fragmentLocation);
                        position = 1;
                        stepView.done(false);
                        stepView.go(position, true);
                        break;
                    }
                    case 1: {
                        if(fragmentLocation.validateData() == false){
                            break;
                        }
                        replaceFragmentContent(fragmentUtilities);
                        position = 2;
                        stepView.done(false);
                        stepView.go(position, true);
                        break;
                    }
                    case 2: {
                        if(fragmentUtilities.validateData() == false){
                            break;
                        }
                        replaceFragmentContent(fragmentConfirm);
                        position = 3;
                        stepView.done(false);
                        stepView.go(position, true);

                        if(roomToEdit == null){
                            nextBtn.setIcon(getDrawable(R.drawable.ic_add));
                            nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                            nextBtn.setText("Đăng bài");
                        }
                        else{
                            nextBtn.setIcon(getDrawable(R.drawable.ic_edit));
                            nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                            nextBtn.setText("Cập nhật");
                        }
                        break;
                    }
                    default:{
                        if(fragmentConfirm.validateData() == false){
                            break;
                        }
                        pushRoomData();
                        break;
                    }
                }
                scrollView.setScrollY(0);
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 3: {
                        replaceFragmentContent(fragmentUtilities);
                        position = 2;
                        stepView.done(false);
                        stepView.go(position, true);
                        nextBtn.setText("Tiếp theo");
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        break;
                    }
                    case 2: {
                        replaceFragmentContent(fragmentLocation);
                        position = 1;
                        stepView.done(false);
                        stepView.go(position, true);
                        break;
                    }
                    case 1: {
                        replaceFragmentContent(fragmentInformation);
                        position = 0;
                        stepView.done(false);
                        stepView.go(position, true);
                        prevBtn.setVisibility(View.GONE);
                        break;
                    }
                    default:{
                        break;
                    }
                }
                scrollView.setScrollY(0);
            }
        });
        replaceFragmentContent(fragmentInformation);
    }

    private void pushRoomData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Rooms");
        DatabaseReference accRef = database.getReference("Accounts");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        Query query = accRef.orderByChild("email").equalTo(currentUser.getEmail());

        if(roomToEdit == null){
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(AccountClass.class);
                        String key = myRef.child("Rooms").push().getKey();
                        Date currentDate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                        Room room = new Room(key,
                                fragmentConfirm.getTitle(),
                                fragmentConfirm.getDescription(),
                                fragmentInformation.getRoomType(),
                                fragmentInformation.getCapacity(),
                                fragmentInformation.getGender(),
                                fragmentInformation.getArea(),
                                fragmentInformation.getPrice(),
                                fragmentInformation.getDeposit(),
                                fragmentInformation.getElectricityCost(),
                                fragmentInformation.getWaterCost(),
                                fragmentInformation.getInternetCost(),
                                fragmentInformation.hasParking(),
                                fragmentInformation.getParkingFee(),
                                fragmentLocation.getLocation(),
                                fragmentUtilities.getUtilities(),
                                user,
                                fragmentConfirm.getPhoneNumber(),
                                sdf.format(currentDate),
                                false,
                                STATUS_PENDING);

                    String pathObject = String.valueOf(room.getId());
                    myRef.child(pathObject).setValue(room);
                    updateImage(room);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // Tạo node PopularRegion


        DatabaseReference databaseReference = database.getReference("PopularRegion");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Chạy", "Hehehe");

                String path_with_type = fragmentLocation.getLocation().getDistrict().getPath_with_type();
                if (snapshot.getChildrenCount() == 0) {
                    Region region = new Region(
                            fragmentLocation.getLocation().getDistrict().getName_with_type(),
                            fragmentLocation.getLocation().getCity().getName_with_type(),
                            1
                    );
                    databaseReference.child(path_with_type).setValue(region);
                } else {
                    if (snapshot.hasChild(path_with_type)) {
                        Region region = snapshot.child(path_with_type).getValue(Region.class);
                        region.setSoLuong(region.getSoLuong() + 1);
                        databaseReference.child(path_with_type).setValue(region);
                    } else {
                        Region region = new Region(
                                fragmentLocation.getLocation().getDistrict().getName_with_type(),
                                fragmentLocation.getLocation().getCity().getName_with_type(),
                                1
                        );
                        databaseReference.child(path_with_type).setValue(region);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
            });
        }
        else{
            DatabaseReference editRef = database.getReference("Rooms").child(roomToEdit.getId());
            Room editedRoom = new Room(roomToEdit.getId(),
                    fragmentConfirm.getTitle(),
                    fragmentConfirm.getDescription(),
                    fragmentInformation.getRoomType(),
                    fragmentInformation.getCapacity(),
                    fragmentInformation.getGender(),
                    fragmentInformation.getArea(),
                    fragmentInformation.getPrice(),
                    fragmentInformation.getDeposit(),
                    fragmentInformation.getElectricityCost(),
                    fragmentInformation.getWaterCost(),
                    fragmentInformation.getInternetCost(),
                    fragmentInformation.hasParking(),
                    fragmentInformation.getParkingFee(),
                    fragmentLocation.getLocation(),
                    fragmentUtilities.getUtilities(),
                    roomToEdit.getCreatedBy(),
                    fragmentConfirm.getPhoneNumber(),
                    roomToEdit.getDateTime(),
                    roomToEdit.isRented(),
                    roomToEdit.getStatus());
            editRef.setValue(editedRoom);
            updateImage(editedRoom);
        }

    }



    protected void replaceFragmentContent(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fmgr = getSupportFragmentManager();

            FragmentTransaction ft = fmgr.beginTransaction();

            ft.replace(R.id.container_body, fragment);

            ft.commit();

        }
    }

    private void updateImage(Room room){
        List<String> uriImageStringList = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải lên...");
        progressDialog.show();
        OnUploadImageCompleteListener listener = new OnUploadImageCompleteListener() {
            @Override
            public void onUploadImageComplete() {
                room.setImages(uriImageStringList);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Rooms/" + room.getId() + "/images");
                myRef.setValue(uriImageStringList);
                notificationOnSuccess(progressDialog, room);

            }
        };
        storageReference = FirebaseStorage.getInstance().getReference();
        List<Uri> uriList = fragmentUtilities.getUriListImg();
        int totalItems = uriList.size();
        if(totalItems == 0){
            notificationOnSuccess(progressDialog, room);
        }
        final int[] successCount = {0};
        for(Uri u : uriList){
            Uri compressedImageUri;
            File actualImage = getFileOfUri(u);
            if(actualImage != null){
                try {
                    File compressedImageBitmap = new Compressor(this).compressToFile(actualImage);
                    compressedImageUri = Uri.fromFile(compressedImageBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(compressedImageUri == null){
                    continue;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.ENGLISH);
                Date now = new Date();
                String fileName = formatter.format(now);
                StorageReference roomImageRef = storageReference.child("Room Images/" + fileName);
                roomImageRef.putFile(compressedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        roomImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                uriImageStringList.add(uri.toString());
                                successCount[0]++;
                                if(successCount[0] == totalItems){
                                    listener.onUploadImageComplete();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityPost.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        return;
                    }
                });
            }
            else{
                uriImageStringList.add(u.toString());
                successCount[0]++;
                if(successCount[0] == totalItems){
                    listener.onUploadImageComplete();
                }
            }
        }
    }

    private void notificationOnSuccess(ProgressDialog progressDialog, Room room){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(roomToEdit == null){
            Toast.makeText(ActivityPost.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(ActivityPost.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(ActivityPost.this, ActivityDetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedRoom", room);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private File getFileOfUri(Uri u) {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.ENGLISH);
        Date now = new Date();
        String fileName = formatter.format(now);

        try {
            inputStream = contentResolver.openInputStream(u);
            file = new File(getCacheDir(), fileName);
            // Tạo tệp tin trong bộ nhớ cache của ứng dụng

            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> uri = new ArrayList<>();
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            if(data.getClipData() != null){
                //Chọn nhiều ảnh
                int countImages = data.getClipData().getItemCount();
                for(int i = 0; i<countImages; i++){
                    uri.add(data.getClipData().getItemAt(i).getUri());
                }

            } else if (data.getData() != null) {
                //Chọn 1 ảnh
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    uri.add(imageUri);
                }
            }
            fragmentUtilities.addImg(uri);
        }
        else{
            //Không chọn ảnh nào
            Toast.makeText(this, "Bạn không chọn hình ảnh nào", Toast.LENGTH_SHORT).show();
        }
    }
}

