package com.example.renthouse.FCM;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.renthouse.OOP.Device;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.utilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendToOwnerTask extends AsyncTask<Void, Void, Void> {
    private Room room;
    private Context context;
    private String userId;

    public SendToOwnerTask(Context context, Room room, String userId){
        this.context = context;
        this.room = room;
        this.userId = userId;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Devices").orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    Device device = child.getValue(Device.class);
                    Notification notification;
                    if(room.getStatus().equals(Constants.STATUS_APPROVED)) {
                        notification = new Notification("Phòng trọ đã được duyệt!", "Chúc mừng! Phòng trọ của bạn đã được duyệt và sẵn sàng cho thuê. Hãy theo dõi tin nhắn thường xuyên nhé!", "system");
                        notification.setAttachedRoomKey(room.getId());
                        sendNoti(device, notification);
                    }
                    else if(room.getStatus().equals(Constants.STATUS_DELETED)){
                        notification = new Notification("Phòng trọ đã bị từ chối!", "Rất tiếc! Phòng trọ của bạn đã bị từ chối. Hãy kiểm tra chính sách đăng phòng và thử lại.", "system");
                        sendNoti(device, notification);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }

    public void sendNoti(Device device, Notification notification){
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
