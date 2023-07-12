package com.example.renthouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.datatransport.runtime.dagger.BindsInstance;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class OutstandingRoomAdapter extends RecyclerView.Adapter<OutstandingRoomAdapter.OutstandingRoomViewHolder> {
    private Context context;
    private List<Room> oustandingRoom;
    private final ItemClick itemClick;
    public OutstandingRoomAdapter(Context context, List<Room> oustandingRoom, ItemClick itemClick) {
        this.oustandingRoom = oustandingRoom;
        this.context = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public OutstandingRoomAdapter.OutstandingRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_outstanding_room, parent, false);
        return new OutstandingRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutstandingRoomAdapter.OutstandingRoomViewHolder holder, int position) {
        Room room = oustandingRoom.get(position);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Accounts");
        String curEmail = curUser.getEmail();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                String email = null;
                for (DataSnapshot Snapshot: snapshot.getChildren())
                {
                    email = Snapshot.child("email").getValue(String.class);
                    if (curEmail.equals(email)){
                        String id = Snapshot.getKey();
                        DatabaseReference refLiked = database.getReference("LikedRooms").child(id);
                        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(room.getId()))
                                {
                                    holder.checkBoxLiked.setChecked(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        holder.checkBoxLiked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String email = null;
                            for (DataSnapshot snapshotAc: snapshot.getChildren())
                            {
                                email = snapshotAc.child("email").getValue(String.class);
                                if (curEmail.equals(email)){
                                    String id = snapshotAc.getKey();
                                    DatabaseReference likedRef = database.getReference("LikedRooms").child(id);
                                    String idRoom = room.getId();
                                    likedRef.child(idRoom).setValue(room);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                } else {
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String email = null;
                            for (DataSnapshot Snapshot: snapshot.getChildren())
                            {
                                email = Snapshot.child("email").getValue(String.class);
                                if (curEmail.equals(email)){
                                    String idAC = Snapshot.getKey();
                                    DatabaseReference likedRef = database.getReference("LikedRooms").child(idAC);
                                    String idRoom = room.getId();
                                    likedRef.child(idRoom).removeValue();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
        });

        holder.textViewNameRoom.setText(room.getTitle());
        holder.textViewAddress.setText(room.getLocation().getWard().getPath());
        long price = (long)oustandingRoom.get(position).getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(price);

        String priceString = formattedNumber.replaceAll(",", ".");
        priceString += " đ/phòng";
        holder.textViewPrice.setText(priceString);

        String image = room.getImages().get(0);
        Picasso.get()
                .load(image)
                .into(holder.imageView);
        holder.itemRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetails(room);
            }
        });
    }

    private void goToDetails(Room room) {
        Intent intent = new Intent(context, ActivityDetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedRoom", room);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return oustandingRoom.size();
    }

    public class OutstandingRoomViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        MaterialCheckBox checkBoxLiked;
        TextView textViewNameRoom;
        TextView textViewPrice;
        TextView textViewAddress;

        LinearLayout itemRoom;

        public OutstandingRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewImageOutstandingRoom);
            checkBoxLiked = itemView.findViewById(R.id.likedCheckBoxOutstandingRoom);
            textViewAddress =itemView.findViewById(R.id.textViewAddressOutstandingRoom);
            textViewNameRoom=itemView.findViewById(R.id.textViewRoomNameOutstandingRoom);
            textViewPrice=itemView.findViewById(R.id.textViewPriceOutstandingRoom);
            itemRoom=itemView.findViewById(R.id.item_outstanding_room);
        }
    }

}
