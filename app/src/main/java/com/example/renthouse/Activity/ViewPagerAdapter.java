package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.renthouse.FragmentFilter.FragmentAmount;
import com.example.renthouse.FragmentFilter.FragmentOptionOther;
import com.example.renthouse.FragmentFilter.FragmentPrice;
import com.example.renthouse.FragmentFilter.FragmentType;
import com.example.renthouse.FragmentFilter.FragmentUtilities;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new FragmentPrice();
            case 1:
                return new FragmentUtilities();
            case 2:
                return new FragmentType();
            case 3:
                return new FragmentAmount();
            case 4:
                return new FragmentOptionOther();
            default:
                return new FragmentPrice();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
