package com.example.renthouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.renthouse.ITEM.itemPhoBien_HomeFragment;
import com.example.renthouse.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PhoBienAdapter extends RecyclerView.Adapter<PhoBienAdapter.ViewHolder> {
    private Context context;
    private List<itemPhoBien_HomeFragment> list;

    public PhoBienAdapter(Context context, List<itemPhoBien_HomeFragment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhoBienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phobien_homefragment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoBienAdapter.ViewHolder holder, int position) {
        itemPhoBien_HomeFragment item = list.get(position);
        Glide.with(context)
                .load(item.getImage())
                .into(holder.image);
        holder.location.setText(item.getLocation());
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView image;
        private TextView location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            location = itemView.findViewById(R.id.location);

        }
    }
}
