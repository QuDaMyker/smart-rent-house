package com.example.renthouse.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityLikedBinding;

public class ActivityLiked extends AppCompatActivity {

    ActivityLikedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(item -> {

           switch (item.getItemId()){
               case R.id.btnLiked:
                   replaceFragment(new LikedEmptyFragment());

                   break;
           }

            return true;
        });
    }
    private void replaceFragment(Fragment f)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, f);
        fragmentTransaction.commit();
    }
}