package com.example.renthouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Activity.ActivityPhoBien;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.Interface.ItemClickPhoBien;
import com.example.renthouse.OOP.PhoBien;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoBienAdapter extends RecyclerView.Adapter<PhoBienAdapter.PhoBienViewHolder> {

    private Context context;
    private List<PhoBien> phoBienList;
    //private  final ItemClickPhoBien itemClick;

    public PhoBienAdapter(Context context, List<PhoBien> phoBienList) {
        this.context = context;
        this.phoBienList = phoBienList;
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
        holder.name.setText(phoBien.getName());

        String idimage = phoBien.getImage();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ImagePhoBien");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Snapshot: snapshot.getChildren()){
                    if (Snapshot.getKey().equals(idimage)){
                        String image = Snapshot.getValue(String.class);
                        Picasso.get().load(image).into(holder.imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void goToPhoBien(PhoBien phoBien) {
//        Intent intent = new Intent(context, ActivityPhoBien.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("selectedPhoBien", phoBien);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return phoBienList.size();
    }

    public class PhoBienViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        LinearLayout item;

        public PhoBienViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.location);
            //item = itemView.findViewById(R.id.cardView);
        }
    }
}