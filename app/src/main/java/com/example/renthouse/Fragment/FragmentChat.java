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
    ProgressDialog progressDialog;

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


        progressDialog = new ProgressDialog(getContext());
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
                List<String> userOtherCurrentKeys = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.child(userCurrent_Key).getChildren()) {
                    String userOtherCurrentKey = dataSnapshot.getKey();
                    userOtherCurrentKeys.add(userOtherCurrentKey);
                }

                fetchAccountDetails(userOtherCurrentKeys, 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
    private void fetchAccountDetails(final List<String> userOtherCurrentKeys, final int index) {
        if (index >= userOtherCurrentKeys.size()) {
            progressDialog.dismiss();
            return;
        }

        final String userOtherCurrentKey = userOtherCurrentKeys.get(index);

        databaseReference.child("Accounts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                nameOther = snapshot1.child(userOtherCurrentKey).child("fullname").getValue(String.class);
                profileOther = snapshot1.child(userOtherCurrentKey).child("image").getValue(String.class);
                emailOthes = snapshot1.child(userOtherCurrentKey).child("email").getValue(String.class);

                //Toast.makeText(getContext(), nameOther, Toast.LENGTH_SHORT).show();

                MessagesList messagesList = new MessagesList(userCurrent_Key, nameOther, emailOthes, lastMessages, profileOther, userOtherCurrentKey, 0);
                messagesLists.add(messagesList);
                messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, getContext()));

                fetchAccountDetails(userOtherCurrentKeys, index + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}