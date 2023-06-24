package com.example.renthouse.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;

import java.util.ArrayList;
import java.util.List;

public class ResultRoomAdapter extends RecyclerView.Adapter<ResultRoomAdapter.RoomViewHolder>{
    List<Room> roomList;
    Context mcContext;
    public ResultRoomAdapter(Context context) {
        this.mcContext = context;
    }
    public void setData(ArrayList<Room> data){
        this.roomList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room content = roomList.get(position);
        if (content == null) {
            return;
        }
        holder.checkboxLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (roomList != null) {
            return roomList.size();
        }
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAmountPeople;
        TextView textViewNameRoom;
        TextView textViewAddress;
        TextView textViewPriceRoom;
        ImageView imageViewRoom;
        CheckBox checkboxLike;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmountPeople = itemView.findViewById(R.id.textViewAmountPeople);
            textViewNameRoom = itemView.findViewById(R.id.textViewRoomName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPriceRoom = itemView.findViewById(R.id.textViewPrice);
            imageViewRoom = itemView.findViewById(R.id.imageViewImageRoom);
            checkboxLike = itemView.findViewById(R.id.likedCheckBox);
        }
    }
}
