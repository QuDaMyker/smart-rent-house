package com.example.renthouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{

    private List<Room> rooms;
    private Context context;

    public RoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);

        return  new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        if (room == null)
        {
            return;
        }

        holder.tvName.setText(room.getTitle());
        holder.tvAddress.setText(room.getLocation().LocationToString());
        holder.tvPrice.setText(String.valueOf(room.getPrice()/1000000) + " Tr");
        String image = room.getImages().get(0);

        Picasso.get()
                .load(image)
                .into(holder.ivRoom);
        holder.itemRooom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetails (room);
            }
        });

    }
    private  void goToDetails (Room r)
    {
        Intent intent = new Intent(context, ActivityDetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedRoom", r);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void release (){
        context = null;
    }


    @Override
    public int getItemCount() {
        if (rooms != null)
        {
            return rooms.size();
        }
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private CardView itemRooom;
        private ImageView ivRoom;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvPrice;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemRooom = itemView.findViewById(R.id.itemRoom);
            ivRoom = itemView.findViewById(R.id.ivRoomImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
