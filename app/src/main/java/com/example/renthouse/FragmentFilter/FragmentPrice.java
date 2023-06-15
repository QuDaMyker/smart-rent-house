package com.example.renthouse.FragmentFilter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPrice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPrice extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText textInputEditTextPriceFrom;
    private TextInputEditText textInputEditTextPriceTo;
    private RangeSlider rangeSlider;
    private String valueFrom;
    private String valueTo;
    public FragmentPrice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPrice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPrice newInstance(String param1, String param2) {
        FragmentPrice fragment = new FragmentPrice();
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
        View view  =  inflater.inflate(R.layout.fragment_price, container, false);
        textInputEditTextPriceTo = view.findViewById(R.id.textInputEditTextPriceTo);
        textInputEditTextPriceFrom = view.findViewById(R.id.textInputEditTextPriceFrom);
        rangeSlider = view.findViewById(R.id.rangeSlider);
        rangeSlider.setValueFrom(0);
        rangeSlider.setValueTo(100);
        textInputEditTextPriceFrom.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().replaceAll(" ", ""); // Loại bỏ dấu phân cách hàng nghìn có sẵn (nếu có)
                long number;
                try {
                    number = Long.parseLong(input);
                    if (number > 20000000) {
                        Toast.makeText(getContext(), "Số tiền quá lớn", Toast.LENGTH_LONG).show();
                        textInputEditTextPriceFrom.setText("");
                        return;
                    }

                    String formattedNumber = decimalFormat.format(number);
                    String price = formattedNumber.replaceAll(",", " ");
                    List<Float> values = rangeSlider.getValues();
                    float minValue = (float) number / 200000;
                    if (minValue < 0.005F) {
                        minValue = 0.005F;
                    }
                    values.set(0, minValue);
                    rangeSlider.setValues(values);

                    textInputEditTextPriceFrom.removeTextChangedListener(this);
                    textInputEditTextPriceFrom.setText(price);
                    textInputEditTextPriceFrom.setSelection(price.length());
                    textInputEditTextPriceFrom.addTextChangedListener(this);
                } catch (NumberFormatException e) {
                    // Xử lý khi không thể chuyển đổi sang số
                }
            }
        });
        textInputEditTextPriceTo.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().replaceAll(" ", ""); // Loại bỏ dấu phân cách hàng nghìn có sẵn (nếu có)
                long number;
                try {
                    number = Long.parseLong(input);
                    if (number > 20000000) {
                        Toast.makeText(getContext(), "Số tiền quá lớn", Toast.LENGTH_LONG).show();
                        textInputEditTextPriceTo.setText("");
                        return;
                    }
                    String formattedNumber = decimalFormat.format(number);

                    String price = formattedNumber.replaceAll(",", " ");
                    List<Float> values = rangeSlider.getValues();
                    float maxValue = (float) number / 200000;
                    if (maxValue < values.get(0)) {
                        maxValue = values.get(0);
                    }
                    values.set(1, maxValue);
                    rangeSlider.setValues(values);

                    textInputEditTextPriceTo.removeTextChangedListener(this);
                    textInputEditTextPriceTo.setText(price);
                    textInputEditTextPriceTo.setSelection(price.length());
                    textInputEditTextPriceTo.addTextChangedListener(this);
                } catch (NumberFormatException e) {
                    // Xử lý khi không thể chuyển đổi sang số
                }
            }
        });
        rangeSlider.setLabelBehavior(LabelFormatter.LABEL_GONE);
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = rangeSlider.getValues();
                float minValue = values.get(0);
                float maxValue = values.get(1);

                long priceFromLong= Math.round(minValue * 200000);
                long priceToLong = Math.round(maxValue * 200000);

                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedNumber1 = decimalFormat.format(priceFromLong);
                String formattedNumber2 = decimalFormat.format(priceToLong);

                String priceFrom = formattedNumber1.replaceAll(",", " ");
                String priceTo = formattedNumber2.replaceAll(",", " ");

                textInputEditTextPriceFrom.setText(String.valueOf(priceFrom));
                textInputEditTextPriceTo.setText(String.valueOf(priceTo));
           }
        });
        return view;
    }
    public boolean hasValue() {
        boolean flag = true;
        valueFrom = String.valueOf(textInputEditTextPriceFrom.getText());
        valueTo = String.valueOf(textInputEditTextPriceTo.getText());
        if (valueFrom.isEmpty() || valueTo.isEmpty()) {
            flag = false;
        }
        return flag;
    }
    public ArrayList<Long> getValue() {
        ArrayList<Long> price = new ArrayList<>();
        price.add(Long.parseLong(valueFrom.replaceAll(" ", "")));
        price.add(Long.parseLong(valueTo.replaceAll(" ", "")));
        return price;
    }
}