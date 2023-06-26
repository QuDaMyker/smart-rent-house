package com.example.renthouse.FragmentFilter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.renthouse.Interface.ITypeValueChangeListener;
import com.example.renthouse.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentType#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentType extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public boolean isResume = true;
    public boolean isDelete = false;
    private RadioGroup radioGroup;
    private ITypeValueChangeListener mListener;
    private int selectedRadioButtonId;
    public FragmentType(ITypeValueChangeListener mListener) {
        // Required empty public constructor
        this.mListener = mListener;
    }
    public FragmentType() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentType.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentType newInstance(String param1, String param2) {
        FragmentType fragment = new FragmentType();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        radioGroup = view.findViewById(R.id.radioButtonGroupType);
        radioGroup.clearCheck();
        selectedRadioButtonId = -1;
        isResume = true;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadioButtonId = checkedId;
                if (isResume) {
                    return;
                }
                if (selectedRadioButtonId != -1) {
                    Log.d("RadioButton", String.valueOf(selectedRadioButtonId));
                    Log.d("RadioButton", String.valueOf(checkedId));
                    mListener.onValueTypeChangeListener();
                    isDelete = false;
                }

            }
        });
        return view;
    }
    public boolean hasValue(){
        boolean flag = true;
        if (selectedRadioButtonId == -1) {
            flag = false;
        }
        return flag;
    }
    @SuppressLint("NonConstantResourceId")
    public int getValue(){
        switch (selectedRadioButtonId) {
            case R.id.radioButtonHomeStay_Campus:
                return 0;
            case R.id.radioButtonHiredRoom:
                return 1;
            case R.id.radioButtonHome:
                return 2;
            case R.id.radioButtonApartment:
                return 3;
        }
        return 0;
    }
    @SuppressLint("NonConstantResourceId")
    public String getValueString(){
        switch (selectedRadioButtonId) {
            case R.id.radioButtonHomeStay_Campus:
                return "Kí túc xá/Homestay";
            case R.id.radioButtonHiredRoom:
                return "Phòng cho thuê";
            case R.id.radioButtonHome:
                return "Nhà nguyên căn";
            case R.id.radioButtonApartment:
                return "Căn hộ";
        }
        return "";
    }
    public void resetFragment() {
        isResume = true;
        isDelete = true;
        radioGroup.clearCheck();
        selectedRadioButtonId = -1;
        isResume = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (isDelete) {
            radioGroup.clearCheck();
            selectedRadioButtonId = -1;
        }
        isResume = false;
    }
}