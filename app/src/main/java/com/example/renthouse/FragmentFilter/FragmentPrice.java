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
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
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
    private RangeSlider rangeSliderControlPrice;

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
        textInputEditTextPriceFrom = view.findViewById(R.id.textInputEditTextPriceFrom);
        textInputEditTextPriceTo = view.findViewById(R.id.textInputEditTextPriceTo);
        rangeSliderControlPrice = view.findViewById(R.id.rangeSlider);
        textInputEditTextPriceFrom.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputEditTextPriceFrom.removeTextChangedListener(this);

                // Lấy dữ liệu đã nhập từ TextInputEditText
                String userInput = s.toString();

                // Xóa tất cả các dấu space có thể đã được thêm trước đó
                userInput = userInput.replaceAll(" ", "");

                try {
                    // Chuyển đổi dữ liệu sang định dạng số tiền
                    long amount = Long.parseLong(userInput);
                    String formattedAmount = decimalFormat.format(amount);

                    // Thêm dấu space vào định dạng hàng nghìn
                    textInputEditTextPriceFrom.setText(formattedAmount);
                    textInputEditTextPriceFrom.setSelection(formattedAmount.length());

                    // Thay đổi giá trị của thumb from trong rangeSliderControlPrice
                    float fromValue = Math.min(Math.max((float) amount, Float.MIN_VALUE), Float.MAX_VALUE);
                    rangeSliderControlPrice.setValueFrom(fromValue);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Bạn hãy nhập một số tiền phù hợp", Toast.LENGTH_LONG).show();
                    textInputEditTextPriceFrom.setText("");
                }

                textInputEditTextPriceFrom.addTextChangedListener(this);
            }
        });
        textInputEditTextPriceTo.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputEditTextPriceTo.removeTextChangedListener(this);

                // Lấy dữ liệu đã nhập từ TextInputEditText
                String userInput = s.toString();

                // Xóa tất cả các dấu space có thể đã được thêm trước đó
                userInput = userInput.replaceAll(" ", "");

                try {
                    // Chuyển đổi dữ liệu sang định dạng số tiền
                    long amount = Long.parseLong(userInput);
                    String formattedAmount = decimalFormat.format(amount);

                    // Thêm dấu space vào định dạng hàng nghìn
                    textInputEditTextPriceTo.setText(formattedAmount);
                    textInputEditTextPriceTo.setSelection(formattedAmount.length());

                    // Thay đổi giá trị của thumb to trong rangeSliderControlPrice
                    float toValue = Math.min(Math.max((float) amount, Float.MIN_VALUE), Float.MAX_VALUE);
                    rangeSliderControlPrice.setValueTo(toValue);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Bạn hãy nhập một số tiền phù hợp", Toast.LENGTH_LONG).show();
                    textInputEditTextPriceTo.setText("");
                }

                textInputEditTextPriceTo.addTextChangedListener(this);
            }
        });
        rangeSliderControlPrice.setValueFrom(0);
        rangeSliderControlPrice.setValueTo(20000000);
        rangeSliderControlPrice.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                List<Float> thumbValues = rangeSliderControlPrice.getValues();

                String fromValue = decimalFormat.format(thumbValues.get(0));
                String toValue = decimalFormat.format(thumbValues.get(1));

                fromValue.replace(",", " ");
                toValue.replace(",", " ");

                textInputEditTextPriceFrom.setText(fromValue);
                textInputEditTextPriceTo.setText(toValue);
            }
        });
        return view;
    }
}