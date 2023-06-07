package com.example.renthouse.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentHome extends Fragment {
    FragmentHomeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        updateUI();

        binding.btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityPost.class));
            }
        });
        return view;
    }

    private void updateUI() {
        if (currentUser != null) {
            binding.tvXinchao.setText("Hi, " + currentUser.getDisplayName());
        }
    }
}