package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.renthouse.Fragment.FragmentAccount;
import com.example.renthouse.Fragment.FragmentChat;
import com.example.renthouse.Fragment.FragmentHome;
import com.example.renthouse.Fragment.FragmentLiked;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ActivityMain extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDefaultFragment();

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.btnHome) {
                replaceFragment(new FragmentHome());
            }else if(item.getItemId() == R.id.btnLiked) {
                replaceFragment(new FragmentLiked());
            }else if(item.getItemId() == R.id.btnChat) {
                replaceFragment(new FragmentChat());
            }else if(item.getItemId() == R.id.btnAccount) {
                replaceFragment(new FragmentAccount());
            }
            return true;
        });
    }
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new FragmentHome());
        fragmentTransaction.commit();
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}