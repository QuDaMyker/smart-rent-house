package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentConfirm extends Fragment {

    TextInputEditText edtPhoneNumber;
    TextInputEditText edtTitle;
    TextInputEditText edtDescription;

    TextInputLayout edtLayoutPhoneNumber;
    TextInputLayout edtLayoutTitle;
    TextInputLayout edtLayoutDescription;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_confirm, container, false);
        edtPhoneNumber = v.findViewById(R.id.edtPhoneNumber);
        edtTitle = v.findViewById(R.id.edtTitle);
        edtDescription = v.findViewById(R.id.edtDescription);

        edtLayoutPhoneNumber = v.findViewById(R.id.edtLayoutPhoneNumber);
        edtLayoutTitle = v.findViewById(R.id.edtLayoutTitle);
        edtLayoutDescription = v.findViewById(R.id.edtLayoutDescription);

        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutDescription.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutPhoneNumber.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    public boolean validateData(){
        boolean flag = true;
        if(TextUtils.isEmpty(edtTitle.getText().toString().trim())){
            edtLayoutTitle.setError("Vui lòng nhập tiêu đề bài đăng");
            flag = false;
        }
        if(TextUtils.isEmpty(edtDescription.getText().toString().trim())){
            edtLayoutDescription.setError("Vui lòng nhập mô tả");
            flag = false;
        }
        if(TextUtils.isEmpty(edtPhoneNumber.getText().toString().trim())){
            edtLayoutPhoneNumber.setError("Vui lòng nhập số điện thoại");
            flag = false;
        }
        if(edtTitle.getText().toString().length() > 60){
            edtLayoutTitle.setError("Tiêu đề bài đăng không được quá 60 ký tự");
            flag = false;
        }
        return flag;
    }
}
