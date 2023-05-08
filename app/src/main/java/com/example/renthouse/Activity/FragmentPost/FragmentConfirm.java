package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentConfirm extends Fragment {

    TextInputEditText edtPhoneNumber;
    TextInputEditText edtTitle;
    TextInputEditText edtDescription;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_confirm, container, false);
        edtPhoneNumber = v.findViewById(R.id.edtPhoneNumber);
        edtTitle = v.findViewById(R.id.edtTitle);
        edtDescription = v.findViewById(R.id.edtDescription);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getPhoneNumber(){
        return edtPhoneNumber.getText().toString();
    }

    public String getTitle(){
        return edtTitle.getText().toString();
    }

    public String getDescription(){
        return edtDescription.getText().toString();
    }
}
