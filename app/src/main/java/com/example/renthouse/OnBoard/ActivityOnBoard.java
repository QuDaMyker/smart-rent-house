package com.example.renthouse.OnBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.renthouse.Activity.ActivityLogIn;
import com.example.renthouse.databinding.ActivityOnBoardBinding;

public class ActivityOnBoard extends AppCompatActivity {
    private ActivityOnBoardBinding binding;
    private OnboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {
        adapter = new OnboardAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager(binding.viewPager);

    }

    private void setListeners() {
        binding.btnBoQua.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(2);
            binding.btnNext.setText("Bắt đầu");
            binding.btnBoQua.setVisibility(View.INVISIBLE);
        });

        binding.btnNext.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() < 2) {
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                if (binding.viewPager.getCurrentItem() == 2) {
                    binding.btnNext.setText("Bắt đầu");
                }
            } else {
                Intent intent = new Intent(ActivityOnBoard.this, ActivityLogIn.class);
                startActivity(intent);
                finish();
            }
        });

    }
}