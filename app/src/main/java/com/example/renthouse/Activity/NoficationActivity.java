package com.example.renthouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.NotificationAdapter;
import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.Chat.Dashboard.ActivityChat;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoficationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    List<Notification> notificationList = new ArrayList<>();
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofication);

        recyclerView = findViewById(R.id.recycleViewNoti);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("Notifications");
        DatabaseReference accountsRef = database.getReference("Accounts");
        accountsRef.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                    String accountKey = accountSnapshot.getKey();

                    userRef = notificationsRef.child(accountKey);
                    userRef.limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                                Notification notification = notificationSnapshot.getValue(Notification.class);
                                Notification modifiedNotification = new Notification(notification.getTitle(), notification.getBody(), notification.getType());
                                modifiedNotification.setAttachedRoom(notification.getAttachedRoom());
                                modifiedNotification.setAttachedMessage(notification.getAttachedMessage());
                                modifiedNotification.setDateTime(notification.getDateTime());
                                modifiedNotification.setRead(notification.isRead());
                                notificationList.add(modifiedNotification);

                            }

                            Collections.reverse(notificationList);

                            notificationAdapter = new NotificationAdapter(notificationList);
                            notificationAdapter.setOnNotificationClickListener(new NotificationAdapter.OnNotificationClickListener() {
                                @Override
                                public void onNotificationClick(Notification notification) {
                                    MessagesList messagesList = notification.getAttachedMessage();
                                    if(notification.getType().equals("chat") && messagesList != null){
                                        Intent intent = new Intent(NoficationActivity.this, ActivityChat.class);
                                        intent.putExtra("currentKey", messagesList.getCurrentKey());
                                        intent.putExtra("name", messagesList.getName());
                                        intent.putExtra("email", messagesList.getEmail());
                                        intent.putExtra("profile_pic", messagesList.getProfilePic());
                                        intent.putExtra("otherKey", messagesList.getOtherKey());
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(notificationAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                    Notification notification = notificationSnapshot.getValue(Notification.class);
                    notification.setRead(true);
                    notificationSnapshot.getRef().setValue(notification);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
