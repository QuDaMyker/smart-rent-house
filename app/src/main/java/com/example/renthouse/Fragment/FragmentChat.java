package com.example.renthouse.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private List<MessagesList> messagesLists;
    private List<MessagesList> tempMessagesLists;
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
        View view = binding.getRoot();


        init();
        loadData();
        setListeners();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void init() {
        messagesLists = new ArrayList<>();
        tempMessagesLists = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();
        preferenceManager = new PreferenceManager(getContext());

        mAuth = FirebaseAuth.getInstance();

        binding.messagesRecyclerView.setHasFixedSize(true);
        binding.messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messagesAdapter = new MessagesAdapter(messagesLists, getContext());
        binding.messagesRecyclerView.setAdapter(messagesAdapter);


    }

    private void loadData() {
        dialogListener.showDialog();

        messagesLists.clear();
        tempMessagesLists.clear();

        reference.child("Conversations").child(preferenceManager.getString(Constants.KEY_USER_KEY)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    messagesLists.clear();
                    tempMessagesLists.clear();

                    Log.d("count", snapshot.getChildrenCount() + "");

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String otherKey = dataSnapshot.getKey();

                        Query query = reference.child("Accounts").orderByKey().equalTo(otherKey);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()) {
                                    AccountClass accountClass = dataSnapshot1.getValue(AccountClass.class);

                                    String sendId = dataSnapshot.child("sendId").getValue(String.class);
                                    String lastMessage;
                                    if (sendId != null) {
                                        if (sendId.equals(preferenceManager.getString(Constants.KEY_USER_KEY))) {
                                            lastMessage = "Tôi: " + dataSnapshot.child("lastMessage").getValue(String.class);
                                        } else {
                                            lastMessage = dataSnapshot.child("lastMessage").getValue(String.class);
                                        }
                                        String time = dataSnapshot.child("sendDate").getValue(String.class) + " " + dataSnapshot.child("sendTime").getValue(String.class);

                                        Boolean seen = dataSnapshot.child("seenMsg").getValue(Boolean.class);
                                        Log.d("seen", seen + "");

                                        Log.d("thoigian", time);
                                        MessagesList messagesList = new MessagesList(
                                                preferenceManager.getString(Constants.KEY_USER_KEY),
                                                accountClass.getFullname(),
                                                accountClass.getEmail(),
                                                lastMessage,
                                                accountClass.getImage(),
                                                otherKey,
                                                seen,
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

                                            Activity activity = getActivity();
                                            if (activity != null) {
                                                activity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        messagesAdapter.notifyDataSetChanged();
                                                        dialogListener.dismissDialog();
                                                    }
                                                });
                                            }



                                        }

                                    } else {
                                        // Xử lý khi sendId là null

                                    }
                                }
                            }                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogListener.dismissDialog();
                            }
                        });
                    }

                    binding.animationView.setVisibility(View.INVISIBLE);
                    binding.messagesRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    binding.animationView.setVisibility(View.VISIBLE);
                    binding.messagesRecyclerView.setVisibility(View.INVISIBLE);
                }

                dialogListener.dismissDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogListener.dismissDialog();
            }
        });
    }

    private void setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Thực hiện cập nhật dữ liệu ở đây
                loadData();
                // Sau khi hoàn thành cập nhật, gọi phương thức setRefreshing(false) để kết thúc hiệu ứng làm mới
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}