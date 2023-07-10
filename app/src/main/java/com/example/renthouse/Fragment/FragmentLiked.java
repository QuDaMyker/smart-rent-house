package com.example.renthouse.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.FragmentFilter.FragmentLikedRooms;
import com.example.renthouse.FragmentFilter.LikedEmptyFragment;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentLikedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentLiked extends Fragment {
    private RecyclerView recyclerView;
    FirebaseDatabase db;
    boolean isEmpty = true;

    private FragmentLikedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLikedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        //checkListLikedRooms();
        replaceFragment(new FragmentLikedRooms());
        return view;
    }
    private void checkListLikedRooms() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        String emaiCur = currentUser.getEmail();
        DatabaseReference refAc = db.getReference("Accounts");
        refAc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailAc = null;
                for (DataSnapshot snapAc : snapshot.getChildren()) {
                    emailAc = snapAc.child("email").getValue(String.class);
                    if (emaiCur.equals(emailAc)) {
                        String idAc = snapAc.getKey();
                        DatabaseReference refLiked = db.getReference("LikedRooms");
                        refLiked.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(idAc)) {
                                    isEmpty = false;
                                    replaceFragment(new FragmentLikedRooms());
                                } else {
                                    isEmpty = true;
                                    replaceFragment(new LikedEmptyFragment());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý lỗi nếu cần thiết
                            }
                        });
                        break; // Thoát khỏi vòng lặp sau khi tìm thấy email tương ứng
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần thiết
            }
        });
    }


    private void replaceFragment(Fragment f)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, f);
        fragmentTransaction.commit();
    }
}