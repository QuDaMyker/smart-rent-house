package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityLikedEmptyBinding;

public class ActivityLiked extends AppCompatActivity {

    ActivityLikedEmptyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikedEmptyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.navigation.setOnItemSelectedListener( item ->{

            switch (item.getItemId()) {
                case R.id.btnLiked:
                    replaceFragment(new LikedEmptyFragment());
                    break;
            }
            return true;
        });

    }
    private void replaceFragment (Fragment f){
        FragmentManager fn = getSupportFragmentManager();
        FragmentTransaction ft = fn.beginTransaction();
        ft.replace(R.id.frameLayout, f);
        ft.commit();
    }
}