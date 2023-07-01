package com.example.renthouse.Admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentChoDuyet;
import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentDanhSach;
import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentQuaHan;

public class TabLayoutPhongTroAdapter extends FragmentStateAdapter {
    public TabLayoutPhongTroAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new AdminPhongTro_FragmentDanhSach();
            }
            case 1:{
                return new AdminPhongTro_FragmentChoDuyet();
            } case 2:{
                return new AdminPhongTro_FragmentQuaHan();
            }
        }
        return new AdminPhongTro_FragmentDanhSach();
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
