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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultRoomAdapter extends RecyclerView.Adapter<ResultRoomAdapter.RoomViewHolder>{
    List<Room> roomList;
    public List<Room> roomListFavorite;
    Context mcContext;
    public boolean isShimmerEnabled = true;
    public ResultRoomAdapter(ArrayList<Room> list, Context context) {
        this.roomList = list;
        this.mcContext = context;
        this.roomListFavorite = new ArrayList<>();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_result_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        if (isShimmerEnabled) {
            holder.shimmerFrameLayout.startShimmerAnimation();
        } else {
            holder.shimmerFrameLayout.stopShimmerAnimation();
            Room content = roomList.get(position);
            if (content == null) {
                return;
            }
            holder.textViewAmountPeople.setText("Số người: " + roomList.get(position).getCapacity());
            holder.textViewAmountPeople.setBackground(null);

            holder.textViewNameRoom.setText("Tên phòng trọ: " + roomList.get(position).getTitle());
            holder.textViewNameRoom.setBackground(null);

            holder.textViewAddress.setText("Địa chỉ phòng trọ: " + roomList.get(position).getLocation().getWard().getPath_with_type());
            holder.textViewAddress.setBackground(null);

            long price = (long)roomList.get(position).getPrice();
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedNumber = decimalFormat.format(price);

            String priceString = formattedNumber.replaceAll(",", ".");
            priceString += " đ/phòng";

            holder.textViewPriceRoom.setText(priceString);
            holder.textViewPriceRoom.setBackground(null);

            holder.checkboxLike.setVisibility(View.VISIBLE);

            holder.imageViewRoom.setBackground(null);
            holder.imageViewRoom.setImageResource(R.drawable.image_room_result);

            holder.checkboxLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        roomListFavorite.add(roomList.get(position));
                        notifyDataSetChanged();
                    } else {
                        if (roomListFavorite.contains(roomList.get(position))){
                            roomListFavorite.remove(roomList.get(position));
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (roomList != null) {
            return isShimmerEnabled  ? 10 : roomList.size();
        }
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAmountPeople;
        TextView textViewNameRoom;
        TextView textViewAddress;
        TextView textViewPriceRoom;
        ImageView imageViewRoom;
        MaterialCheckBox checkboxLike;
        ShimmerFrameLayout shimmerFrameLayout;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmountPeople = itemView.findViewById(R.id.textViewAmountPeople);
            textViewNameRoom = itemView.findViewById(R.id.textViewRoomName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPriceRoom = itemView.findViewById(R.id.textViewPrice);
            imageViewRoom = itemView.findViewById(R.id.imageViewImageRoom);
            checkboxLike = itemView.findViewById(R.id.likedCheckBox);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout);
        }
    }
}
