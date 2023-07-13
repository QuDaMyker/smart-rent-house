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
import com.example.renthouse.OOP.Posted;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ItemPostedBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

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
        return new ViewHolder(ItemPostedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        private ItemPostedBinding binding;

        public ViewHolder(ItemPostedBinding itemPostedBinding) {
            super(itemPostedBinding.getRoot());
            binding = itemPostedBinding;
        }

        void setData(Room room) {
            List<String> images = room.getImages();
            Picasso.get().load(images.get(0)).into(binding.image);
            binding.tvTitle.setText(room.getTitle()+"");
            binding.tvPrice.setText(room.getPrice()+"");
            String address = room.getLocation().getAddress() + room.getLocation().getStreet() + room.getLocation().getDistrict().getPath_with_type();
            binding.tvAddress.setText(address);
            binding.getRoot().setOnClickListener(v -> {
                Room roomIntent = room;
                itemClick.onItemClick(roomIntent);
            });

        }
    }
}
