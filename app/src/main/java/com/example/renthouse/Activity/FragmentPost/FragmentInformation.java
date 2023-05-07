package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;

public class FragmentInformation extends Fragment {

    CheckBox cbParking;
    LinearLayout parkingInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_information, container, false);
        cbParking = (CheckBox) v.findViewById(R.id.cbParking);
        parkingInfo = (LinearLayout) v.findViewById(R.id.parkingInfo);

        cbParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    parkingInfo.setVisibility(View.VISIBLE);
                }
                else{
                    parkingInfo.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }
}
