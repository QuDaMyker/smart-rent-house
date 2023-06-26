package com.example.renthouse.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.renthouse.Admin.Fragment.Admin_FragmentHome;
import com.example.renthouse.Admin.Fragment.Admin_FragmentNguoiDung;
import com.example.renthouse.Admin.Fragment.Admin_FragmentPhongTro;
import com.example.renthouse.Fragment.FragmentHome;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminMainBinding;

public class Admin_ActivityMain extends AppCompatActivity {
    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDefaultFragment();

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.btnHome: {
                    replaceFragment(new Admin_FragmentHome());
                    break;
                }
                case R.id.btnDSPhongTro: {
                    replaceFragment(new Admin_FragmentPhongTro());
                    break;
                }
                case R.id.btnUser: {
                    replaceFragment(new Admin_FragmentNguoiDung());
                    break;
                }
            }
            return true;
        });
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, new Admin_FragmentHome());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}