package com.example.renthouse.Fragment;

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

import com.example.renthouse.Adapter.PostedAdapter;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAccountPosted extends Fragment {
    private RecyclerView recyclerView;
    private List<Room> listRoomPosted;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressBar progressBar;
    private PostedAdapter postedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_posted, container, false);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.recycleView);


        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        listRoomPosted = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        postedAdapter = new PostedAdapter(getContext(), listRoomPosted);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postedAdapter);


        if (currentUser != null) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Rooms");


            reference.addValueEventListener(new ValueEventListener() {
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
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return view;
    }
}