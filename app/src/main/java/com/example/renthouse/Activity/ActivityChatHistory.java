package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.renthouse.Adapter.ChatAdapter;
import com.example.renthouse.OOP.ChatMessage;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityChatHistory extends AppCompatActivity {
    RecyclerView chatRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);
        chatRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("3", "1", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        chatMessages.add(new ChatMessage("1", "2", "Hello", "12:20"));
        Bitmap mIcon = BitmapFactory.decodeResource(this.getResources(),R.drawable.baseline_person_24);
        chatRecyclerView.setAdapter(new ChatAdapter(chatMessages, mIcon, "1"));
    }
}