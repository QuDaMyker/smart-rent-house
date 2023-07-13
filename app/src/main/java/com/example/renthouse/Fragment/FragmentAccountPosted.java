package com.example.renthouse.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Adapter.PostedAdapter;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAccountPosted extends Fragment {
    private RecyclerView recyclerView;
    private List<Room> listRoomPosted;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog progressDialog;
    private PostedAdapter postedAdapter;
    private LottieAnimationView lottieAnimationView;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_posted, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.recycleView);

        lottieAnimationView = view.findViewById(R.id.animationView);

        preferenceManager = new PreferenceManager(getContext());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        listRoomPosted = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        postedAdapter = new PostedAdapter(getContext(), listRoomPosted, new ItemClick() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(getContext(), ActivityDetails.class);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("selectedRoom", room);
                intent.putExtras(bundle);
                startActivity(intent);*/

                intent.putExtra("selectedRoom", room);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postedAdapter);


        if (currentUser != null) {
            progressDialog.show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();

            Query query = reference.child("Rooms").orderByChild("createdBy/email").equalTo(preferenceManager.getString(Constants.KEY_EMAIL));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Room> tempRoom = new ArrayList<>();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Room room = snapshot.getValue(Room.class);
                        tempRoom.add(room);
                    }
                    listRoomPosted.addAll(tempRoom);
                    postedAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });

            /*reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listRoomPosted.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Room item = new Room();
                        String email = dataSnapshot.child("createdBy").child("email").getValue(String.class);
                        if (email.equals(currentUser.getEmail())) {
                            String id = dataSnapshot.child("id").getValue(String.class);
                            String title = dataSnapshot.child("title").getValue(String.class);
                            int price = dataSnapshot.child("price").getValue(Integer.class);
                            GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {
                            };
                            List<String> images = dataSnapshot.child("images").getValue(typeIndicator);
                            LocationTemp location = dataSnapshot.child("location").getValue(LocationTemp.class);
                            item.setId(id);
                            item.setImages(images);
                            item.setTitle(title);
                            item.setPrice(price);
                            item.setLocation(location);

                            listRoomPosted.add(item);
                        }
                    }
                    postedAdapter.notifyDataSetChanged();
                    if(listRoomPosted.isEmpty()) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
        }

        return view;
    }
}