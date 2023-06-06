package com.example.renthouse.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Adapter.ChatAdapter;
import com.example.renthouse.OOP.ChatMessage;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentChatHistory extends Fragment {
    RecyclerView chatRecyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_history, container, false);

        chatRecyclerView = (RecyclerView)view.findViewById(R.id.chatRecyclerView);
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("3", "1", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        Bitmap mIcon = BitmapFactory.decodeResource(this.getResources(),R.drawable.baseline_person_24);
        chatRecyclerView.setAdapter(new ChatAdapter(chatMessages, mIcon, "1"));

        return view;
    }
}