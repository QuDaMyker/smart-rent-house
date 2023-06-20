package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.renthouse.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAmount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAmount extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton btnDescreasePeople;
    private ImageButton btnIncreasePeople;
    private TabLayout tabLayout;
    private EditText editTextAmount;
    public int POSITION = 0;
    public FragmentAmount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAmount.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAmount newInstance(String param1, String param2) {
        FragmentAmount fragment = new FragmentAmount();
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
        View view = inflater.inflate(R.layout.fragment_amount, container, false);
        btnDescreasePeople = view.findViewById(R.id.buttonDescreasePeople);
        btnIncreasePeople = view.findViewById(R.id.buttonIncreasePeople);
        editTextAmount = view.findViewById(R.id.editTextAmountPeople);
        tabLayout = view.findViewById(R.id.tabLayoutGender);
        editTextAmount.setEnabled(false);
        editTextAmount.setFocusable(false);
        btnDescreasePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(String.valueOf(editTextAmount.getText()));
                if (amount <= 1) {
                    return;
                } else {
                    amount = amount - 1;
                    editTextAmount.setText(String.valueOf(amount));
                }
            }
        });
        btnIncreasePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(String.valueOf(editTextAmount.getText()));
                if (amount > 8) {
                    return;
                } else {
                    amount = amount + 1;
                    editTextAmount.setText(String.valueOf(amount));
                }
            }
        });
        TabLayout.Tab tab  = tabLayout.getTabAt(POSITION);
        if (tab != null){
            tab.select();
        }

        return view;
    }
    public boolean hasValue() {
        return true;
    }
    public ArrayList<Integer> getValue(){
        ArrayList<Integer> amountAndGender = new ArrayList<>();
        int amount = Integer.parseInt(String.valueOf(editTextAmount.getText()));
        final int[] gender = {0};
        int position  = tabLayout.getSelectedTabPosition();

        // Thực hiện các tác vụ tương ứng với tab đã chọn
        switch (position) {
            case 0:
                gender[0] = 0; // Nữ
                break;
            case 1:
                gender[0] = 1; // Nam
                break;
            case 2:
                gender[0] = 2; // Khác
                break;
        }
        amountAndGender.add(amount);
        amountAndGender.add(gender[0]);
        return amountAndGender;
    }
    public String getValueString() {
        List<Integer> list = getValue();
        int amount = list.get(0);
        int gender = list.get(1);
        String value = "";
        if (gender == 0) {
            value = " Nữ";
        } else if (gender == 1) {
            value = " Nam";
        } else {
            value = " Khác";
        }
        return String.valueOf(amount) + value;
    }
    public void resetFragment() {
        editTextAmount.setText("2");
    }
}