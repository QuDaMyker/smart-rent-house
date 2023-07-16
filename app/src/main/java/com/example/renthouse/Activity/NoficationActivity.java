package com.example.renthouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.NotificationAdapter;
import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.Chat.Dashboard.ActivityChat;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.Chat.OOP.Conversation;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoficationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    List<Notification> notificationList = new ArrayList<>();
    DatabaseReference userRef;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofication);

        recyclerView = findViewById(R.id.recycleViewNoti);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.btn_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
                                modifiedNotification.setAttachedRoomKey(notification.getAttachedRoomKey());
                                modifiedNotification.setAttachedMessageKey(notification.getAttachedMessageKey());
                                modifiedNotification.setDateTime(notification.getDateTime());
                                modifiedNotification.setRead(notification.isRead());
                                notificationList.add(modifiedNotification);

                            }

                            Collections.reverse(notificationList);

                            notificationAdapter = new NotificationAdapter(notificationList);
                            notificationAdapter.setOnNotificationClickListener(new NotificationAdapter.OnNotificationClickListener() {
                                @Override
                                public void onNotificationClick(Notification notification) {
                                    String messagesKey = notification.getAttachedMessageKey();
                                    String roomKey = notification.getAttachedRoomKey();

                                    if(notification.getType().equals("chat")){
                                        String[] parts = messagesKey.split("/");
                                        String receiveId = parts[0];
                                        String sendId = parts[1];
                                        database.getReference("Accounts").child(sendId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                AccountClass user = snapshot.getValue(AccountClass.class);
                                                Intent intent = new Intent(NoficationActivity.this, ActivityChat.class);
                                                intent.putExtra("currentKey", receiveId);
                                                intent.putExtra("name", user.getFullname());
                                                intent.putExtra("email", user.getEmail());
                                                intent.putExtra("profile_pic", user.getImage());
                                                intent.putExtra("otherKey", sendId);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                    else if(notification.getType().equals("room")){
                                        database.getReference("Rooms").child(roomKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    Room room = snapshot.getValue(Room.class);
                                                    Intent intent = new Intent(NoficationActivity.this, ActivityDetails.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("selectedRoom", room);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                    else if(notification.getType().equals("system") && notification.getAttachedRoomKey() != null){
                                        database.getReference("Rooms").child(roomKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    Room room = snapshot.getValue(Room.class);
                                                    Intent intent = new Intent(NoficationActivity.this, ActivityDetails.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("selectedRoom", room);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
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
