package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.renthouse.R;

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
    private AppCompatImageButton btnDescreasePeople;
    private AppCompatImageButton btnIncreasePeople;
    private EditText editTextAmount;
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
        return view;
    }
}