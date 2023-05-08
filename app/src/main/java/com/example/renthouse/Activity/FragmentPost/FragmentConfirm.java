package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;

public class FragmentConfirm extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_confirm, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("key", myStringVariable);
//
//        setArguments(bundle);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String myString = bundle.getString("myStringKey");
//            int myInt = bundle.getInt("myIntKey");
//            // Do something with the data
//        }
//    }
}
