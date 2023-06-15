package com.example.renthouse.Fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.renthouse.FragmentFilter.FragmentAmount;
import com.example.renthouse.FragmentFilter.FragmentOptionOther;
import com.example.renthouse.FragmentFilter.FragmentPrice;
import com.example.renthouse.FragmentFilter.FragmentType;
import com.example.renthouse.FragmentFilter.FragmentUtilities;
import com.example.renthouse.OOP.ObjectSearch;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FragmentFilter extends Fragment {
    MaterialButton[] materialButtonFilter = new MaterialButton[5];
    private Context mContext;
    private FragmentPrice priceFragment;
    private FragmentUtilities utilitiesFragment;
    private FragmentType typeFragment;
    private FragmentAmount amountFragment;
    private FragmentOptionOther optionOtherFragment;
    private ObjectSearch objectSearch;

    private static int POSITION = -1;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        materialButtonFilter[0] = view.findViewById(R.id.buttonPrice); // buttonPrice
        materialButtonFilter[1] = view.findViewById(R.id.buttonUtilities); // buttonUtilities
        materialButtonFilter[2] = view.findViewById(R.id.buttonType); // buttonType
        materialButtonFilter[3] = view.findViewById(R.id.buttonAmount); // buttonAmount
        materialButtonFilter[4] = view.findViewById(R.id.buttonOptionOther); // buttonOptionOther

        priceFragment = new FragmentPrice();
        utilitiesFragment = new FragmentUtilities();
        typeFragment = new FragmentType();
        amountFragment = new FragmentAmount();
        optionOtherFragment = new FragmentOptionOther();

        objectSearch = new ObjectSearch();

        HorizontalScrollView horizontalScrollViewResult = view.findViewById(R.id.horizontalDisplayResult);
        horizontalScrollViewResult.setVisibility(View.GONE);
        handleButtonnFilter();
        _initSetButtonPrice();
        return view;
    }
    public void _initSetButtonPrice() {
        materialButtonFilter[0].setBackgroundColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[0].setTextColor(getResources().getColor(R.color.white));
        POSITION = 0;
        replaceFragment(priceFragment);
    }
    public void unChekedButton() {
        Log.d("Stated", String.valueOf(materialButtonFilter[POSITION].isCheckable()));
        switch (POSITION) {
            case 0:
                if (!priceFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    objectSearch.setPrice(priceFragment.getValue());
                    setButtonChecked();
                }
                break;
            case 1:
                if (!utilitiesFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    objectSearch.setUtilities(utilitiesFragment.getValue());
                    setButtonChecked();
                }
                break;
            case 2:
                if (!typeFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    objectSearch.setType(typeFragment.getValue());
                    setButtonChecked();
                }
                break;
            case 3:
                if (!amountFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    objectSearch.setAmount(amountFragment.getValue().get(0));
                    objectSearch.setGender(amountFragment.getValue().get(1));
                    setButtonChecked();
                }
                break;
            case 4:
                if (!optionOtherFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    objectSearch.setSort(optionOtherFragment.getValue());
                    setButtonChecked();
                }
                break;
        }
    }
    private void setButtonUnChecked(){
        materialButtonFilter[POSITION].setBackgroundColor(getResources().getColor(R.color.Secondary_90));
        materialButtonFilter[POSITION].setTextColor(getResources().getColor(R.color.Secondary_40));
        materialButtonFilter[POSITION].setIcon(getResources().getDrawable(R.drawable.ic_expand));
    }
    private void setButtonChecked(){
        materialButtonFilter[POSITION].setBackgroundColor(getResources().getColor(R.color.Primary_98));
        materialButtonFilter[POSITION].setTextColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[POSITION].setIcon(getResources().getDrawable(R.drawable.ic_expand_checked));
    }
    public void handleButtonnFilter() {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            int finalI = i;
            materialButtonFilter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialButtonFilter[finalI].setBackgroundColor(getResources().getColor(R.color.Primary_40));
                    materialButtonFilter[finalI].setTextColor(getResources().getColor(R.color.white));
                    materialButtonFilter[finalI].setIcon(getResources().getDrawable(R.drawable.ic_collapse));

                    if (POSITION == finalI) {
                        return;
                    }
                    unChekedButton();
                    POSITION = finalI;
                    setFragment(finalI);
                }
            });
        }
    }
    public void setFragment(int position) {
        switch (position) {
            case 0:
                replaceFragment(priceFragment);
                break;
            case 1:
                replaceFragment(utilitiesFragment);
                utilitiesFragment.setValue(objectSearch.getUtilities());
                break;
            case 2:
                replaceFragment(typeFragment);
                break;
            case 3:
                replaceFragment(amountFragment);
                break;
            case 4:
                replaceFragment(optionOtherFragment);
                break;
            default:
                replaceFragment(priceFragment);
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linearLayoutFragment, fragment);
        fragmentTransaction.commit();
    }
}