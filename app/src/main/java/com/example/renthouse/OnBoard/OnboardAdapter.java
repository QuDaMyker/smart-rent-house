package com.example.renthouse.OnBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.renthouse.OOP.ItemOnboard;
import com.example.renthouse.OnBoard.FragmentOnBoard1;
import com.example.renthouse.OnBoard.FragmentOnBoard2;
import com.example.renthouse.OnBoard.FragmentOnBoard3;
import com.example.renthouse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OnboardAdapter extends FragmentStatePagerAdapter {


    public OnboardAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOnBoard1();
            case 1:
                return new FragmentOnBoard2();
            case 2:
                return new FragmentOnBoard3();
            default:
                return new FragmentOnBoard1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
