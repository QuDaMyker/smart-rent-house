package com.example.renthouse.Chat.Messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Chat.Dashboard.ActivityChat;
import com.example.renthouse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private List<MessagesList> messagesLists;
    private final Context context;

    public MessagesAdapter(List<MessagesList> messagesLists, Context context) {
        this.messagesLists = messagesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessagesList item = messagesLists.get(position);

        if (!item.getProfilePic().isEmpty()) {
            Picasso.get().load(item.getProfilePic()).into(holder.profilePic);
        }

        holder.name.setText(item.getName());
        holder.lastMessage.setText(item.getLastMessages());
        holder.timesent.setText(item.getSendTime());
        if(!item.isUnseenMessages()) {
            holder.lastMessage.setTextColor(Color.BLACK);
        } else  {
            holder.lastMessage.setTextColor(ContextCompat.getColor(context, R.color.Secondary_40));
        }



        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChat.class);
                intent.putExtra("currentKey", item.getCurrentKey());
                intent.putExtra("name", item.getName());
                intent.putExtra("email", item.getEmail());
                intent.putExtra("profile_pic", item.getProfilePic());
                intent.putExtra("otherKey", item.getOtherKey());
                context.startActivity(intent);
            }
        });


    }

    public void updateData(List<MessagesList> messagesLists) {
        this.messagesLists = messagesLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagesLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePic;
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessages;
        private LinearLayout rootLayout;
        private TextView timesent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            timesent = itemView.findViewById(R.id.timesent);

        }
    }
}
