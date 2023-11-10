package com.example.renthouse.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.renthouse.Adapter.TabLayoutAccountAdapter;
import com.example.renthouse.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentAccount extends Fragment {
    public TabLayout fragHome_tab_layout;
    public ViewPager2 fragHome_viewPager2;
    public TabLayoutAccountAdapter tabLayoutAccountAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        fragHome_tab_layout = view.findViewById(R.id.fragHome_tab_layout);
        fragHome_viewPager2 = view.findViewById(R.id.fragHome_viewPager2);

        fragHome_tab_layout.addTab(fragHome_tab_layout.newTab().setText("Cá nhân"));
        fragHome_tab_layout.addTab(fragHome_tab_layout.newTab().setText("Bài chờ duyệt"));
        fragHome_tab_layout.addTab(fragHome_tab_layout.newTab().setText("Bài đã đăng"));

        FragmentManager fragmentManager = getChildFragmentManager();

        tabLayoutAccountAdapter = new TabLayoutAccountAdapter(fragmentManager, getLifecycle());
        fragHome_viewPager2.setAdapter(tabLayoutAccountAdapter);

        fragHome_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragHome_viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragHome_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                fragHome_tab_layout.selectTab(fragHome_tab_layout.getTabAt(position));
            }
        });

        return view;
    }

}