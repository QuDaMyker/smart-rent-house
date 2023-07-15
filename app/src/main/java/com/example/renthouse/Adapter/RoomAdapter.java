package com.example.renthouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinPhong;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{

    private List<Room> rooms;
    private Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private int enableLikeButton = View.VISIBLE;
    private int minItemShowed = 0;

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

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String emaiCur = currentUser.getEmail();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference refAc = firebaseDatabase.getReference("Accounts");
        //xem p có phải là p đã thích k
        refAc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                String emailAc = null;
                for (DataSnapshot snapAc: snapshot.getChildren())
                {
                    emailAc = snapAc.child("email").getValue(String.class);
                    if (emaiCur.equals(emailAc)){
                        String idAc = snapAc.getKey();
                        DatabaseReference refLiked = firebaseDatabase.getReference("LikedRooms").child(idAc);
                        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(room.getId()))
                                {
                                    holder.cbLike.setChecked(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.cbLike.setVisibility(enableLikeButton);
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
                goToDetails(room);
            }
        });
        //cbliked thay đổi
        holder.cbLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu checkbox được chọn, them id phòng dzo ds Likedroom
                    String emailCur = currentUser.getEmail();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Accounts");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String emailAC = null;
                            for (DataSnapshot snapshotAc: snapshot.getChildren())
                            {
                                emailAC = snapshotAc.child("email").getValue(String.class);
                                if (emailCur.equals(emailAC)){
                                    String idAC = snapshotAc.getKey();
                                    DatabaseReference likedRef = firebaseDatabase.getReference("LikedRooms").child(idAC);
                                    String idRoom = room.getId();
                                    likedRef.child(idRoom).setValue(idRoom);

                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    // Nếu bỏ like thì xóa khỏi thích
                    String emailCur = currentUser.getEmail();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Accounts");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String emailAC = null;
                            for (DataSnapshot snapshotAc: snapshot.getChildren())
                            {
                                emailAC = snapshotAc.child("email").getValue(String.class);
                                if (emailCur.equals(emailAC)){
                                    String idAC = snapshotAc.getKey();
                                    DatabaseReference likedRef = firebaseDatabase.getReference("LikedRooms").child(idAC);
                                    String idRoom = room.getId();
                                    likedRef.child(idRoom).removeValue();
                                    //xóa khỏi ds hiện bên liked room
                                    rooms.remove(room);
                                    setLimit(rooms.size());
                                    notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private  void goToDetails (Room r)
    {
        //Intent intent = new Intent(context, ActivityDetails.class);
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
        if (minItemShowed > 0)
        {
            return minItemShowed;
        }
        return 0;
    }
    public void setLimit (int limit)
    {
        minItemShowed = limit;
        notifyDataSetChanged();
    }

    public void setEnableLikeButton(int enable) {
        enableLikeButton = enable;
        notifyDataSetChanged();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private CardView itemRooom;
        private ImageView ivRoom;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvPrice;

        private CheckBox cbLike;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemRooom = itemView.findViewById(R.id.itemRoom);
            ivRoom = itemView.findViewById(R.id.ivRoomImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cbLike = itemView.findViewById(R.id.cbLike);
        }
    }
}
