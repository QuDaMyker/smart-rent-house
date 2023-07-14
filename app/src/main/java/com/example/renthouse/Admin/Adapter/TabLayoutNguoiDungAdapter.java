package com.example.renthouse.Admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.renthouse.Admin.Fragment_NguoiDung.AdminNguoiDung_FragmentDanhSachNguoiDung;
import com.example.renthouse.Admin.Fragment_NguoiDung.AdminNguoiDung_Fragment_NguoiDungBiChan;

public class TabLayoutNguoiDungAdapter extends FragmentStateAdapter {
    public TabLayoutNguoiDungAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new AdminNguoiDung_FragmentDanhSachNguoiDung();
            }
            case 1: {
                return new AdminNguoiDung_Fragment_NguoiDungBiChan();
            }
        }
        return new AdminNguoiDung_FragmentDanhSachNguoiDung();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

