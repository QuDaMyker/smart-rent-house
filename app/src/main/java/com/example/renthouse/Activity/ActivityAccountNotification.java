package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.renthouse.FCM.TokenUpdateTask;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Device;
import com.example.renthouse.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityAccountNotification extends AppCompatActivity {

    MaterialSwitch roomNoti;
    MaterialSwitch chatNoti;
    MaterialSwitch likeNoti;
    MaterialSwitch scheduleNoti;

    String deviceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_notification);

        roomNoti = findViewById(R.id.roomNoti);
        chatNoti = findViewById(R.id.chatNoti);
        likeNoti = findViewById(R.id.likeNoti);
        scheduleNoti = findViewById(R.id.scheduleNoti);

        DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference().child("Devices");
        devicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deviceSnapshot : dataSnapshot.getChildren()) {
                    Device device = deviceSnapshot.getValue(Device.class);
                    deviceKey = deviceSnapshot.getKey();

                    if (device.getUser().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        roomNoti.setChecked(device.isRoomNoti());
                        chatNoti.setChecked(device.isChatNoti());
                        likeNoti.setChecked(device.isLikeNoti());
                        scheduleNoti.setChecked(device.isScheduleNoti());
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error checking device in Firebase", databaseError.toException());
            }
        });

        roomNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference().child("Devices").child(deviceKey);
                devicesRef.child("roomNoti").setValue(b);
            }
        });

        chatNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference().child("Devices").child(deviceKey);
                devicesRef.child("chatNoti").setValue(b);
            }
        });

        likeNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference().child("Devices").child(deviceKey);
                devicesRef.child("likeNoti").setValue(b);
            }
        });

        scheduleNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference().child("Devices").child(deviceKey);
                devicesRef.child("scheduleNoti").setValue(b);
            }
        });
    }
}