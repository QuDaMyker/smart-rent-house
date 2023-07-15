package com.example.renthouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Interface.ItemClick;

import com.example.renthouse.OOP.Room;

import com.example.renthouse.databinding.ItemLatestRoomBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.ViewHolder> {
    private Context context;
    private List<Room> list;
    private final ItemClick itemClick;

    public PostedAdapter(Context context, List<Room> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public PostedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLatestRoomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostedAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLatestRoomBinding binding;

        public ViewHolder(ItemLatestRoomBinding itemLatestRoomBinding) {
            super(itemLatestRoomBinding.getRoot());
            binding = itemLatestRoomBinding;
        }

        void setData(Room room) {
            List<String> images = room.getImages();
            Picasso.get().load(images.get(0)).into(binding.imageViewImageRoomLatest);
            binding.textViewRoomNameLatest.setText(room.getTitle() + "");
            int price = room.getPrice();
            binding.textViewPriceLatesRoom.setText(room.getPrice() + " VND/ Tháng");
            String address = "Số " + room.getLocation().getAddress() + " đường " + room.getLocation().getStreet() + room.getLocation().getDistrict().getPath_with_type();
            binding.textViewAddressLatestRoom.setText(address);
            binding.getRoot().setOnClickListener(v -> {
                Room roomIntent = room;
                itemClick.onItemClick(roomIntent);
            });

        }

        public String convertCurrency(int amount) {
            if (amount < 1000000) {
                return String.valueOf(amount);
            } else {
                double convertedAmount = amount / 1000000.0;
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setGroupingSeparator('.');
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##", symbols);
                return decimalFormat.format(convertedAmount) + " triệu";
            }
        }

    }
}
