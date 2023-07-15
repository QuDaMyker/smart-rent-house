package com.example.renthouse.Chat.Dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Activity.BaseActivity;
import com.example.renthouse.Chat.MemoryData;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.Chat.OOP.Conversation;
import com.example.renthouse.FCM.SendNotificationTask;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityChatBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
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

public class ActivityChat extends BaseActivity {
    private ActivityChatBinding binding;
    private DatabaseReference databaseReference;
    private List<ChatList> chatLists;
    private List<ChatList> tempChatLists;
    private String otherKey;
    private String currentKey;
    private String getUserMobile = "";
    private int lastIndex;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        updateUI();
        loadMessages();
        setListeners();

    }


    private void init() {
        preferenceManager = new PreferenceManager(ActivityChat.this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        chatLists = new ArrayList<>();
        tempChatLists = new ArrayList<>();

        binding.chattingRecycleView.setHasFixedSize(true);
        binding.chattingRecycleView.setLayoutManager(new LinearLayoutManager(ActivityChat.this));

        chatAdapter = new ChatAdapter(chatLists, ActivityChat.this);
        binding.chattingRecycleView.setAdapter(chatAdapter);


    }

    private void updateUI() {
        // get data form messages adapter class
        currentKey = preferenceManager.getString(Constants.KEY_USER_KEY);
        final String getName = getIntent().getStringExtra("name");
        final String getEmail = getIntent().getStringExtra("email");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        otherKey = getIntent().getStringExtra("otherKey");


        // get user email from memory
        getUserMobile = MemoryData.getData(ActivityChat.this);
        binding.name.setText(getName);
        Picasso.get().load(getProfilePic).into(binding.profilePic);

        databaseReference.child("Conversations").child(currentKey).child(otherKey).child("seenMsg").setValue(true);
        databaseReference.child("Conversations").child(otherKey).child(currentKey).child("seenMsg").setValue(true);


    }

    private void loadMessages() {
        databaseReference.child("Chat").child(currentKey).child(otherKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastIndex = (int) snapshot.getChildrenCount();
                chatLists.clear();
                tempChatLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("sender").getValue(String.class).equals(currentKey)) {
                        String msg = dataSnapshot.child("msg").getValue(String.class);
                        String sendDate = dataSnapshot.child("send-date").getValue(String.class);
                        String sendTime = dataSnapshot.child("send-time").getValue(String.class);

                        ChatList chatList = new ChatList(true, "nguoi dung hien tai", "name", msg, sendDate, sendTime);
                        tempChatLists.add(chatList);

                    } else if (dataSnapshot.child("sender").getValue(String.class).equals(otherKey)) {
                        String msg = dataSnapshot.child("msg").getValue(String.class);
                        String sendDate = dataSnapshot.child("send-date").getValue(String.class);
                        String sendTime = dataSnapshot.child("send-time").getValue(String.class);

                        ChatList chatList = new ChatList(false, "nguoi dung hien tai", "name", msg, sendDate, sendTime);
                        tempChatLists.add(chatList);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatLists.addAll(tempChatLists);
                        chatAdapter.notifyDataSetChanged();
                    }
                });

                binding.chattingRecycleView.post(new Runnable() {
                    @Override
                    public void run() {

                        binding.chattingRecycleView.smoothScrollToPosition(chatLists.size() - 1);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setListeners() {
        binding.mButton1.setOnClickListener(v -> {
            binding.hintBar.setVisibility(View.INVISIBLE);

            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) binding.backView.getLayoutParams();
            layoutParams1.bottomToTop = R.id.messageEditTxt;

            binding.backView.setLayoutParams(layoutParams1);
        });
        binding.mButton2.setOnClickListener(v -> {
            binding.messageEditTxt.setText(binding.mButton2.getText());
        });
        binding.mButton3.setOnClickListener(v -> {
            binding.messageEditTxt.setText(binding.mButton3.getText());
        });
        binding.mButton4.setOnClickListener(v -> {
            binding.messageEditTxt.setText(binding.mButton4.getText());
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.messageEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.messageEditTxt.getText().toString().trim().isEmpty()) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                    SimpleDateFormat simpleTimeFormatConversaton = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                    final String getMessages = binding.messageEditTxt.getText().toString().trim();
                    lastIndex++;

                    // Set value cho người gửi
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("sender").setValue(currentKey);
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("msg").setValue(getMessages);
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("send-date").setValue(simpleDateFormat.format(date));
                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(String.valueOf(lastIndex)).child("send-time").setValue(simpleTimeFormat.format(date));

                    Conversation conversation = new Conversation(currentKey, otherKey, getMessages, simpleDateFormat.format(date), simpleTimeFormatConversaton.format(date), true);

                    databaseReference.child("Conversations").child(currentKey).child(otherKey).setValue(conversation);

                    // Set value cho người nhận
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("sender").setValue(currentKey);
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("msg").setValue(getMessages);
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("send-date").setValue(simpleDateFormat.format(date));
                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(String.valueOf(lastIndex)).child("send-time").setValue(simpleTimeFormat.format(date));

                    conversation = new Conversation(currentKey, otherKey, getMessages, simpleDateFormat.format(date), simpleTimeFormatConversaton.format(date), false);
                    databaseReference.child("Conversations").child(otherKey).child(currentKey).setValue(conversation);

                    binding.messageEditTxt.setText("");

                    Notification notification = new Notification("Bạn có một tin nhắn mới", "Kết nối với người thuê hoặc chủ nhà ngay bây giờ!", "chat");
                    notification.setAttachedMessageKey(conversation.getReceiveId() + "/" + conversation.getSendId());
                    SendNotificationTask task = new SendNotificationTask(ActivityChat.this, notification);
                    task.execute();
                }
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Thực hiện cập nhật dữ liệu ở đây
                loadMessages();
                // Sau khi hoàn thành cập nhật, gọi phương thức setRefreshing(false) để kết thúc hiệu ứng làm mới
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}