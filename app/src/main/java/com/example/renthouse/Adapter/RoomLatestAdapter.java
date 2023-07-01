package com.example.renthouse.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RoomLatestAdapter extends RecyclerView.Adapter<RoomLatestAdapter.RoomLatestViewHolder> {
    List<Room> listRoomLatest;
    Context mContext;

    public RoomLatestAdapter(Context mContextm, List<Room> roomLatest) {
        this.mContext = mContext;
        this.listRoomLatest = roomLatest;
    }
    public void setDuLieu(List<Room> roomArrayList){
        this.listRoomLatest = roomArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomLatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_room, parent, false);
        return new RoomLatestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomLatestViewHolder holder, int position) {
        Room room = listRoomLatest.get(position);
        if(room == null) {
            return;
        }
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String emaiCur = currentUser.getEmail();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
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
                                    holder.checkBoxLiked.setChecked(true);
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
        holder.textViewAddress.setText(room.getLocation().getWard().getPath());
        holder.textViewNameRoom.setText(room.getTitle());
        long price = (long)listRoomLatest.get(position).getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(price);

        String priceString = formattedNumber.replaceAll(",", ".");
        priceString += " đ/phòng";
        holder.textViewPrice.setText(priceString);
        holder.checkBoxLiked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu checkbox được chọn, them dzo thích
                    String emailCur = currentUser.getEmail();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference("Accounts");
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
                                    likedRef.child(idRoom).setValue(room);
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
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference("Accounts");
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

    @Override
    public int getItemCount() {
        if (listRoomLatest.size() == 0) {
            return 0;
        }
        return listRoomLatest.size();
    }

    public class RoomLatestViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        MaterialCheckBox checkBoxLiked;
        TextView textViewNameRoom;
        TextView textViewPrice;
        TextView textViewAddress;
        public RoomLatestViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewImageRoomLatest);
            checkBoxLiked = itemView.findViewById(R.id.likedCheckBoxLatestRoom);
            textViewAddress =itemView.findViewById(R.id.textViewAddressLatestRoom);
            textViewNameRoom=itemView.findViewById(R.id.textViewRoomNameLatest);
            textViewPrice=itemView.findViewById(R.id.textViewPriceLatesRoom);
        }
    }
}
