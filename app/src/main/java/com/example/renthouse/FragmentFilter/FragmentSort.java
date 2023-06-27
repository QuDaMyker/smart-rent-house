package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.renthouse.Interface.ISortValueChangeListener;
import com.example.renthouse.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSort#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSort extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioGroup radioGroup;
    private ISortValueChangeListener mListener;
    private int seletecedRadioButton;
    private boolean isResume;
    private boolean isDelete = false;
    public FragmentSort(ISortValueChangeListener mListener) {
        // Required empty public constructor
        this.mListener = mListener;
    }
    public FragmentSort() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOptionOther.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSort newInstance(String param1, String param2) {
        FragmentSort fragment = new FragmentSort();
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
        View view =  inflater.inflate(R.layout.fragment_option_other, container, false);

        radioGroup = view.findViewById(R.id.radioGroupSort);
        seletecedRadioButton = -1;
        radioGroup.clearCheck();
        this.isResume = true;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                seletecedRadioButton = radioGroup.getCheckedRadioButtonId();
                if (isResume) {
                    return;
                }
                if (seletecedRadioButton != -1) {
                    mListener.onValueSortChangeListener();
                    isDelete = false;
                }
            }
        });

        return view;
    }
    public boolean hasValue(){
        boolean flag = true;
        if (seletecedRadioButton == -1) {
            flag = false;
        }
        return flag;
    }
    public int getValue() {
        switch (seletecedRadioButton) {
            case R.id.radioButtonLienQuanNhat:
                return 0;
            case R.id.radioButtonMoiNhat:
                return 1;
            case R.id.radioButtonGiaMinToiMax:
                return 2;
            case R.id.radioButtonGiaMaxToiMin:
                return 3;
        }
        return 0;
    }
    public String getValueString() {
        switch (seletecedRadioButton) {
            case R.id.radioButtonLienQuanNhat:
                return "Liên quan nhất";
            case R.id.radioButtonMoiNhat:
                return "Mới nhất";
            case R.id.radioButtonGiaMinToiMax:
                return "Giá thấp đến cao";
            case R.id.radioButtonGiaMaxToiMin:
                return "Giá cao xuống thấp";
        }
        return "";
    }

    public void resetFragment() {
        isResume = true;
        isDelete = true;
        radioGroup.clearCheck();
        seletecedRadioButton = -1;
        isResume = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (isDelete) {
            radioGroup.clearCheck();
            seletecedRadioButton = -1;
        }
        isResume = false;
    }
}