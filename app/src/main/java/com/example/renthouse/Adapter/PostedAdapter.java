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

import com.example.renthouse.OOP.Posted;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.ViewHolder> {
    private Context context;
    private List<Room> list;

    public PostedAdapter(Context context, List<Room> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posted, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostedAdapter.ViewHolder holder, int position) {
        Room item = list.get(position);

        List<String> images = item.getImages();
        Picasso.get().load(images.get(0)).into(holder.image);

        holder.tvTitle.setText(item.getTitle());
        int price = item.getPrice();
        String s = String.valueOf(price);
        holder.tvPrice.setText(s);
        String address = item.getLocation().getAddress() + item.getLocation().getStreet() + item.getLocation().getDistrict().getPath_with_type();
        holder.tvAddress.setText(address);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when click open activity detail
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
