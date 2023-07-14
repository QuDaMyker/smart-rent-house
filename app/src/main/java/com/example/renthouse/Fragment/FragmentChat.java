package com.example.renthouse.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.Activity.ActivityMain;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Chat.MemoryData;
import com.example.renthouse.Chat.Messages.MessagesAdapter;
import com.example.renthouse.Chat.Messages.MessagesList;
import com.example.renthouse.Interface.DialogListener;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentChatBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentChat extends Fragment {
    private FragmentChatBinding binding;
    private final List<MessagesList> messagesLists = new ArrayList<>();
    private final List<MessagesList> tempMessagesLists = new ArrayList<>();
    private MessagesAdapter messagesAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private PreferenceManager preferenceManager;
    private DialogListener dialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        View view = binding.getRoot();


        // get intent

        reference = FirebaseDatabase.getInstance().getReference();
        preferenceManager = new PreferenceManager(getContext());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        binding.messagesRecyclerView.setHasFixedSize(true);
        binding.messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter to recycleview
        messagesAdapter = new MessagesAdapter(messagesLists, getContext());
        binding.messagesRecyclerView.setAdapter(messagesAdapter);


        dialogListener.showDialog();

        reference.child("Conversations").child(preferenceManager.getString(Constants.KEY_USER_KEY)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    messagesLists.clear();
                    tempMessagesLists.clear();
                    Log.d("count", snapshot.getChildrenCount() + "");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String otherKey = dataSnapshot.getKey();
                        String sendId = dataSnapshot.child("sendId").getValue(String.class);
                        String lastMessage;
                        if (sendId.equals(preferenceManager.getString(Constants.KEY_USER_KEY))) {
                            lastMessage = "Tôi: " + dataSnapshot.child("lastMessage").getValue(String.class);
                        } else {
                            lastMessage = dataSnapshot.child("lastMessage").getValue(String.class);
                        }

                        String time = dataSnapshot.child("sendDate").getValue(String.class) + " " + dataSnapshot.child("sendTime").getValue(String.class);

                        Log.d("thoigian", time);

                        Query query = reference.child("Accounts").orderByKey().equalTo(otherKey);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()) {
                                    AccountClass accountClass = dataSnapshot1.getValue(AccountClass.class);


                                    MessagesList messagesList = new MessagesList(
                                            preferenceManager.getString(Constants.KEY_USER_KEY),
                                            accountClass.getFullname(),
                                            accountClass.getEmail(),
                                            lastMessage,
                                            accountClass.getImage(),
                                            otherKey,
                                            0,
                                            time
                                    );
                                    tempMessagesLists.add(messagesList);
                                    if (tempMessagesLists.size() == snapshot.getChildrenCount()) {
                                        messagesLists.addAll(tempMessagesLists);

                                        Collections.sort(messagesLists, new Comparator<MessagesList>() {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                            @Override
                                            public int compare(MessagesList obj1, MessagesList obj2) {

                                                try {
                                                    Date date1 = dateFormat.parse(obj2.getSendTime());
                                                    Date date2 = dateFormat.parse(obj1.getSendTime());
                                                    return date1.compareTo(date2);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                return 0;
                                            }
                                        });
                                        messagesAdapter.notifyDataSetChanged();
                                        //progressDialog.dismiss();
                                        dialogListener.dismissDialog();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogListener.dismissDialog();
                            }
                        });
                    }
                } else {
                    binding.animationView.setVisibility(View.VISIBLE);
                    binding.messagesRecyclerView.setVisibility(View.VISIBLE);
                }

                dialogListener.dismissDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();
            }
        });


        return view;
    }


}