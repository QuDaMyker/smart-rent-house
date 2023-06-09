package com.example.renthouse.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAccountPostedBinding;

public class FragmentAccountPosted extends Fragment {
    private FragmentAccountPostedBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountPostedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        return view;
    }
}