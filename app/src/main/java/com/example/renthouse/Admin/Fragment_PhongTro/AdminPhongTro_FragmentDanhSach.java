package com.example.renthouse.Admin.Fragment_PhongTro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.R;

public class AdminPhongTro_FragmentDanhSach extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_phong_tro__danh_sach, container, false);

        //replaceFragment(new AdminPhongTro_FragmentEmpty());

        return view;
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutPhongTro, fragment);
        fragmentTransaction.commit();
    }
}