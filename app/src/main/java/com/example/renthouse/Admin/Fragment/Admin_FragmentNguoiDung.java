package com.example.renthouse.Admin.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Admin.Adapter.TabLayoutNguoiDungAdapter;
import com.example.renthouse.Admin.Fragment_NguoiDung.AdminNguoiDung_FragmentDanhSachNguoiDung;
import com.example.renthouse.Admin.Fragment_NguoiDung.AdminNguoiDung_Fragment_NguoiDungBiChan;
import com.example.renthouse.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Admin_FragmentNguoiDung extends Fragment {
    private TabLayout fragNguoiDung_tab_layout;
    private ViewPager2 fragNguoiDung_viewPager2;
    private TabLayoutNguoiDungAdapter tabLayoutNguoiDungAdapter;
    private Fragment selectedFragment = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__nguoi_dung, container, false);

        fragNguoiDung_tab_layout = view.findViewById(R.id.fragNguoiDung_tabLayout);
        fragNguoiDung_viewPager2 = view.findViewById(R.id.fragNguoiDung_viewPager2);

        fragNguoiDung_tab_layout.addTab(fragNguoiDung_tab_layout.newTab().setText("Danh sách người dùng"));
        fragNguoiDung_tab_layout.addTab(fragNguoiDung_tab_layout.newTab().setText("Người dùng bị chặn"));

        FragmentManager fragmentManager = getChildFragmentManager();

        tabLayoutNguoiDungAdapter = new TabLayoutNguoiDungAdapter(fragmentManager, getLifecycle());

        fragNguoiDung_viewPager2.setAdapter(tabLayoutNguoiDungAdapter);

        fragNguoiDung_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragNguoiDung_viewPager2.setCurrentItem(tab.getPosition());

                selectedFragment = fragmentManager.getFragments().get(tab.getPosition());
                if (selectedFragment instanceof AdminNguoiDung_FragmentDanhSachNguoiDung) {
                    ((AdminNguoiDung_FragmentDanhSachNguoiDung) selectedFragment).loadDataFragment();
                } else {
                    ((AdminNguoiDung_Fragment_NguoiDungBiChan) selectedFragment).loadDataFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fragNguoiDung_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                fragNguoiDung_tab_layout.selectTab(fragNguoiDung_tab_layout.getTabAt(position));
            }
        });

        return view;
    }

}