package com.example.renthouse.FCM;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.OOP.Device;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendNotificationTask extends AsyncTask<Void, Void, Void> {
    private Context context;

    public SendNotificationTask(Context context) {
        this.context = context;
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
                    FCMSend.pushNotification(context, device.getToken(), "Có phòng trọ mới vừa được đăng trên Rent House", "Hãy kiểm tra ngay để không bỏ lỡ cơ hội tuyệt vời này!");
                    Log.e("Token send", device.getToken());
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
