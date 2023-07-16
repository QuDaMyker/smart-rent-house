package com.example.renthouse.FCM;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Device;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendNotificationTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private Notification notification;
    private String ownerId;

    public SendNotificationTask(Context context, Notification notification) {
        this.context = context;
        this.notification = notification;
    }
    public SendNotificationTask(Context context, Notification notification, String ownerId) {
        this.context = context;
        this.notification = notification;
        this.ownerId = ownerId;
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
                    boolean flag = false;
                    if(device.isRoomNoti() && notification.getType().equals("room")){
                        if(ownerId.equals(device.getUserId())){
                            Log.e("id", ownerId);
                            flag = false;
                        }
                        else {
                            flag = true;
                        }
                    }
                    if(device.isChatNoti() && notification.getType().equals("chat")){
                        String[] parts = notification.getAttachedMessageKey().split("/");
                        String receiveId = parts[0];
                        String sendId = parts[1];
                        if(device.getUserId().equals(receiveId)){
                            flag = true;
                        }
                    }
                    if(device.isLikeNoti() && notification.getType().equals("like")){
                        flag = true;
                    }
                    if(device.isScheduleNoti() && notification.getType().equals("schedule")){
                        flag = true;
                    }

                    if(flag){
                        sendNoti(device);
                        Log.e("Token send", device.getToken());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error checking device token in Firebase", databaseError.toException());
            }
        });
        return null;
    }

    public void sendNoti(Device device){
        //send noti
        FCMSend.pushNotification(context, device.getToken(), notification.getTitle(), notification.getBody());
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        notification.setDateTime(sdf.format(currentDate));
        notification.setRead(false);

        //Save to db
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("Notifications");
        
        DatabaseReference userRef = notificationsRef.child(device.getUserId());
        DatabaseReference notificationRef = userRef.push();
        notificationRef.setValue(notification);
    }
}
