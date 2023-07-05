package com.example.renthouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.PhoBien;
import com.example.renthouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoBienAdapter extends RecyclerView.Adapter<PhoBienAdapter.PhoBienViewHolder> {
    private Context context;
    private List<PhoBien> phoBienList;
    private final ItemClick itemClick;

    public PhoBienAdapter(Context context, List<PhoBien> phoBienList, ItemClick itemClick) {
        this.context = context;
        this.phoBienList = phoBienList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public PhoBienAdapter.PhoBienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phobien_homefragment, parent, false);
        return new PhoBienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoBienAdapter.PhoBienViewHolder holder, int position) {
        PhoBien phoBien = phoBienList.get(position);

        holder.title.setText(phoBien.getTitle());
        String image = phoBien.getImage();
        Picasso.get()
                .load(image)
                .into(holder.imageView);
        holder.itemPhoBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListPhoBien(phoBien);
            }
        });
    }

    private void goToListPhoBien(PhoBien phoBien){ }

    @Override
    public int getItemCount() {
        return phoBienList.size();
    }

    public class PhoBienViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        LinearLayout itemPhoBien;
        public PhoBienViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.location);
            itemPhoBien = itemView.findViewById(R.id.cardView);
        }
    }
}
