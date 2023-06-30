package com.example.renthouse.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultRoomAdapter extends RecyclerView.Adapter<ResultRoomAdapter.RoomViewHolder>{
    List<Room> roomList;
    public List<Room> roomListFavorite;
    Context mcContext;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
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
    public void onBindViewHolder(@NonNull RoomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (isShimmerEnabled) {
            holder.shimmerFrameLayout.startShimmerAnimation();
        } else {
            holder.shimmerFrameLayout.stopShimmerAnimation();
            Room room = roomList.get(position);
            if (room == null) {
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
                        //updateDataFirebase(roomList.get(position));
                        notifyDataSetChanged();
                    } else {
                        if (roomListFavorite.contains(roomList.get(position))){
                            roomListFavorite.remove(roomList.get(position));
                            // Remove dữ liệu khỏi firebase
                            notifyDataSetChanged();
                        }
                    }
                }
            });
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện click vào item trong recycleview (tức đã xem) :D
                    addSeenRoom(room);
                    //onClickGoDetail(room);
                }
            });
        }

    }

    private void addSeenRoom(Room room) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accRef = database.getReference("Accounts");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //DatabaseReference roomRef = database.getReference("Rooms");
        Query query = accRef.orderByChild("email").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Lấy được id của user
                    String idUser = dataSnapshot.getKey();

                    // Tạo node SeenRoom và add node idUser vô
                    DatabaseReference refLiked = database.getReference("SeenRoom").child(idUser);
                    refLiked.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() == 0) {
                                List<String> listRoom = new ArrayList<>();
                                listRoom.add(room.getId());
                                refLiked.setValue(listRoom);
                            } else {
                                Object obj= String.valueOf(snapshot.getValue());
                                // Convert obj to a string representation
                                String objString = String.valueOf(obj);

                                // Remove leading and trailing whitespace and brackets
                                String elementsString = objString.trim().substring(1, objString.length() - 1);

                                // Split into individual elements
                                String[] elementsArray = elementsString.split(",");

                                // Create a new ArrayList<String> and add elements
                                List<String> arrayList = new ArrayList<>(Arrays.asList(elementsArray));

                                if (arrayList.contains(room.getId())) {
                                    arrayList.remove(room.getId());

                                }
                                arrayList.add(0, room.getId());
                                refLiked.setValue(arrayList);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClickGoDetail(Room room) {
        Intent intent = new Intent(mcContext, ActivityDetails.class);
        Bundle bundle  = new Bundle();
        bundle.putSerializable("selectedRoom", room);
        intent.putExtras(bundle);
        mcContext.startActivity(intent);
    }
    private void updateDataFirebase(Room room) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accRef = database.getReference("Accounts");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Query query = accRef.orderByChild("email").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("Hhehehe", String.valueOf(dataSnapshot.getKey()));
                    AccountClass accountClass = dataSnapshot.getValue(AccountClass.class);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        LinearLayout layoutItem;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmountPeople = itemView.findViewById(R.id.textViewAmountPeople);
            textViewNameRoom = itemView.findViewById(R.id.textViewRoomName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPriceRoom = itemView.findViewById(R.id.textViewPrice);
            imageViewRoom = itemView.findViewById(R.id.imageViewImageRoom);
            checkboxLike = itemView.findViewById(R.id.likedCheckBox);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout);
            layoutItem = itemView.findViewById(R.id.item_result_room);
        }
    }
}
