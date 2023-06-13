package com.example.renthouse.Chat.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthouse.Chat.MemoryData;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        chattingRecycleView = findViewById(R.id.chattingRecycleView);


        // get data form messages adapter class
        currentKey = getIntent().getStringExtra("currentKey");
        final String getName = getIntent().getStringExtra("name");
        final String getEmail = getIntent().getStringExtra("email");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        otherKey = getIntent().getStringExtra("otherKey");

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
                // lay list tin nhan cua user dang nhap va user dang nhan
                chatLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentKey)) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                if (dataSnapshot2.child("sender").getValue(String.class).equals(currentKey)) {
                                    String msg = dataSnapshot2.child("msg").getValue(String.class);

                                    Date data = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                    ChatList chatList = new ChatList(true, "nguoi dang nhap hien tai", "name", msg, simpleDateFormat.format(data), simpleTimeFormat.format(data));
                                    chatLists.add(chatList);
                                } else {
                                    String msg = dataSnapshot2.child("msg").getValue(String.class);

                                    Date data = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                    ChatList chatList = new ChatList(false, "nguoi dang nhap hien tai", "name", msg, simpleDateFormat.format(data), simpleTimeFormat.format(data));
                                    chatLists.add(chatList);
                                }
                                lastIndex = Integer.valueOf(dataSnapshot2.getKey());
                            }
                            break;
                        }
                    } else if (dataSnapshot.getKey().equals(otherKey)) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if(dataSnapshot1.getKey().equals(currentKey)) {
                                for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    if (dataSnapshot2.child("sender").getValue(String.class).equals(currentKey)) {
                                        String msg = dataSnapshot2.child("msg").getValue(String.class);

                                        Date data = new Date();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                        ChatList chatList = new ChatList(true, "nguoi dang nhap hien tai", "name", msg, simpleDateFormat.format(data), simpleTimeFormat.format(data));
                                        chatLists.add(chatList);
                                    } else {
                                        String msg = dataSnapshot2.child("msg").getValue(String.class);

                                        Date data = new Date();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                        ChatList chatList = new ChatList(false, "nguoi dang nhap hien tai", "name", msg, simpleDateFormat.format(data), simpleTimeFormat.format(data));
                                        chatLists.add(chatList);
                                    }
                                    lastIndex = Integer.valueOf(dataSnapshot2.getKey());
                                }

                            }




                        }

                    }


                }
                chatAdapter.updateChatList(chatLists);

                chattingRecycleView.scrollToPosition(chatLists.size() - 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageEditText.getText().toString().isEmpty()) {

                    Date data = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                    final String getMessages = messageEditText.getText().toString().trim();
                    lastIndex++;
                    databaseReference.child("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(currentKey)) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    if (dataSnapshot.getKey().equals(currentKey)) {
                                        Toast.makeText(ActivityChat.this, currentKey, Toast.LENGTH_SHORT).show();
                                        databaseReference.child("Chat").child(currentKey).child(otherKey).child(lastIndex + "").child("sender").setValue(currentKey);
                                        databaseReference.child("Chat").child(currentKey).child(otherKey).child(lastIndex + "").child("msg").setValue(getMessages);
                                        messageEditText.setText("");
                                        break;
                                    }
                                }
                            } else {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if(dataSnapshot.hasChild(currentKey)) {

                                    }
                                }
                            }
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equals(currentKey)) {
                                    Toast.makeText(ActivityChat.this, currentKey, Toast.LENGTH_SHORT).show();
                                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(lastIndex + "").child("sender").setValue(currentKey);
                                    databaseReference.child("Chat").child(currentKey).child(otherKey).child(lastIndex + "").child("msg").setValue(getMessages);
                                    messageEditText.setText("");
                                } else {
                                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(lastIndex + "").child("sender").setValue(currentKey);
                                    databaseReference.child("Chat").child(otherKey).child(currentKey).child(lastIndex + "").child("msg").setValue(getMessages);
                                    messageEditText.setText("");
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
}