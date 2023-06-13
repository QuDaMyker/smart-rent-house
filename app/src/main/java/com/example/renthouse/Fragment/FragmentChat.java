package com.example.renthouse.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.Chat.MemoryData;
import com.example.renthouse.Chat.Messages.MessagesAdapter;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentChat extends Fragment {
    private final List<MessagesList> messagesLists = new ArrayList<>();
    private String email;
    private String name;
    private RecyclerView messagesRecyclerView;
    private int unseenMessages = 0;
    private String lastMessages = "";
    private String chatKey = "";
    private boolean dataSet = false;
    private MessagesAdapter messagesAdapter;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String profileOther= "";
    String nameOther = "";
    String emailOthes = "";
    String userCurrent_Key ="";
    String userOtherCurrent_key = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        final CircleImageView userProfilePic = view.findViewById(R.id.userProfilePic);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        // get intent

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            email = currentUser.getEmail();
            name = currentUser.getDisplayName();
        }

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter to recycleview
        messagesAdapter = new MessagesAdapter(messagesLists, getContext());
        messagesRecyclerView.setAdapter(messagesAdapter);


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //  get profile pic from firebase database

        databaseReference.child("Accounts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.hasChild("email")) {
                        if (dataSnapshot.child("email").getValue(String.class).equals(currentUser.getEmail())) {
                            userCurrent_Key = dataSnapshot.getKey();
                            final String profilePicUrl = dataSnapshot.child("image").getValue(String.class);
                            if (!profilePicUrl.isEmpty()) {
                                Picasso.get().load(profilePicUrl).into(userProfilePic);
                            }
                            break;
                        }
                    }
                }
                //progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        databaseReference.child("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesLists.clear();
                unseenMessages = 0;
                lastMessages = "";
                chatKey ="";
                // tim userhienj tai
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if(dataSnapshot.getKey().equals(userCurrent_Key)) {
                        // chay vong lap tim cac user da nhan tin voi user hien tai
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            //Toast.makeText(getContext(), dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                            userOtherCurrent_key = dataSnapshot1.getKey();

                            // tim cac tin nhan giua 2 user
                            String lastIndex = dataSnapshot1.getChildrenCount()+"";
                            lastMessages = dataSnapshot1.child(lastIndex).child("msg").getValue(String.class);
                            //Toast.makeText(getContext(), lastMessages, Toast.LENGTH_SHORT).show();
                            databaseReference.child("Accounts").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    nameOther = snapshot.child(userOtherCurrent_key).child("fullname").getValue(String.class);
                                    emailOthes = snapshot.child(userOtherCurrent_key).child("email").getValue(String.class);
                                    profileOther = snapshot.child(userOtherCurrent_key).child("image").getValue(String.class);

                                    MessagesList messagesList = new MessagesList(userCurrent_Key,nameOther, emailOthes, lastMessages, profileOther, userOtherCurrent_key, 0);
                                    messagesLists.add(messagesList);
                                    messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, getContext()));
                                    messagesAdapter.updateData(messagesLists);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        break;
                    } else if(dataSnapshot.hasChild(userCurrent_Key+"")) {
                        userOtherCurrent_key = dataSnapshot.getKey();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            // tim cac tin nhan giua 2 user
                            String lastIndex = dataSnapshot1.getChildrenCount()+"";
                            lastMessages = dataSnapshot1.child(lastIndex).child("msg").getValue(String.class);
                            Toast.makeText(getContext(), lastMessages, Toast.LENGTH_SHORT).show();
                            databaseReference.child("Accounts").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    nameOther = snapshot.child(userOtherCurrent_key).child("fullname").getValue(String.class);
                                    emailOthes = snapshot.child(userOtherCurrent_key).child("email").getValue(String.class);
                                    profileOther = snapshot.child(userOtherCurrent_key).child("image").getValue(String.class);

                                    MessagesList messagesList = new MessagesList(userCurrent_Key,nameOther, emailOthes, lastMessages, profileOther, userOtherCurrent_key, 0);
                                    messagesLists.add(messagesList);
                                    messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, getContext()));
                                    messagesAdapter.updateData(messagesLists);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        break;
                    }
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });


        return view;
    }
}