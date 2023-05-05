package com.example.renthouse.Test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{

    private List<Room> likedRoom;

    public RoomAdapter(List<Room> likedRoom) {
        this.likedRoom = likedRoom;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_rooms, parent, false);

        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = likedRoom.get(position);
        if( room == null)
        {
            return;
        }
        holder.imagePhong.setImageResource(room.getSourceImage());
        holder.tvName.setText(room.getName());
        int Gia = room.getPrice()/1000000;
        String Gia_s = Integer.toString(Gia);
        holder.tvTien.setText(Gia_s + " triệu VND/Tháng");
        holder.tvDiaChi.setText(room.getAddress());
    }

    @Override
    public int getItemCount() {
        if (likedRoom != null) {
            return likedRoom.size();
        }
        return 0;
    }

    public  class RoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagePhong;
        private TextView tvName;
        private TextView tvTien;
        private TextView tvDiaChi;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhong = itemView.findViewById(R.id.imgRoom);
            tvName = itemView.findViewById(R.id.tvNameRoom);
            tvTien= itemView.findViewById(R.id.tvMoney);
            tvDiaChi = itemView.findViewById(R.id.tvDc);
        }
    }
}
