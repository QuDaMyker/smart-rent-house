package com.example.renthouse.Chat.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Chat.MemoryData;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.FCM.SendNotificationTask;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityChat extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<ChatList> chatLists = new ArrayList<>();
    private int generatedChatKey;
    private String otherKey;
    private String currentKey;
    String getUserMobile = "";
    int lastIndex;

    MessagesList messagesList;
    private RecyclerView chattingRecycleView;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final MaterialButton sendBtn = findViewById(R.id.sendBtn);
        final MaterialButton mButton1 = findViewById(R.id.mButton1);
        final MaterialButton mButton2 = findViewById(R.id.mButton2);
        final MaterialButton mButton3 = findViewById(R.id.mButton3);
        final MaterialButton mButton4 = findViewById(R.id.mButton4);
        final HorizontalScrollView hintBar = findViewById(R.id.hintBar);
        final View backView = findViewById(R.id.backView);

        mButton1.setOnClickListener(v -> {
            hintBar.setVisibility(View.INVISIBLE);


            /*ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) messageEditText.getLayoutParams();
            layoutParams.topToTop = R.id.chattingRecycleView;
            messageEditText.setLayoutParams(layoutParams);*/

            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) backView.getLayoutParams();
            layoutParams1.bottomToTop = R.id.messageEditTxt;
            backView.setLayoutParams(layoutParams1);




        });
        mButton2.setOnClickListener(v -> {
            messageEditText.setText(mButton2.getText());
        });
        mButton3.setOnClickListener(v -> {
            messageEditText.setText(mButton3.getText());
        });
        mButton4.setOnClickListener(v -> {
            messageEditText.setText(mButton4.getText());
        });


        chattingRecycleView = findViewById(R.id.chattingRecycleView);


        // get data form messages adapter class
        currentKey = getIntent().getStringExtra("currentKey");
        final String getName = getIntent().getStringExtra("name");
        final String getEmail = getIntent().getStringExtra("email");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        otherKey = getIntent().getStringExtra("otherKey");

        getMessageList();

        // get user email from memory
        getUserMobile = MemoryData.getData(ActivityChat.this);
        nameTV.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        chattingRecycleView.setHasFixedSize(true);
        chattingRecycleView.setLayoutManager(new LinearLayoutManager(ActivityChat.this));

        chatAdapter = new ChatAdapter(chatLists, ActivityChat.this);
        chattingRecycleView.setAdapter(chatAdapter);

        databaseReference.child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastIndex = Integer.valueOf((int) snapshot.child(currentKey).child(otherKey).getChildrenCount());
                chatLists.clear();
                //Toast.makeText(getApplicationContext(), snapshot.getKey(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot dataSnapshot : snapshot.child(currentKey).child(otherKey).getChildren()) {
                    // Toast.makeText(ActivityChat.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                    if (dataSnapshot.child("sender").getValue(String.class).equals(currentKey)) {
                        String msg = dataSnapshot.child("msg").getValue(String.class);
                        String sendDate = dataSnapshot.child("send-date").getValue(String.class);
                        String sendTime = dataSnapshot.child("send-time").getValue(String.class);

                        ChatList chatList = new ChatList(true, "nguoi dung hien tai", "name", msg, sendDate, sendTime);
                        chatLists.add(chatList);

                    } else if (dataSnapshot.child("sender").getValue(String.class).equals(otherKey)) {
                        String msg = dataSnapshot.child("msg").getValue(String.class);
                        String sendDate = dataSnapshot.child("send-date").getValue(String.class);
                        String sendTime = dataSnapshot.child("send-time").getValue(String.class);

                        ChatList chatList = new ChatList(false, "nguoi dung hien tai", "name", msg, sendDate, sendTime);
                        chatLists.add(chatList);
                    }
                    //lastIndex = Integer.valueOf(dataSnapshot.getKey());
                    chatAdapter.notifyDataSetChanged();
                    chattingRecycleView.post(new Runnable() {
                        @Override
                        public void run() {
                            chattingRecycleView.smoothScrollToPosition(chatLists.size() - 1);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageEditText.getText().toString().trim().isEmpty()) {
                    Date data = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                    final String getMessages = messageEditText.getText().toString().trim();
                    lastIndex++;

                    // Set value cho người gửi
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("sender").setValue(currentKey);
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("msg").setValue(getMessages);
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("send-date").setValue(simpleDateFormat.format(data));
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("send-time").setValue(simpleTimeFormat.format(data));

                    // Set value cho người nhận
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("sender").setValue(currentKey);
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("msg").setValue(getMessages);
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("send-date").setValue(simpleDateFormat.format(data));
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("send-time").setValue(simpleTimeFormat.format(data));

                    messageEditText.setText("");

                    Notification notification = new Notification("Bạn có một tin nhắn mới", "Kết nối với người thuê hoặc chủ nhà ngay bây giờ!", "chat");
                    notification.setAttachedMessage(messagesList);
                    SendNotificationTask task = new SendNotificationTask(ActivityChat.this, notification);
                    task.execute();
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getMessageList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Accounts").child(currentKey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountClass user = snapshot.getValue(AccountClass.class);
                messagesList = new MessagesList(otherKey, user.getFullname(), user.getEmail(), null, user.getImage(), currentKey, 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}