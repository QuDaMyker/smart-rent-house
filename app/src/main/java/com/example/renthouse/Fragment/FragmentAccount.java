package com.example.renthouse.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class FragmentAccount extends Fragment {
    private FragmentAccountBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        getCurrentUser();

        binding.accountPersonalInfomationButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
            }
        });

        return view;
    }
    private void getCurrentUser() {
        if (currentUser != null) {
            String providerId = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            binding.accountPersonalEmailProfile.setText(email);
            binding.accountPersonalNameProfile.setText(displayName);
            Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_default_profile)
                    .error(R.drawable.back_ground_background)
                    .into(binding.accountPersonalImageProfile);
        }
    }
}