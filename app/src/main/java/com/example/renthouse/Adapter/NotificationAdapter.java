package com.example.renthouse.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.University;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> notificationList;

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
    }

    private OnNotificationClickListener clickListener;

    public void setOnNotificationClickListener(OnNotificationClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView dateTime;
        public ImageView notiImg;
        public ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            dateTime = itemView.findViewById(R.id.dateTime);
            notiImg = itemView.findViewById(R.id.notiImg);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification item = notificationList.get(position);
        holder.title.setText(item.getTitle());
        holder.dateTime.setText(item.getDateTime());

        if(item.isRead()){
            holder.parentLayout.setBackgroundColor(ContextCompat.getColor(holder.parentLayout.getContext(), R.color.white));
        }
        else {
            holder.parentLayout.setBackgroundColor(ContextCompat.getColor(holder.parentLayout.getContext(), R.color.Primary_98));
        }
        if(!TextUtils.isEmpty(item.getType())){
            if(item.getType().equals("room")){
                holder.notiImg.setImageResource(R.drawable.noti_room);
            }
            else if(item.getType().equals("chat")){
                holder.notiImg.setImageResource(R.drawable.noti_chat);
            }
            else if(item.getType().equals("like")){
                holder.notiImg.setImageResource(R.drawable.noti_like);
            }
            else if(item.getType().equals("schedule")){
                holder.notiImg.setImageResource(R.drawable.noti_schedule);
            }
            else{
                holder.notiImg.setImageResource(R.drawable.noti_system);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onNotificationClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
