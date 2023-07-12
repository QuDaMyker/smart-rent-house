package com.example.renthouse.FCM;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.renthouse.Admin.OOP.NotiSchedule;
import com.example.renthouse.OOP.Device;
import com.example.renthouse.OOP.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Admin_SendNotificationTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String title;
    private String content;

    public Admin_SendNotificationTask(Context context, String title, String content) {
        this.context = context;
        this.title = title;
        this.content = content;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference devicesRef = database.getReference().child("Devices");

        devicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deviceSnapshot : dataSnapshot.getChildren()) {
                    Device device = deviceSnapshot.getValue(Device.class);
                    //TODO: check receiver
                    FCMSend.pushNotification(context, device.getToken(), title, content);
                    Log.e("Token sent", device.getToken());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error checking device token in Firebase", databaseError.toException());
            }
        });
        return null;
    }
}
