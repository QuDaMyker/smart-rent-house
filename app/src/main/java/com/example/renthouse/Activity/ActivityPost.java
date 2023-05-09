package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.renthouse.Activity.FragmentPost.FragmentConfirm;
import com.example.renthouse.Activity.FragmentPost.FragmentInformation;
import com.example.renthouse.Activity.FragmentPost.FragmentLocation;
import com.example.renthouse.Activity.FragmentPost.FragmentUtilities;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.OOP.Ward;
import com.example.renthouse.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class ActivityPost extends AppCompatActivity {
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

    public interface OnUploadImageCompleteListener {
        void onUploadImageComplete();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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

                        nextBtn.setIcon(getDrawable(R.drawable.ic_add));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                        nextBtn.setText("Đăng bài");
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
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //HARDCODE, TODO: retrieve current user
        AccountClass user = new AccountClass("ABC",
                "abc@gmail.com",
                "0987654321",
                "12344",
                "a",
                "12-12-2023");

        String key = myRef.child("Rooms").push().getKey();
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
                fragmentConfirm.getPhoneNumber());

        String pathObject = String.valueOf(room.getId());
        myRef.child(pathObject).setValue(room);
        updateImage(room);
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
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(ActivityPost.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();
            }
        };
        storageReference = FirebaseStorage.getInstance().getReference();
        List<Uri> uriList = fragmentUtilities.getUriListImg();
        int totalItems = uriList.size();
        if(totalItems == 0){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Toast.makeText(ActivityPost.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();
            return;
        }
        final int[] successCount = {0};
        for(Uri u : uriList){
            Uri compressedImageUri;
            File actualImage = new File(u.getPath());
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
    }
}

