package com.example.renthouse.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.renthouse.Fragment.FragmentAccountPosted;
import com.example.renthouse.Fragment.FragmentAccountTabAccount;

public class TabLayoutAccountAdapter extends FragmentStateAdapter {
    public TabLayoutAccountAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new FragmentAccountTabAccount();
            }
            case 1: {
                return new FragmentAccountPosted();
            }
        }
        return new FragmentAccountTabAccount();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
