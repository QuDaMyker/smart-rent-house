package com.example.renthouse.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.ChatMessage;
import com.example.renthouse.databinding.ItemReceivedMessageBinding;
import com.example.renthouse.databinding.ItemSentMessageBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatMessage> chatMessages;
    private final Bitmap receiveProfileImg;
    private final String sendId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;


    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiveProfileImg, String sendId) {
        this.chatMessages = chatMessages;
        this.receiveProfileImg = receiveProfileImg;
        this.sendId = sendId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(ItemSentMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),parent,false
            ));
        }
        else{
            return new ReceiveMessageViewHolder(ItemReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),parent,false
            ));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }
        else{
            ((ReceiveMessageViewHolder) holder).setData(chatMessages.get(position), receiveProfileImg);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).getSendId().equals((sendId))){
            return VIEW_TYPE_SENT;
        }
        else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemSentMessageBinding binding;
        SentMessageViewHolder(ItemSentMessageBinding itemSentMessageBinding){
            super(itemSentMessageBinding.getRoot());
            binding = itemSentMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.sentMessage.setText(chatMessage.getMessage());
            binding.textTime.setText(chatMessage.getDateTime());
        }
    }
    static class ReceiveMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemReceivedMessageBinding binding;
        ReceiveMessageViewHolder(ItemReceivedMessageBinding itemReceivedMessageBinding){
            super(itemReceivedMessageBinding.getRoot());
            binding = itemReceivedMessageBinding;
        }
        void setData(ChatMessage chatMessage, Bitmap receiveProfileImg){
            binding.receivedMessage.setText(chatMessage.getMessage());
            binding.textTime.setText(chatMessage.getDateTime());
            binding.imgProfile.setImageBitmap(receiveProfileImg);
        }
    }
}
