package com.example.renthouse.Admin.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Admin.Adapter.TabLayoutPhongTroAdapter;
import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentChoDuyet;
import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentDanhSach;
import com.example.renthouse.Admin.Fragment_PhongTro.AdminPhongTro_FragmentQuaHan;
import com.example.renthouse.R;
import com.google.android.material.tabs.TabLayout;

public class Admin_FragmentPhongTro extends Fragment {
    private TabLayout fragPhongtro_tab_layout;
    private ViewPager2 fragPhongtro_viewPager2;
    private TabLayoutPhongTroAdapter tabLayoutPhongTroAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__phong_tro, container, false);;

        fragPhongtro_tab_layout = view.findViewById(R.id.fragPhongTro_tabLayout);
        fragPhongtro_viewPager2 = view.findViewById(R.id.fragPhongTro_viewPager2);

        fragPhongtro_tab_layout.addTab(fragPhongtro_tab_layout.newTab().setText("Danh sách"));
        fragPhongtro_tab_layout.addTab(fragPhongtro_tab_layout.newTab().setText("Chờ duyệt"));
        fragPhongtro_tab_layout.addTab(fragPhongtro_tab_layout.newTab().setText("Quá hạn"));

        FragmentManager fragmentManager = getChildFragmentManager();

        tabLayoutPhongTroAdapter = new TabLayoutPhongTroAdapter(fragmentManager, getLifecycle());
        fragPhongtro_viewPager2.setAdapter(tabLayoutPhongTroAdapter);

        fragPhongtro_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragPhongtro_viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //fragPhongtro_viewPager2.setCurrentItem(tab.getPosition());
            }
        });

        fragPhongtro_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                fragPhongtro_tab_layout.selectTab(fragPhongtro_tab_layout.getTabAt(position));
            }
        });



        return view;
    }
}